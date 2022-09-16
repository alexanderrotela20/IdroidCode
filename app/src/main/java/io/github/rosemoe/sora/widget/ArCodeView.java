package io.github.rosemoe.sora.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import java.io.File;
import java.util.Collections;
import java.util.List;
import com.ardev.idroid.R;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.jetbrains.annotations.Contract;

public class ArCodeView extends CodeEditor {

 private File file;
//private ILanguageServer mLanguageServer;
private ITextActionPresenter mTextActionPresenter;

 public ArCodeView(Context context) {
        this(context, null);
		
		
    }

    public ArCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArCodeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        //setColorScheme(new SchemeAndroidIDE());
        //setTextActionPresenter(chooseTextActionPresenter());
       // subscribeEvent(SelectionChangeEvent.class, this::handleSelectionChange);
        //subscribeEvent(ContentChangeEvent.class, this::handleContentChange);

        setInputType(createInputFlags());
    }

    public static int createInputFlags() {
        int flags =
                EditorInfo.TYPE_CLASS_TEXT
				| EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
               | EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS
 				| EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
        

        return flags;
    }

	  @NonNull
    @Contract(" -> new")
    public ITextActionPresenter chooseTextActionPresenter() {
        
            return new EditorTextActionWindow(this);
        
    }
    
    
     // public File getFile() {
        // return file;
    // }

   
    // public void setFile(File file) {
        // this.file = file;

        // if (file != null && mLanguageServer != null) {
            // final DocumentHandler documentHandler = mLanguageServer.getDocumentHandler();
            // if (documentHandler.accepts(file.toPath())) {
                // final String  text = getText().toString();
                // final DocumentOpenEvent event = new DocumentOpenEvent(file.toPath(), text,  0);
                // documentHandler.onFileOpened(event);
            // }

            // analyze();
        // }

        // if (file != null) {
            // getExtraArguments().putString(KEY_FILE, file.getAbsolutePath());
        // } else {
            // getExtraArguments().remove(KEY_FILE);
        // }
    // }

   
    // public void setLanguageServer(ILanguageServer server) {
        // this.mLanguageServer = server;     
    // }
    
    // public void analyze() {
        // if (mLanguageServer != null
                // && getFile() != null
                // && getEditorLanguage() instanceof IdroidLanguage) {
            // CompletableFuture.supplyAsync(() -> mLanguageServer.analyze(getFile().toPath()))
                    // .whenComplete(
                            // (diagnostics, throwable) -> {
                                // final IDroidLanguage lang = (IDroidLanguage) getEditorLanguage();
                                // final IAnalyzeManager analyzer = lang.getAnalyzeManager();
                                // if (analyzer instanceof IAnalyzeManager) {
                                    // ((IAnalyzeManager) analyzer).updateDiagnostics(diagnostics);
                                // }
                                // analyzer.rerun();
                            // });
        // }
    // }

    
    
     public void setTextActionPresenter(@NonNull ITextActionPresenter actionPresenter) {
        Objects.requireNonNull(actionPresenter, "Cannot set text action presenter to null");

        if (mTextActionPresenter != null) {
            mTextActionPresenter.destroy();
            mTextActionPresenter = null;
        }

        this.mTextActionPresenter = actionPresenter;

        actionPresenter.bindEditor(this);
        registerActionsTo(actionPresenter);
    }
    
    public void registerActionsTo(@NonNull ITextActionPresenter actionPresenter) {
        Objects.requireNonNull(
                actionPresenter, "Cannot register actions to null text action presenter");

        int index = -1;

        TypedArray array =
                getContext()
                        .getTheme()
                        .obtainStyledAttributes(
                                new int[] {
                                    android.R.attr.actionModeSelectAllDrawable,
                                    android.R.attr.actionModeCutDrawable,
                                    android.R.attr.actionModeCopyDrawable,
                                    android.R.attr.actionModePasteDrawable,
                                });

        

        

        actionPresenter.registerAction(
                new TextAction(
                        tintDrawable(array.getDrawable(0)),
                        android.R.string.selectAll,
                        TextAction.SELECT_ALL,
                        index++));

        actionPresenter.registerAction(
                new TextAction(
                        tintDrawable(array.getDrawable(1)),
                        android.R.string.cut,
                        TextAction.CUT,
                        index++));

        actionPresenter.registerAction(
                new TextAction(
                        tintDrawable(array.getDrawable(2)),
                        android.R.string.copy,
                        TextAction.COPY,
                        index++));

        actionPresenter.registerAction(
                new TextAction(
                        tintDrawable(array.getDrawable(3)),
                        android.R.string.paste,
                        TextAction.PASTE,
                        index++));


        array.recycle();
    }
    
    private Drawable tintDrawable(@NonNull Drawable drawable) {
        drawable.setTint(ContextCompat.getColor(getContext(), R.color.color_primary));
        return drawable;
    }

    public interface ITextActionPresenter {
        /**
         * Bind the action presenter with the given editor instance.
         *
         * @param editor The editor to bind with.
         */
        void bindEditor(@NonNull ArCodeView editor);

        /**
         * Register the text action with this presenter.
         *
         * @param action The action to register.
         */
        void registerAction(@NonNull TextAction action);

        /**
         * Look for the action with the given id in the actions registry.
         *
         * @param id The id to look for.
         * @return The registered text action. Maybe <code>null</code>.
         */
        @Nullable
        TextAction findAction(int id);

        /** Invalidate the registered actions. */
        void invalidateActions();

        /** Dismiss the presenter. */
        void dismiss();

        /**
         * Destroy this action presenter. The presenter should unsubscribe from any subscribed
         * events and release any held resources.
         */
        void destroy();

        default boolean canShowAction(
                @NonNull ArCodeView editor, @NonNull ArCodeView.TextAction action) {
            // all the actions are visible by default
            // so we need to get a confirmation from the editor
            if (action.visible) {
				
              //  return editor.shouldShowTextAction(action.id);
            }

            return true;
        }

         
    }

    /**
     * A model class for text actions.
     *
     * @author Akash Yadav
     */
    public static class TextAction implements Comparable<TextAction> {

        public static final int QUICKFIX = 9;
        public static final int EXPAND_SELECTION = 4;
        public static final int GOTO_DEFINITION = 5;
        public static final int FIND_REFERENCES = 6;
        public static final int COMMENT_LINE = 7;
        public static final int UNCOMMENT_LINE = 8;

        // common action IDs
        public static final int PASTE = 3;
        public static final int COPY = 2;
        public static final int CUT = 1;
        public static final int SELECT_ALL = 0;
        /** The ID of this text action; */
        public final int id;
        /** The index at which this action should be placed. */
        public final int index;
        /** The drawable resource id for this text action. */
        public Drawable icon;
        /** The string resource id for this text action. */
        @StringRes public int titleId;
        /** Whether this action should be visible to user. */
        public boolean visible = true;

        public TextAction(Drawable icon, int titleId, int id, int index) {
            this.icon = icon;
            this.titleId = titleId;
            this.id = id;
            this.index = index;
			
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TextAction)) {
                return false;
            }
            TextAction that = (TextAction) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public int compareTo(TextAction o) {
            return Integer.compare(this.index, o.index);
        }
    }

}