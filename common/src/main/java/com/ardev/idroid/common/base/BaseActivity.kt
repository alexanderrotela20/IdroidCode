package com.ardev.idroid.common.base

import android.os.*
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.viewbinding.ViewBinding
import com.ardev.idroid.app.AppActivity

abstract class BaseActivity<VB : ViewBinding>(val inflate: (LayoutInflater) -> VB) : AppActivity() {

	private val binding: VB by lazy { inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preSetContent()
        setContentView(binding.root)
        observeViewModel()
        onBackPressedDispatcher.addCallback(this, OnBackPressedCallbackInner(this))
    }
    
    
 internal class OnBackPressedCallbackInner(baseActivity: BaseActivity) : OnBackPressedCallback(true) {
    private val activity: WeakReference<BaseActivity> = WeakReference(baseActivity)

    override fun handleOnBackPressed() {
        activity.get()?.onBackEvent() ?: return
    }
}

	abstract fun onBackEvent() 
	protected open fun preSetContent() {}
	protected open fun observeViewModel() {}

}