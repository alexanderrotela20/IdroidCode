package com.ardev.idroid.ui.about;

import androidx.annotation.NonNull;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import com.google.android.material.transition.MaterialSharedAxis;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;

 
import com.ardev.idroid.R;

public class LicenseFragment  extends MaterialAboutFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
    }
    
    
    
    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
    getAppBar().setElevation(0);
    getAppBar().setBackgroundColor(requireContext().getResources().getColor(R.color.color_surface));
    
    }
    
    @Override protected 
    String getTitle() {
    return "Licencias";
    }
    
      
    
   @Override
    protected MaterialAboutList getMaterialAboutList(Context context) {
		
								
		      return Repository.createLicenseList(requireContext());
		}
   
	}

