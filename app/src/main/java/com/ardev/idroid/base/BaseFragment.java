package com.ardev.idroid.base;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ardev.idroid.app.MainViewModelProvider;
import com.ardev.idroid.ui.main.MainActivity;
import com.ardev.idroid.ui.main.MainViewModel;
import com.google.android.material.appbar.MaterialToolbar;



public  class BaseFragment extends Fragment {

MaterialToolbar toolbar;
protected MainViewModel mainViewModel;
	public BaseFragment(String path) {

		Bundle args = new Bundle();
		args.putString("path", path);
		setArguments(args);
	}
	

	public String getPath() {
		return requireArguments().getString("path");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			mainViewModel = MainViewModelProvider.getViewModel();
		
				
				
				
				
		}
		
			@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
   		 super.onViewCreated(view, savedInstanceState);
	 
		requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
         if (menu instanceof MenuBuilder) {
			MenuBuilder menuBuilder = (MenuBuilder) menu;
			menuBuilder.setOptionalIconsVisible(true);
		}
			menu.clear();
		setMenu(menu);
                
               
                
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            
            //onMenuSelected(menuItem);
 
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
	}
	
	 
	
	 public MaterialToolbar getToolbar() {
        if (toolbar == null) toolbar = ((MainActivity) requireActivity()).getToolbar();
        return toolbar;
    }
	
	
	protected void setMenu(Menu menu) {}
	//protected void onMenuSelected(MenuItem menuItem) {}
	}