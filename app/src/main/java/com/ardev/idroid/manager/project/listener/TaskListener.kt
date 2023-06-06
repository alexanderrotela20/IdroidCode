package com.ardev.idroid.manager.project.listener

import com.ardev.builder.project.Project

interface TaskListener {
        fun onStarted(message: String)
        fun onCompleted(project: Project, success: Boolean, message: String)
    }