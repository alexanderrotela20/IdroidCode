package com.danielstone.materialaboutlibrary;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import com.google.android.material.appbar.AppBarLayout;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.ardev.idroid.R;
import com.danielstone.materialaboutlibrary.adapters.MaterialAboutListAdapter;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.DefaultViewTypeManager;
import com.danielstone.materialaboutlibrary.util.ViewTypeManager;
import androidx.fragment.app.DialogFragment;
import java.util.concurrent.Executors;
import java.lang.ref.WeakReference;

public abstract class MaterialAboutFragment extends DialogFragment {

    private MaterialAboutList list = new MaterialAboutList.Builder().build();
    private MaterialToolbar toolbar;
    private AppBarLayout appbar;
    private RecyclerView recyclerView;
    private MaterialAboutListAdapter adapter;

    public static MaterialAboutFragment newInstance(MaterialAboutFragment fragment) {
        return fragment;
    }
	protected abstract String getTitle();
    protected abstract MaterialAboutList getMaterialAboutList(Context activityContext);


    protected boolean shouldAnimate() {
        return true;
    }
    protected AppBarLayout getAppBar() {
    return appbar;
    }
 
	 

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mal_material_about_content, container, false);
 		appbar = view.findViewById(R.id.appbar);
 		toolbar = view.findViewById(R.id.toolbar);
 		
        recyclerView =  view.findViewById(R.id.mal_recyclerview);
        toolbar.setTitle(getTitle());
        toolbar.setNavigationOnClickListener(v ->dismiss());

requireActivity().getOnBackPressedDispatcher().addCallback( new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
dismiss();
}
});
        
        adapter = new MaterialAboutListAdapter(getViewTypeManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(20);

        new ListTask(this);
        
        
	 
        return view;
    }

		 @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @NonNull
    protected ViewTypeManager getViewTypeManager() {
        return new DefaultViewTypeManager();
    }

    protected MaterialAboutList getList() {
        return list;
    }

    protected void setMaterialAboutList(MaterialAboutList materialAboutList) {
        list = materialAboutList;
        adapter.setData(list.getCards());
    }

    private void displayList(){
        if (shouldAnimate()) {
            recyclerView.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(600)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
        } else {
            recyclerView.setAlpha(1f);
            recyclerView.setTranslationY(0f);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void refreshMaterialAboutList() {
        setMaterialAboutList(list);
    }

    private static class ListTask {

        
   ListTask(MaterialAboutFragment materialAboutFragment) {
     Executors.newSingleThreadExecutor().execute(() -> {
       WeakReference<MaterialAboutFragment> fragmentContextReference  = new WeakReference<>(materialAboutFragment);
            MaterialAboutFragment fragment = fragmentContextReference.get();
            

new Handler(Looper.getMainLooper()).post(() -> { 
 if (fragment != null) {
                fragment.setMaterialAboutList(fragment.getMaterialAboutList(fragment.getActivity()));
                fragment.displayList();
            }
            fragmentContextReference.clear();
        
		});
	});

        }

    
}
}
