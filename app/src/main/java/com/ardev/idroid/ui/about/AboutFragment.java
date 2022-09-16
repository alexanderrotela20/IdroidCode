package com.ardev.idroid.ui.about;

import  androidx.annotation.NonNull;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.ardev.idroid.ui.home.HomeActivity;

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
  
  public class AboutFragment extends MaterialAboutFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
    }
    
    @Override protected 
    String getTitle() {
    return "Acerca de";
    }
    
    
   @Override
    protected MaterialAboutList getMaterialAboutList(Context context) {

        return Repository.createAboutList(requireContext(),((AppCompatActivity) requireActivity()) );
        }
  
}
