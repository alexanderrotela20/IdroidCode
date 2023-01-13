package com.ardev.idroid.ui.main.fragment.preview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ardev.component.view.TouchImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.VectorMasterDrawable;
import com.ardev.component.view.OutlineView;
import com.ardev.tools.parser.AndroidXmlParser;
import com.ardev.tools.parser.ReadOnlyParser;
import com.ardev.idroid.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.ardev.idroid.ext.ProjectUtils;
import com.bumptech.glide.request.RequestOptions;
import com.ardev.idroid.R;

import java.io.File;

public class ImageFragment extends BaseFragment {

private OutlineView outlineView;
private AndroidXmlParser androidXmlParser;

   
   	public ImageFragment(String path) {
		super(path);
		}
		
		
   @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
                             
 View v = inflater.inflate(R.layout.fragment_image, container, false);
 		TouchImageView imageView = v.findViewById(R.id.imageView);
		ImageView vectorView =  v.findViewById(R.id.vectorView);
		File file = new File(getPath());
	
		if(file.exists()){
		
			if(ProjectUtils.isDrawableXMLFile(file)) {
			imageView.setVisibility(View.GONE);
			vectorView.setVisibility(View.VISIBLE);
			try {
	VectorMasterDrawable  vectorDrawable = VectorMasterDrawable.fromXMLFile(file);
			Glide.with(requireContext())
                    .applyDefaultRequestOptions(new RequestOptions().override(100).encodeQuality(80))
                    .load(vectorDrawable)
                    .into(vectorView);
			} catch(Exception e) {}
			} else {
			vectorView.setVisibility(View.GONE);
			imageView.setVisibility(View.VISIBLE);
			Glide.with(requireContext())
                    .applyDefaultRequestOptions(new RequestOptions().override(100).encodeQuality(80))
                  
                    .load(file)
                   
                    .into(imageView);
			
			}
		
		
		
		
 
 
 
 		}
                           return v;  
                     }
                     
                     
                     
                     }