package com.ardev.idroid.ui.main;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import com.ardev.component.widget.breadcrumbs.model.IBreadcrumbItem;
import com.ardev.idroid.ext.JsonUtils;
import com.ardev.idroid.service.IndexServiceConnection;
import com.ardev.idroid.service.IndexService;
import com.ardev.idroid.ui.home.ProjectProvider;
import androidx.annotation.NonNull;
import com.ardev.builder.project.Project;
import com.ardev.idroid.ext.ProjectUtils;
import com.ardev.idroid.ui.home.ProjectManager;
import com.google.android.material.transition.MaterialFade;
import android.view.ViewGroup;
import androidx.transition.TransitionManager;
import com.ardev.idroid.ui.main.model.Editable;
import com.ardev.idroid.ui.main.adapter.PageAdapter;
import com.ardev.idroid.databinding.ActivityMainBinding;
import com.ardev.idroid.ui.main.adapter.FileListAdapter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ardev.idroid.ext.UtilKt;
import com.ardev.idroid.app.MainViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.PopupMenu;
import android.view.animation.AnticipateInterpolator;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ardev.component.widget.breadcrumbs.BreadcrumbsView;
import com.google.android.material.tabs.TabLayoutMediator;
import java.lang.reflect.Type;
import com.ardev.idroid.base.BaseActivity;
import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;

import java.util.concurrent.Executors;
import com.google.android.material.appbar.MaterialToolbar;
import java.io.File;
import androidx.viewpager2.widget.CompositePageTransformer;
import com.ardev.component.widget.breadcrumbs.DefaultBreadcrumbsCallback;
import com.google.android.material.tabs.TabLayout;
import com.ardev.f.storage.Storage;
import com.ardev.component.widget.breadcrumbs.model.BreadcrumbItem;
import com.ardev.idroid.R;

