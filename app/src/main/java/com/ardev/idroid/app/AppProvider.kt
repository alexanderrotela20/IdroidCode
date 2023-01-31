package com.ardev.idroid.app

import android.content.Context


object AppProvider {

    private lateinit var mContext: Context

    fun init(context: Context) {
        mContext = context.applicationContext
    }

    fun getApplicationContext() : Context {
        if(!this::mContext.isInitialized) throw IllegalStateException("init() has not been called yet in AppProvider.class")
        return mContext

    }
    }