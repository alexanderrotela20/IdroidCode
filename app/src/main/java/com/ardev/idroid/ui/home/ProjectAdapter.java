package com.ardev.idroid.ui.home;

import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.ardev.builder.project.Project;
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

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {

	private OnClickListener onClickListener = null;

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

private OnLongClickListener onLongClickListener = null;

	public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
		this.onLongClickListener = onLongClickListener;
	}

	List<Project> projects = new ArrayList<>();

	public void setList(List<Project> projects) {

		if (!projects.isEmpty()) {
			this.projects = projects;
			notifyDataSetChanged();
		}
	}

	@Override
	public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		return new ProjectHolder(
				LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false));

	}

	@Override
	public void onBindViewHolder(ProjectHolder viewHolder, final int position) {

		viewHolder.name.setText(projects.get(position).getRootFile().getName());

		viewHolder.itemView.setOnClickListener(view -> {
			if (onClickListener == null)
				return;

			onClickListener.onItemClick(view, projects.get(position));

		});
		viewHolder.itemView.setOnLongClickListener(view -> {
			if (onLongClickListener == null) return false;

			onLongClickListener.onItemLongClick(view, projects.get(position));
			return true;

		});
	}

	@Override
	public int getItemCount() {
		return projects.size();
	}

	public class ProjectHolder extends RecyclerView.ViewHolder {
		TextView name;
		View itemView;

		public ProjectHolder(@NonNull View itemView) {
			super(itemView);
			this.itemView = itemView;
			name = itemView.findViewById(R.id.name);

		}
	}

	public interface OnClickListener {
		void onItemClick(View view, Project project);

	}
		public interface OnLongClickListener {
		void onItemLongClick(View view, Project project);

	}
}