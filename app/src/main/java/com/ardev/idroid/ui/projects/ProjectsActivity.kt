package com.ardev.idroid.ui.projects

class ProjectsActivity : BaseActivity<ActivityProjectsBinding>(ActivityProjectsBinding::inflate) {

    private lateinit var viewModel: ProjectsViewModel by viewModels
    private val adapter: ProjectsAdapter by lazy { ProjectsAdapter() }
    
    override fun preSetContent() {
        installSplashScreen().setOnExitAnimationListener {
            it.remove()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           
        setupRecyclerView()
        setupFab()    
    }
    
    private fun setupRecyclerView() {
        binding.list.adapter = adapter
        binding.list.setHasFixedSize(true)
        binding.list.itemAnimator = DefaultItemAnimator()
        adapter.setOnItemClick { project ->
            viewModel.openProject(project)
        }
    }
    
    private fun setupFab() {
        binding.fab.addSystemWindowInsetToMargin(bottom = true)
        binding.fab.setOnClickListener {
            WizardFragment.newInstance().apply {
            setOnProjectCreated { project ->
              viewModel.loadProjects()
              viewModel.openProject(project, true) 
            }
            }.also {
             supportFragmentManager.showFragment(it)
            }
        }
    }

    override fun observeViewModel() {
       viewModel.loadProjects.observe(viewLifecycleOwner) { state ->
         TransitionManager.beginDelayedTransition(
                binding.list.parent as ViewGroup, MaterialFade()
            )
            when(state) {
                is Resource.Success -> {
                   if (state.data.isNullOrEmpty()) {
                    manageViewVisibility(UiState.ERROR)
                      } else {
                    manageViewVisibility(UiState.SUCCESS)
                    adapter.submitList(state.data)
                      }
                }
                is Resource.Error -> {
                manageViewVisibility(UiState.ERROR)
                }
                is Resource.Loading -> {
                    manageViewVisibility(UiState.LOADING)
                }
                else -> Unit
            }
        }
        
        viewModel.openProject.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                ProjectProvider.init(state.data)
                  launchActivity<MainActivity>()
                }
                is Resource.Error -> {
                
                }
                else -> Unit
            }
        }
        
    
    }
   
    private fun manageViewVisibility(currentState: UiState) {
        when (currentState) {
            UiState.LOADING -> {
               binding.emptyItem.visibility = View.GONE
               binding.scrollingView.visibility = View.GONE
               binding.loadingList.visibility = View.VISIBLE
            }
            UiState.SUCCESS -> {
               binding.loadingList.visibility = View.GONE
               binding.emptyItem.visibility = View.GONE
               binding.scrollingView.visibility = View.VISIBLE 
            }
            UiState.ERROR -> {
               binding.loadingList.visibility = View.GONE
               binding.scrollingView.visibility = View.GONE
               binding.emptyItem.visibility = View.VISIBLE
            }
        }        
    }
    
}