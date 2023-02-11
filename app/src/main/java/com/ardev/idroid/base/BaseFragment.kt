package com.ardev.idroid.base

import android.view.MenuInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewbinding.ViewBinding
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle


	abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

	private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       
requireActivity().addMenuProvider( object: MenuProvider {
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
	menu.clear()
	setMenu(menu)
 }
			override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        
     observeViewModel() 
    

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    	_binding =  getViewBinding(inflater, container)
        return binding.root  
    }
	override fun onDestroy() {
	super.onDestroy()
	_binding = null
	}

	protected fun setMenu(menu: Menu) {}
	protected open fun observeViewModel() {}
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB




}