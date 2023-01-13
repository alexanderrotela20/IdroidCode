package com.ardev.idroid.ui.main


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import androidx.activity.viewModels
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import com.ardev.component.widget.breadcrumbs.model.IBreadcrumbItem
import com.ardev.idroid.ext.JsonUtils
import com.ardev.idroid.service.IndexServiceConnection
import com.ardev.idroid.service.IndexService
import com.ardev.idroid.ui.home.ProjectProvider
import androidx.annotation.NonNull
import com.ardev.builder.project.Project
import com.ardev.idroid.ext.ProjectUtils
import com.ardev.idroid.ui.home.ProjectManager
import com.google.android.material.transition.MaterialFade
import android.view.ViewGroup
import androidx.transition.TransitionManager
import com.ardev.idroid.ui.main.model.Editable
import com.ardev.idroid.ui.main.adapter.PageAdapter
import com.ardev.idroid.databinding.ActivityMainBinding
import com.ardev.idroid.ui.main.adapter.FileListAdapter
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.ardev.idroid.ext.UtilKt
import com.ardev.idroid.app.MainViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.graphics.Insets
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ardev.component.widget.breadcrumbs.BreadcrumbsView
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.reflect.Type
import com.ardev.idroid.base.BaseActivity
import com.google.gson.Gson
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.List
import java.util.Arrays
import org.apache.commons.io.FileUtils

