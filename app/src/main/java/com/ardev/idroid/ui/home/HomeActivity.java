package com.ardev.idroid.ui.home;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Environment;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.window.SplashScreen;
import androidx.core.widget.NestedScrollView;
import androidx.transition.TransitionManager;
import com.ardev.builder.project.Project;
import com.ardev.idroid.ui.about.AboutActivity;
import com.ardev.idroid.ui.setting.SettingActivity;
import com.ardev.idroid.ui.about.AboutFragment;
import com.ardev.idroid.ui.main.MainActivity;
import com.ardev.idroid.ext.ActivityUtils;
import com.google.android.material.transition.MaterialFade;
import com.ardev.idroid.ui.home.wizard.WizardFragment;
import com.ardev.idroid.databinding.ActivityHomeBinding;
import com.ardev.idroid.base.BasePermissionActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.os.Bundle;
import java.util.Comparator;
import java.util.Arrays;
import android.os.Handler;
import java.util.concurrent.Executors;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.core.view.WindowCompat;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import androidx.appcompat.widget.PopupMenu;
import java.io.File;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;
import com.ardev.idroid.R;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.ardev.f.storage.Storage;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelStore;
import com.ardev.idroid.ext.UtilKt;
import java.util.concurrent.Executors;
import androidx.core.view.ViewCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.Hold;
import com.google.android.material.transition.MaterialContainerTransform;

    public class HomeActivity extends BasePermissionActivity  {
    
     
    HomeViewModel mViewModel;   
    
    RecyclerView.LayoutManager layoutManager;
    ProjectAdapter mAdapter;
    ActivityHomeBinding binding;
    Storage st;
    String pathFather;
    
    

  private final Hold holdTransition = new Hold();


    
  @Override
  public void onPermissionsGranted() {
  showProjects(pathFather);
  }
    
    
    
@Override
    protected void onCreate(Bundle savedInstanceState) {
		
      super.onCreate(savedInstanceState);
	 
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
       
        
        mViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this)).get(HomeViewModel.class);
        
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
           pathFather = getExternalFilesDir("projects").getAbsolutePath();
            } else {
        
        pathFather = Environment.getExternalStorageDirectory() + File.separator + "IdroidCode"+ File.separator + "projects";
        }
        
        fabInit();
        rvInit();
        toolbarInit();
		
 
 
	 
}

    private void toolbarInit() {
    
		binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
 if(id == R.id.about) {
	 
 AboutFragment newFragment = new AboutFragment();
 ActivityUtils.showFragment(HomeActivity.this, newFragment);
 } else if(id == R.id.setting) {
 cambiarActivity(HomeActivity.this, SettingActivity.class);
 
 }

return true;
});

}


    private void fabInit() {
 UtilKt.addSystemWindowInsetToMargin(binding.fab, false, false, false, true);
  ViewCompat.setTransitionName(binding.fab, "toWizard");
  binding.scrollingView.setOnScrollChangeListener(
        (v, x, y, oldX, oldY) -> {
          if (y > oldY + 20 && binding.fab.isExtended()) {
            binding.fab.shrink();
          }
          if (y < oldY - 20 && !binding.fab.isExtended()) {
            binding.fab.extend();
          }
          if (y == 0) {
            binding.fab.extend();
          }
        });
  
  
  
  
        binding.fab.setOnClickListener( v -> {
         
		
   WizardFragment newFragment = new WizardFragment();
   configureTransitions(newFragment);
         newFragment.setOnProjectCreated(this::openProject);
         
                   
 getSupportFragmentManager().beginTransaction()
   .addSharedElement(v, "toWizard")
   .add(android.R.id.content, newFragment)
   .addToBackStack(null)
   .commit();
                    
                });
    }
    
    public void rvInit() {
mAdapter= new ProjectAdapter();
  binding.list.setAdapter(mAdapter);
binding.list.setHasFixedSize(true);
binding.list.setItemAnimator(new DefaultItemAnimator());

mAdapter.setOnClickListener((view, project) ->openProject(project));

mAdapter.setOnLongClickListener(this::showOptionsMenu);
	
    }
    
    private void openProject(Project project) {
   		ProjectProvider.init(project);
               startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }
    
    
    
    
    private void showProjects(String path) {
    Executors.newSingleThreadExecutor().execute(() -> {
       File projectDir = new File(pathFather);
       File[] directories = projectDir.listFiles(File::isDirectory);
        List<Project> projects = new ArrayList<>();
            
            if (directories != null) {
             Arrays.sort(directories, Comparator.comparingLong(File::lastModified));
             
                for (File directory : directories) {
                    File appModule = new File(directory, "app");
                    if (appModule.exists()) {
                    Project project = new Project(new File(directory.getAbsolutePath().replaceAll("%20", " ")));
                        projects.add(project);
                    }
        		}
        	}
        
         runOnUiThread(() -> {
         
          TransitionManager.beginDelayedTransition(
                    (ViewGroup) binding.list.getParent(), new MaterialFade());
            if (projects.isEmpty()) {
                binding.scrollingView.setVisibility(View.GONE);
                binding.emptyItem.setVisibility(View.VISIBLE);
            } else {
               binding.scrollingView.setVisibility(View.VISIBLE);
                binding.emptyItem.setVisibility(View.GONE);
                mAdapter.setList(projects);
            }
          
          });
});
}
    
    private void showOptionsMenu(View view, Project project) {
    
    
    CharSequence[] chars = new CharSequence[]{"Compartir", "Borrar"};
		
		MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(HomeActivity.this, R.style.ThemeOverlay_MaterialAlertDialog_NoRounded);
		 
		b.setItems(chars, (d, i) -> {

			 
		 });
		 b.show();
        
    }

     private void configureTransitions(Fragment fragment) {
   
    int colorSurface = MaterialColors.getColor(HomeActivity.this, R.attr.colorSurface, R.attr.colorOnSurface);
    MaterialContainerTransform enterContainerTransform = buildContainerTransform(true);
    enterContainerTransform.setAllContainerColors(colorSurface);
    
    fragment.setSharedElementEnterTransition(enterContainerTransform);
    holdTransition.setDuration(enterContainerTransform.getDuration());

    MaterialContainerTransform returnContainerTransform = buildContainerTransform(false);
    returnContainerTransform.setAllContainerColors(colorSurface);
    fragment.setSharedElementReturnTransition(returnContainerTransform);
  }
   
   
  private MaterialContainerTransform buildContainerTransform(boolean entering) {
   
    MaterialContainerTransform transform = new MaterialContainerTransform(HomeActivity.this, entering);
    transform.setDrawingViewId(entering ? R.id.root : binding.getRoot().getId());
    
    return transform;
  }
   
   
}