package com.ardev.idroid.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DiffUtil;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.ardev.idroid.ui.main.fragment.preview.ImageFragment;
import com.ardev.idroid.ui.main.fragment.editor.CodeEditorFragment;
import com.ardev.idroid.ui.main.fragment.preview.LayoutFragment;
import com.ardev.idroid.ui.main.fragment.preview.ValuesFragment;
import com.ardev.idroid.ext.ProjectUtils;
import com.ardev.idroid.ui.main.model.Editable;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PageAdapter extends FragmentStateAdapter {

    private final List<Editable> data = new ArrayList<>();

    public PageAdapter(FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    public void submitList(List<Editable> files) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return data.size();
            }

            @Override
            public int getNewListSize() {
                return files.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return Objects.equals(data.get(oldItemPosition), files.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return Objects.equals(data.get(oldItemPosition), files.get(newItemPosition));
            }
        });
        data.clear();
        data.addAll(files);
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        if (data.isEmpty()) {
            return -1;
        }
        return data.get(position).hashCode();
    }

    @Override
    public boolean containsItem(long itemId) {
        for (Editable d : data) {
            if (d.hashCode() == itemId) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
    Fragment fragment = null;
     if(getFileName(position).endsWith(".xml")) {
     	if(data.get(position).isPreview()) {
     	if(ProjectUtils.isValuesXMLFile(new File(getFilePath(position)))) {
     	 fragment =	new ValuesFragment(getFilePath(position));
     	 } else if(ProjectUtils.isLayoutXMLFile(new File(getFilePath(position)))) {
     	 fragment =	new LayoutFragment(getFilePath(position));
     	 }  else if(ProjectUtils.isDrawableXMLFile(new File(getFilePath(position)))) {
     	 fragment =	new ImageFragment(getFilePath(position));
     	 }
     	 
     	} else {
    	 fragment =	CodeEditorFragment.Companion.newInstance(getFilePath(position));
     	}
       } else if(isImage(getFilePath(position))) {
       fragment =	new ImageFragment(getFilePath(position));
       } else {
       fragment =	CodeEditorFragment.Companion.newInstance(getFilePath(position));
       }
    
    
    	return fragment;
    }
    
    
   private String getFilePath(int position) {
    return data.get(position).getFile().getPath();
    }
    
    private String getFileName(int position) {
    return data.get(position).getFile().getName();
    
    }
    
    private boolean isImage(String path) {
    File file = new File(path);
        String fileType = "";
        URL url = null;
		
        try {
            url = new URL("file://" + file.getPath());
            URLConnection connection = url.openConnection();
            fileType = connection.getContentType();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return fileType.contains("image/");
    
    }
    
    
    
    
}