import java.util.concurrent.Executors
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import androidx.viewpager2.widget.CompositePageTransformer
import com.ardev.component.widget.breadcrumbs.DefaultBreadcrumbsCallback
import com.google.android.material.tabs.TabLayout
import com.ardev.f.storage.Storage
import com.ardev.component.widget.breadcrumbs.model.BreadcrumbItem
import com.ardev.idroid.R

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(), ProjectManager.OnProjectOpenListener {
	
	
private lateinit var mFilesAdapter: FileListAdapter 
private lateinit var mPagerAdapter: PageAdapter 
private lateinit var project: Project
private lateinit var mProjectManager: ProjectManager
private lateinit var mIndexServiceConnection: mIndexServiceConnection

var firstTimeInitTab = true
var firstTimeInitList = true

	
	override val viewmodel: MainViewModel by viewModels
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

	override fun onCreate(bundle: Bundle) {
		super.onCreate(bundle)
	MainViewModelProvider.init(viewModel)
	
	 project = Project(ProjectProvider.getRootFile()) 
	 mProjectManager = ProjectManager.getInstance()
     mProjectManager.addOnProjectOpenListener(this@MainActivity)
	  mIndexServiceConnection = IndexServiceConnection(viewModel)
    
		init()
		viewModel.setFatherPath(project.getRootFile().getPath())
		
	if (!project.equals(mProjectManager.getCurrentProject())) openProject(project)
	}
	
	private fun init() {
	with(binding) {
navView.addSystemWindowInsetToPadding(false, false, false, false)
pager2.addSystemWindowInsetToMargin(false, false, false, false)
pager2.addInsetTypeImeToPadding()

	setSupportActionBar(toolbar)
	supportActionBar.setDisplayHomeAsUpEnabled(true)
	
		val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawer, toolbar, R.string.app_name, R.string.app_name)
	
		toggle.syncState()
		toggle.setDrawerSlideAnimationEnabled(true)
		drawer.addDrawerListener(toggle)
		
		breadcrumbsView.setCallback { item, position -> 
		mainViewModel.setCurrentPath(getPath(position))
		 bkpListBreadcrumb(breadcrumbsView.getItems())
		
	}
}
		initRecyclerView()
		initTab()
		}
		
		private fun initRecyclerView(){
	with(binding) {
mFilesAdapter = FileListAdapter()
	with(list) {
 	adapter = mFilesAdapter
	setHasFixedSize(true)
	setItemAnimator(DefaultItemAnimator())
	}
mFilesAdapter.setOnClickListener { view, file -> 
			 if (file.isDirectory()) {
  	viewModel.addBreadcrumbItem(file.getPath())
	viewModel.setCurrentPath(file.getPath())
		} else {
	viewModel.addEditor(Editable(file, false))
drawer.closeDrawer(GravityCompat.START)
}
}

mFilesAdapter.setOnLongClickListener { v, file -> 

if (file.isDirectory()) showOptionsMenu(v, file)
else if((ProjectUtils.isLayoutXMLFile(file) || ProjectUtils.isValuesXMLFile(file)  || ProjectUtils.isDrawableXMLFile(file)) && file.isFile()) showFilesMenu(v, file)

}
 }
 
 
 }
 
		override fun observeViewModel() {
 	
 	viewModel.fatherPath.observe(this) { fatherPath -> 
       	supportActionBar.setTitle(File(fatherPath).getName())
       		
      val breadcrumbs = File(fatherPath + "/app/build/.cache", ".breadcrumbs")
      
if (breadcrumbs.exists()) {
	try {
  val listBreadCrumb: List<BreadcrumbItem> =  JsonUtils.jsonToList(breadcrumbs.readText(), BreadcrumbItem.class::java)
   listBreadCrumb.forEach{binding.breadcrumbsView.addItem(it)}

	} catch (e: Exception) {}
		
		} else {
	
	binding.breadcrumbsView.addItem(BreadcrumbItem.createSimpleItem(File(fatherPath).getName()))
	
	}
            }
            
	mainViewModel.currentPath.observe(this) { path -> 
	var _path: String = null
	if(firstTimeInitList) {
                firstTimeInitList = false
     val currentPathCache = File(viewModel.fatherPath.value + "/app/build/.cache", ".currentpath")
if (currentPathCache.exists()) {
	try {
   _path =  Gson().fromJson(currentPathCache.readText(), String.class::java)
   
	} catch (e: Exception) {} 
	} else {
	_path = path
	}
	} else {
	_path = path
	}
	showList(_path)
	bkpCurrentPath(_path)
	}
	
	
	
	
	mainViewModel.breadcrumbItem.observe(this) { file -> 
		val breadcrumbItem = BreadcrumbItem(viewModel.listDir().value)
		breadcrumbItem.setSelectedItem(file.getName())
  binding.breadcrumbsView.addItem(breadcrumbItem)
  bkpListBreadcrumb(binding.breadcrumbsView.getItems())
	}
		
	viewModel.listEditor
                .observe(this) { files -> 
                
                    mPagerAdapter.submitList(files)
                    binding.tab.setVisibility( if(files.isEmpty()) View.GONE else View.VISIBLE)
                }
                
   mainViewModel.currentTabPosition
                .observe(this) { position -> 
                if(firstTimeInitTab) {
                firstTimeInitTab = false
                 val positionFile = File(viewModel.fatherPath.value + "/app/build/.cache", ".position")
if (positionFile.exists()) {
	try {
  val pos: Int = Gson().fromJson(positionFile.readText(), Int.class::java)
  binding.pager2.setCurrentItem(pos, false)
		} catch (e: Exception) {} 
		} else {
			 binding.pager2.setCurrentItem(position, false)
			}
              } else {
                
             binding.pager2.setCurrentItem(position, false)
				
               }
                 }
                 
    viewModel.isIndexing
                .observe(this) { indexing -> 
                   
                }
                
        mainViewModel.currentState
                .observe(this) { message -> 
                supportActionBar.setSubtitle(message) 
                
                }
		}
		
private fun getPath(depth: Int): String {
	if (depth == -1) depth = binding.breadcrumbsView.getItems().size() - 1
	val sbPath =  StringBuffer(viewModel.fatherPath.value)
	for (int i = 1 i <= depth i++) {
		
		sbPath.append("/").append(v.breadcrumbsView.getItems().get(i).getSelectedItem())
	}
	return sbPath.toString()
}
 
 private fun showList(path: String ) {
   
    Executors.newSingleThreadExecutor().execute(() -> {
    
        List<File> list = st.getFiles(path)
         
          
       runOnUiThread(() -> {
     
	
                    
            if (list.isEmpty()) {
                v.scrollingView.setVisibility(View.GONE)
                v.emptyItem.setVisibility(View.VISIBLE)
            } else {
                v.scrollingView.setVisibility(View.VISIBLE)
                v.emptyItem.setVisibility(View.GONE)
                 mFilesAdapter.setList(list)
        mainViewModel.setListCurrent(list)
        }
        
            
	
	})
	})
	}

