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

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;
import com.ardev.idroid.R;
import java.util.function.Consumer;

/**
 * Adapter for text actions in editor.
 *
 * @author Akash Yadav
 */
public class TextActionItemAdapter extends RecyclerView.Adapter<TextActionItemAdapter.VH> {

    private final List<ArCodeView.TextAction> actions;
    private final Consumer<ArCodeView.TextAction> onClick;

    public TextActionItemAdapter(
            List<ArCodeView.TextAction> actions, Consumer<ArCodeView.TextAction> onClick) {
        Objects.requireNonNull(actions);
        this.actions = actions;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_text_action_item, parent, false);
        return new VH(view);
                
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        
        final ArCodeView.TextAction action = actions.get(position);
        
holder.button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, action.icon, null, null);
        holder.button.setText(action.titleId);
       holder.itemView.setOnClickListener(
                v -> {
                    if (onClick != null) {
                        onClick.accept(action);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        View itemView;
     MaterialButton button;
        public VH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
           button = itemView.findViewById(R.id.root);
           
        

        }
    }
}
