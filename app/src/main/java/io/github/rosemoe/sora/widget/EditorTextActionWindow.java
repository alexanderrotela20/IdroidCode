/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.rosemoe.sora.widget;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ardev.idroid.R;
 
import com.ardev.idroid.databinding.LayoutEditorActionsBinding;
import io.github.rosemoe.sora.event.HandleStateChangeEvent;
import io.github.rosemoe.sora.event.ScrollEvent;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.SubscriptionReceipt;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.EditorTouchEventHandler;
import io.github.rosemoe.sora.widget.base.EditorPopupWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Presents text actions in a popup widow.
 *
 * @author Akash Yadav
 * @see io.github.rosemoe.sora.widget.component.EditorTextActionWindow
 */
public class EditorTextActionWindow extends EditorPopupWindow
        implements ArCodeView.ITextActionPresenter {

    

    private static final long DELAY = 200;
    protected final List<SubscriptionReceipt<?>> subscriptionReceipts;
    private final Set<ArCodeView.TextAction> registeredActions = new TreeSet<>();
    private ArCodeView editor;
    private LayoutEditorActionsBinding binding;
    private EditorTouchEventHandler touchHandler;
    private long mLastScroll;
    private int mLastPosition;

    /**
     * Create a popup window for editor
     *
     * @param editor The editor
     * @see #FEATURE_SCROLL_AS_CONTENT
     * @see #FEATURE_SHOW_OUTSIDE_VIEW_ALLOWED
     * @see #FEATURE_HIDE_WHEN_FAST_SCROLL
     */
    public EditorTextActionWindow(@NonNull CodeEditor editor) {
        super(editor, FEATURE_SHOW_OUTSIDE_VIEW_ALLOWED);
        this.subscriptionReceipts = new ArrayList<>();

        getPopup().setAnimationStyle(R.style.PopupAnimation);
    }

    @Override
    public void bindEditor(@NonNull ArCodeView editor) {
        Objects.requireNonNull(editor, "Cannot bind with null editor");

        this.editor = editor;
        this.touchHandler = editor.getEventHandler();
        this.binding = LayoutEditorActionsBinding.inflate(LayoutInflater.from(editor.getContext()));
        this.binding.getRoot().setBackground(createBackground());
        this.binding.textActions.setVerticalFadingEdgeEnabled(true);
        this.binding.textActions.setFadingEdgeLength((int) (10 * editor.getDpUnit()));
        this.binding.textActions.setClipToOutline(
                true); // prevent items from being drawn outside window
        this.editor
                .getComponent(io.github.rosemoe.sora.widget.component.EditorTextActionWindow.class)
                .setEnabled(false);

        setContentView(this.binding.getRoot());
        subscribeToEvents();

        this.registeredActions.clear();
    }

    @Override
    public void registerAction(@NonNull ArCodeView.TextAction action) {
        Objects.requireNonNull(editor, "No editor attached!");
        this.registeredActions.add(action);
    }

    @Nullable
    @Override
    public ArCodeView.TextAction findAction(int id) {
        return this.registeredActions.stream()
                .filter(action -> action.id == id)
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public void invalidateActions() {
        if (binding == null || editor == null) {
            return;
        }

        final List<ArCodeView.TextAction>  actions =
                this.registeredActions.stream()
                        .filter(action -> canShowAction(editor, action))
                        .collect(Collectors.toList());
        this.binding.textActions.setAdapter(
                new TextActionItemAdapter(actions, this::performTextAction));
    }

    @Override
    public void destroy() {

        if (isShowing()) {
            dismiss();
        }

        this.registeredActions.clear();
        this.editor = null;
        this.binding = null;
        this.unsubscribeEvents();
    }

    @NonNull
    private Drawable createBackground() {
        final GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(ContextCompat.getColor(editor.getContext(), R.color.color_surface));
        drawable.setCornerRadius(8 * editor.getDpUnit());
        return drawable;
    }

    @Override
    public void show() {
        Objects.requireNonNull(editor, "No editor attached!");

        final RecyclerView actionsList = this.binding.textActions;
        if (binding.getRoot().getParent() != null) {
            ((ViewGroup) binding.getRoot().getParent()).removeView(actionsList);
        }

        final int dp8 = 8;
        final int dp16 = dp8 * 2;
        final List<ArCodeView.TextAction> actions =
                this.registeredActions.stream()
                        .filter(action -> canShowAction(editor, action))
                        .collect(Collectors.toList());
        actionsList.setAdapter(new TextActionItemAdapter(actions, this::performTextAction));
        this.binding
                .getRoot()
                .measure(
                        View.MeasureSpec.makeMeasureSpec(
                                editor.getWidth() - dp16 * 2, // 16dp margins from start and end
                                View.MeasureSpec.AT_MOST),
                        View.MeasureSpec.makeMeasureSpec(
                                (int) (260 * editor.getDpUnit())
                                        - dp16 * 2, // 260dp at most and 16dp margins from top and
                                // bottom
                                View.MeasureSpec.AT_MOST));
        setSize(
                this.binding.getRoot().getMeasuredWidth(),
                this.binding.getRoot().getMeasuredHeight());
        super.show();
    }

    private void subscribeToEvents() {
        this.subscriptionReceipts.add(
                this.editor.subscribeEvent(SelectionChangeEvent.class, this::onSelectionChanged));
        this.subscriptionReceipts.add(
                this.editor.subscribeEvent(ScrollEvent.class, this::onScrollEvent));
        this.subscriptionReceipts.add(
                this.editor.subscribeEvent(
                        HandleStateChangeEvent.class, this::onHandleStateChanged));
    }

    private void unsubscribeEvents() {
        for (final SubscriptionReceipt<?> receipt : this.subscriptionReceipts) {
            receipt.unsubscribe();
        }
        this.subscriptionReceipts.clear();
    }

    protected void onSelectionChanged(SelectionChangeEvent event, Unsubscribe unsubscribe) {
        if (touchHandler.hasAnyHeldHandle()) {
            return;
        }
        if (event.isSelected()) {
            if (!isShowing()) {
                this.editor.post(this::displayWindow);
            }
            mLastPosition = -1;
        } else {
            boolean show = false;
            if (event.getCause() == SelectionChangeEvent.CAUSE_TAP
                    && event.getLeft().index == mLastPosition
                    && !isShowing()
                    && !this.editor.getText().isInBatchEdit()) {
                this.editor.post(this::displayWindow);
                show = true;
            } else {
                dismiss();
            }
            if (event.getCause() == SelectionChangeEvent.CAUSE_TAP && !show) {
                mLastPosition = event.getLeft().index;
            } else {
                mLastPosition = -1;
            }
        }
    }

    protected void onScrollEvent(ScrollEvent event, Unsubscribe unsubscribe) {
        long last = mLastScroll;
        mLastScroll = System.currentTimeMillis();
        if (mLastScroll - last < DELAY) {
            postDisplay();
        }
    }

    protected void onHandleStateChanged(
            @NonNull HandleStateChangeEvent event, Unsubscribe unsubscribe) {
        if (event.isHeld()) {
            postDisplay();
        }
    }

    private void performTextAction(@NonNull ArCodeView.TextAction action) {
       // this.editor.performTextAction(action);
        if (action.id != ArCodeView.TextAction.SELECT_ALL
                && action.id != ArCodeView.TextAction.EXPAND_SELECTION) {
            dismiss();
        }
    }

    private int selectTop(@NonNull RectF rect) {
        int rowHeight = editor.getRowHeight();
        if (rect.top - rowHeight * 3 / 2F > getHeight()) {
            return (int) (rect.top - rowHeight * 3 / 2 - getHeight());
        } else {
            return (int) (rect.bottom + rowHeight / 2);
        }
    }

    public void displayWindow() {
        int top;
        Cursor cursor = this.editor.getCursor();
        if (cursor.isSelected()) {
            RectF leftRect = this.editor.getLeftHandleDescriptor().position;
            RectF rightRect = this.editor.getRightHandleDescriptor().position;
            int top1 = selectTop(leftRect);
            int top2 = selectTop(rightRect);
            top = Math.min(top1, top2);
        } else {
            top = selectTop(this.editor.getInsertHandleDescriptor().position);
        }
        top = Math.max(0, Math.min(top, this.editor.getHeight() - getHeight() - 5));
        float handleLeftX =
                this.editor.getOffset(
                        this.editor.getCursor().getLeftLine(),
                        this.editor.getCursor().getLeftColumn());
        float handleRightX =
                this.editor.getOffset(
                        this.editor.getCursor().getRightLine(),
                        this.editor.getCursor().getRightColumn());
        int panelX = (int) ((handleLeftX + handleRightX) / 2f);
        setLocationAbsolutely(panelX, top);
        show();
    }

    private void postDisplay() {
        if (!isShowing()) {
            return;
        }

        dismiss();

        if (!this.editor.getCursor().isSelected()) {
            return;
        }

        this.editor.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (editor == null) {
                            if (isShowing()) {
                                dismiss();
                            }
                            return;
                        }

                        if (!touchHandler.hasAnyHeldHandle()
                                && System.currentTimeMillis() - mLastScroll > DELAY
                                && touchHandler.getScroller().isFinished()) {
                            displayWindow();
                        } else {
                            editor.postDelayed(this, DELAY);
                        }
                    }
                },
                DELAY);
				}
	@Override
 public void dismiss() {
	
    }
}
