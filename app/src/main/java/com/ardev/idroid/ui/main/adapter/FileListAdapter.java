package com.ardev.idroid.ui.main.adapter;

import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.ardev.idroid.ext.AdapterHelper;
import java.util.List;
import java.io.File;
import android.content.Intent;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ardev.idroid.R;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ListHolder> {

	private OnClickListener onClickListener = null;
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	private OnLongClickListener onLongClickListener = null;
	public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
		this.onLongClickListener = onLongClickListener;
	}

	List<File> mFiles = new ArrayList<File>();

	public void setList(List<File> list) {

		if (!list.isEmpty()) {
			this.mFiles = list;
			notifyDataSetChanged();
		}
	}

	@Override
	public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		return new ListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filelist, parent, false));

	}

	@Override
	public void onBindViewHolder(ListHolder viewHolder, final int position) {

		viewHolder.name.setText(mFiles.get(position).getName());
		AdapterHelper.setFileIcon(viewHolder.icon, mFiles.get(position) );
          viewHolder.icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		viewHolder.itemView.setOnClickListener(view -> {
			if (onClickListener == null) return;
			onClickListener.onItemClick(view, mFiles.get(position));
		});
		
	viewHolder.itemView.setOnLongClickListener(view -> {
			if (onLongClickListener == null) return false;
			onLongClickListener.onItemLongClick(view, mFiles.get(position));
		 return true;
		});
		
	}

	@Override
	public int getItemCount() {
		return mFiles.size();
	}

	public class ListHolder extends RecyclerView.ViewHolder {
		TextView name;
		ImageView icon;
		View itemView;

		public ListHolder(@NonNull View itemView) {
			super(itemView);
			this.itemView = itemView;
			name = itemView.findViewById(R.id.name);
			icon = itemView.findViewById(R.id.icon);

		}
	}

	public interface OnClickListener {
		void onItemClick(View view, File file);

	}
	
	public interface OnLongClickListener {
		void onItemLongClick(View view, File file);
		
		}
}