public class MainActivity extends  BaseActivity implements ProjectManager.OnProjectOpenListener {
	
	
FileListAdapter mFilesAdapter;
PageAdapter mPagerAdapter;
MainViewModel mainViewModel;
ActivityMainBinding v;
Project project;
private ProjectManager mProjectManager;
private IndexServiceConnection mIndexServiceConnection;
Storage st;

boolean isFirst = true, isFirst2 = true, isFirst3 = true;


	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
v = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(v.getRoot());
	 st = new Storage(this);	
	 mainViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this)).get(MainViewModel.class);
	MainViewModelProvider.init(mainViewModel);
	 project = new Project(ProjectProvider.getRootFile());
	 
	 mProjectManager = ProjectManager.getInstance();
     mProjectManager.addOnProjectOpenListener(this);
	  mIndexServiceConnection = new IndexServiceConnection(mainViewModel);
    
	
		init();
		initBCV();
		initRV();
		initTab();
		initObserver();
		mainViewModel.setFatherPath(project.getRootFile().getPath());
		mainViewModel.init();
		
	if (!project.equals(mProjectManager.getCurrentProject())) openProject(project);
	}
	
	private void init() {
UtilKt.addSystemWindowInsetToPadding(v.navView, false, false, false, false);
UtilKt.addSystemWindowInsetToMargin(v.pager2, false, false, false, false);

ViewCompat.setOnApplyWindowInsetsListener(
                v.pager2,
                (vi, insets) -> {
                    boolean imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime());

                    Insets in = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                   v.pager2.setPadding(0, 0, 0, in.bottom);
                    if (imeVisible) {
                        int imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
                        v.pager2.setPadding(0, 0, 0, imeHeight);
                    }
                    return insets;
        });



	setSupportActionBar(v.toolbar);
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		ActionBarDrawerToggle toggle =
		new ActionBarDrawerToggle(
		this,
		v.drawer,
		v.toolbar,
		R.string.app_name,
		R.string.app_name);
	
		toggle.syncState();
		toggle.setDrawerSlideAnimationEnabled(true);
		v.drawer.addDrawerListener(toggle);
		}
		

	private void initBCV() {
	
 	v.breadcrumbsView.setCallback( new DefaultBreadcrumbsCallback<BreadcrumbItem>() {
	@Override
	public void onNavigateBack(BreadcrumbItem item, int position) {
		
		mainViewModel.setCurrentPath(getPath(position));
		 bkpListBreadcrumb(v.breadcrumbsView.getItems());
	}
	
	@Override
	public void onNavigateNewLocation(BreadcrumbItem newItem, int changedPosition) {
		
	}
});
		
		}
		
		private void initRV(){

mFilesAdapter = new FileListAdapter();
  v.list.setAdapter(mFilesAdapter);
v.list.setHasFixedSize(true);
v.list.setItemAnimator(new DefaultItemAnimator());
mFilesAdapter.setOnClickListener( (view, file) -> {
			 if (file.isDirectory()) {
  	mainViewModel.addBreadcrumbItem(file.getPath());
	mainViewModel.setCurrentPath(file.getPath());
		} else {
	mainViewModel.addEditor(new Editable(file, false));
v.drawer.closeDrawer(GravityCompat.START);

}
});

mFilesAdapter.setOnLongClickListener( (v, file) -> { 

if (file.isDirectory()) showOptionsMenu(v, file);
else if((ProjectUtils.isLayoutXMLFile(file) || ProjectUtils.isValuesXMLFile(file)  || ProjectUtils.isDrawableXMLFile(file)) && file.isFile()) showFilesMenu(v, file);

});
 
 
 
 }
 
 	public void initObserver() {
 	
 	mainViewModel.getFatherPath().observe(MainActivity.this, fatherPath -> {
       	getSupportActionBar().setTitle(new File(fatherPath).getName());
       		
       		
      File breadcrumbs = new File(fatherPath + "/app/build/.cache", ".breadcrumbs");
      
if (breadcrumbs.exists()) {
	try {
   List<BreadcrumbItem> listBci =  JsonUtils.jsonToList(FileUtils.readFileToString(breadcrumbs, Charset.defaultCharset()), BreadcrumbItem.class);
   listBci.forEach( it -> v.breadcrumbsView.addItem(it));
  
   	
		} catch (Exception e) {}
		
		
		} else {
	
	v.breadcrumbsView.addItem(BreadcrumbItem.createSimpleItem(new File(fatherPath).getName()));
	
	}

	

            });
	mainViewModel.getCurrentPath().observe(MainActivity.this, path -> { 
	String _path = null;
	if(isFirst3) {
                isFirst3 = false;
     File currentPathCache = new File(mainViewModel.getFatherPath().getValue() + "/app/build/.cache", ".currentpath");
if (currentPathCache.exists()) {
	try {
   _path =  new Gson().fromJson(FileUtils.readFileToString(currentPathCache, Charset.defaultCharset()), String.class);
   
	} catch (Exception e) {} 
	} else {
	_path = path;
	}
	} else {
	_path = path;
	}
	showList(_path);
	bkpCurrentPath(_path);
	});
	
	
	
	
	mainViewModel.getBreadcrumbItem().observe(MainActivity.this, file -> {
		BreadcrumbItem breadcrumbItem = new BreadcrumbItem(mainViewModel.getListDir().getValue());
		breadcrumbItem.setSelectedItem(file.getName());
  v.breadcrumbsView.addItem(breadcrumbItem);
  bkpListBreadcrumb(v.breadcrumbsView.getItems());
	});
		
	mainViewModel.getListEditor()
                .observe(MainActivity.this, files -> {
                
                    mPagerAdapter.submitList(files);
                    v.tab.setVisibility(files.isEmpty() ? View.GONE : View.VISIBLE);
                });
                
   mainViewModel.getCurrentTabPosition()
                .observe(MainActivity.this, position -> {
                if(isFirst) {
                isFirst = false;
                 File positionFile = new File(mainViewModel.getFatherPath().getValue() + "/app/build/.cache", ".position");
if (positionFile.exists()) {
	try {
   int pos=  new Gson().fromJson(FileUtils.readFileToString(positionFile, Charset.defaultCharset()), int.class);
  v.pager2.setCurrentItem(pos, false);
		} catch (Exception e) {} 
		} else {
			 v.pager2.setCurrentItem(position, false);
			}
              } else {
                
             v.pager2.setCurrentItem(position, false);
				
               }
                 });
                 
    mainViewModel.isIndexing()
                .observe(MainActivity.this, indexing -> {
                   
                });
                
        mainViewModel.getCurrentState()
                .observe(MainActivity.this, message -> getSupportActionBar().setSubtitle(message));
		}
		
private String getPath(int depth) {
	if (depth == -1) depth = v.breadcrumbsView.getItems().size() - 1;
	StringBuffer sbPath = new StringBuffer(mainViewModel.getFatherPath().getValue());
	for (int i = 1; i <= depth; i++) {
		
		sbPath.append("/").append(v.breadcrumbsView.getItems().get(i).getSelectedItem());
	}
	return sbPath.toString();
}
 
 private void showList(String path) {
   
    Executors.newSingleThreadExecutor().execute(() -> {
    
        List<File> list = st.getFiles(path);
         
          
       runOnUiThread(() -> {
     
	
                    
            if (list.isEmpty()) {
                v.scrollingView.setVisibility(View.GONE);
                v.emptyItem.setVisibility(View.VISIBLE);
            } else {
                v.scrollingView.setVisibility(View.VISIBLE);
                v.emptyItem.setVisibility(View.GONE);
                 mFilesAdapter.setList(list);
        mainViewModel.setListCurrent(list);
        }
        
            
	
	});
	});
	}

private void showOptionsMenu(View view, File file) {
   
    
    CharSequence[] chars = new CharSequence[]{"Compartir", "Borrar"};
		
		MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(MainActivity.this, R.style.ThemeOverlay_MaterialAlertDialog_NoRounded);
		 
		b.setItems(chars, (d, i) -> {

			 
		 });
		 b.show();
        
    }