private fun showOptionsMenu(view: View, file: File) {
   
    val chars: Array<CharSequence> = arrayOf("Compartir", "Borrar")
	
	val dialog = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialAlertDialog_NoRounded)
		 
		dialog.setItems(chars) { d, item -> 

			 
		 }
		 dialog.show()
        
    }
private fun showFilesMenu(view: View, file: File) {
   
     val chars: Array<CharSequence> = arrayOf("Vista Previa", "Editar")
    
		val dialog = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialAlertDialog_NoRounded)
		 
		dialog.setItems(chars) { d, item -> 
		when (item) {
		 0 -> {
		viewModel.addEditor(Editable(file, true))
		binding.drawer.closeDrawer(GravityCompat.START)
			}
		1 -> {
		viewModel.addEditor(new Editable(file, false))
		binding.drawer.closeDrawer(GravityCompat.START)
			}
		}
			 
		 }
		 dialog.show()
        
    }
    
   private fun showTabMenu(tab: TabLayout.Tab) {
   val editable: Editable = viewModel.listEditor.value.get(tab.getPosition())
  val isPreview = editable.isPreview()
    val file = editable.getFile()
    
    val prev =  file.getName()

   val menu =  PopupMenu(this, tab.view)
	with(menu.getMenu()) {
		add("Cerrar")
		add("Cerrar otros")
		add("Cerrar todos")
		}
		
		menu.setOnMenuItemClickListener{ item -> 
			 when (item.getTitle().toString()) {

	 "Cerrar" -> viewModel.closeEditor(editable)
	"Cerrar otros" -> viewModel.closeOthersEditor(editable)
	"Cerrar todos" -> viewModel.closeAllEditor()
						
					 }
					 
					 true
				}
				menu.show()
   
   
   }
    
    
    
    private fun initTab() {
    
    
		
   mPagerAdapter = new PageAdapter(getSupportFragmentManager(), getLifecycle())
        v.pager2.setAdapter(mPagerAdapter)
        v.pager2.setUserInputEnabled(false)
		v.pager2.setPageTransformer((view, position) -> {
		view.setAlpha(0.2f)
		view.setVisibility(View.VISIBLE)
		view.animate().alpha(1.0f).setDuration(view.getResources().getInteger(android.R.integer.config_shortAnimTime))
		})
        
        v.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
           showTabMenu(tab)
               
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               mainViewModel.setCurrentTabPosition(tab.getPosition())

            }
        })
		
        new TabLayoutMediator(v.tab, v.pager2, true, false, (tab, i) -> {
			boolean isPreview = mainViewModel.getListEditor().getValue().get(i).isPreview()

			String fileName = mainViewModel.getListEditor().getValue().get(i).getFile().getName()
			
			tab.setText(fileName)
        	if(isPreview) tab.setIcon(R.drawable.ic_preview)
        	 
        	
        }).attach()
    
    
    
    }
    
    
     fun openProject(project: Project) {
        mIndexServiceConnection.setProject(project)
        viewModel.setIndexing(true)
        val intent = Intent(this@MainActivity, IndexService::class.java)
        startService(intent)
        bindService(intent, mIndexServiceConnection, Context.BIND_IMPORTANT)
    }
    
     override public void onProjectOpen(Project project) {
    
    
    }
    
    
   override fun onBackPressed() {
             if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
            } else {
            super.onBackPressed()
            }
	}
	
	

	  private void bkpListBreadcrumb(List<IBreadcrumbItem> bList) {
   try {
         File bciFile = new File(mainViewModel.getFatherPath().getValue() + "/app/build/.cache", ".breadcrumbs")
         String content  = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson( bList,new TypeToken<List<BreadcrumbItem>>(){}.getType())
        
        if(!bciFile.exists()) bciFile.createNewFile()
		if(!bList.isEmpty()){
        FileUtils.writeStringToFile(bciFile,
                 content,
                 Charset.defaultCharset())
                 }
                 } catch (Exception e) {}
   
   }
   
   private void bkpCurrentPath(String path) {
   try {
         File pathFile= new File(mainViewModel.getFatherPath().getValue() + "/app/build/.cache", ".currentpath")
        
        if(!pathFile.exists()) pathFile.createNewFile()
		
        FileUtils.writeStringToFile(pathFile,
                 new Gson().toJson(path) ,
                 Charset.defaultCharset())
                 
                 } catch (Exception e) {}
   
   
         }
	
	

	}