private void showFilesMenu(View view, File file) {
   
    
    CharSequence[] chars = new CharSequence[]{"Previsualizar", "Editar"};
		
		MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(MainActivity.this, R.style.ThemeOverlay_MaterialAlertDialog_NoRounded);
		 
		b.setItems(chars, (d, item) -> {
		switch(item) {
		case 0: 
		mainViewModel.addEditor(new Editable(file, true));
		v.drawer.closeDrawer(GravityCompat.START);
		break;
		case 1:
		mainViewModel.addEditor(new Editable(file, false));
		v.drawer.closeDrawer(GravityCompat.START);
		break;
		}
			 
		 });
		 b.show();
        
    }
    
   private void showTabMenu(TabLayout.Tab tab) {
   Editable editable = mainViewModel.getListEditor().getValue().get(tab.getPosition());
   boolean isPreview = mainViewModel.getListEditor().getValue().get(tab.getPosition()).isPreview();
    File file = mainViewModel.getListEditor().getValue().get(tab.getPosition()).getFile();
    
    String prev =  mainViewModel.getListEditor().getValue().get(tab.getPosition()).getFile().getName();

   PopupMenu menu = new PopupMenu(MainActivity.this, tab.view);
	
		menu.getMenu().add("Cerrar");
		menu.getMenu().add("Cerrar otros");
		menu.getMenu().add("Cerrar todos");
		menu.setOnMenuItemClickListener( item -> {
					 switch(item.getTitle().toString()) {
						 
						 case "Cerrar":
					mainViewModel.closeEditor(editable);
							 break;
							 
							 case "Cerrar otros":
					mainViewModel.closeOthersEditor(editable);
							 break;
							 
						 case "Cerrar todos":
					mainViewModel.closeAllEditor();
							 break;	 
					 }
					
					
					
					return true;
				});
				menu.show();
   
   
   }
    
    
    
    private void initTab() {
    
    
		
   mPagerAdapter = new PageAdapter(getSupportFragmentManager(), getLifecycle());
        v.pager2.setAdapter(mPagerAdapter);
        v.pager2.setUserInputEnabled(false);
		v.pager2.setPageTransformer((view, position) -> {
		view.setAlpha(0.2f);
		view.setVisibility(View.VISIBLE);
		view.animate().alpha(1.0f).setDuration(view.getResources().getInteger(android.R.integer.config_shortAnimTime));
		});
        
        v.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
           showTabMenu(tab);
               
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               mainViewModel.setCurrentTabPosition(tab.getPosition());

            }
        });
		
        new TabLayoutMediator(v.tab, v.pager2, true, false, (tab, i) -> {
			boolean isPreview = mainViewModel.getListEditor().getValue().get(i).isPreview();

			String fileName = mainViewModel.getListEditor().getValue().get(i).getFile().getName();
			
			tab.setText(fileName);
        	if(isPreview) tab.setIcon(R.drawable.ic_preview);
        	 
        	
        }).attach();
    
    
    
    }
    
    
     public void openProject(@NonNull Project project) {
        
        mIndexServiceConnection.setProject(project);

        mainViewModel.setIndexing(true);

        Intent intent = new Intent(MainActivity.this, IndexService.class);
        startService(intent);
        bindService(intent, mIndexServiceConnection, Context.BIND_IMPORTANT);
    }
    
     @Override
    public void onProjectOpen(Project project) {
    
    
    }
    
    
   @Override
        public void onBackPressed() {
             if (v.drawer.isDrawerOpen(GravityCompat.START)) {
            v.drawer.closeDrawer(GravityCompat.START);
            } else {
            super.onBackPressed();
            }
	}
	
	
	public MaterialToolbar getToolbar() {
        return v.toolbar;
    }
	
	  private void bkpListBreadcrumb(List<IBreadcrumbItem> bList) {
   try {
         File bciFile = new File(mainViewModel.getFatherPath().getValue() + "/app/build/.cache", ".breadcrumbs");
         String content  = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson( bList,new TypeToken<List<BreadcrumbItem>>(){}.getType());
        
        if(!bciFile.exists()) bciFile.createNewFile();
		if(!bList.isEmpty()){
        FileUtils.writeStringToFile(bciFile,
                 content,
                 Charset.defaultCharset());
                 }
                 } catch (Exception e) {}
   
   }
   
   private void bkpCurrentPath(String path) {
   try {
         File pathFile= new File(mainViewModel.getFatherPath().getValue() + "/app/build/.cache", ".currentpath");
        
        if(!pathFile.exists()) pathFile.createNewFile();
		
        FileUtils.writeStringToFile(pathFile,
                 new Gson().toJson(path) ,
                 Charset.defaultCharset());
                 
                 } catch (Exception e) {}
   
   
         }
	
	

	}