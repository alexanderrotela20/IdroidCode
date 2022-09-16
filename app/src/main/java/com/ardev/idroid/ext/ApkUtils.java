package com.ardev.idroid.ext;

import android.content.Intent;
import android.content.Context;
import static com.ardev.idroid.app.IdroidApplication.appContext;
import com.ardev.idroid.app.IdroidApplication;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public   class ApkUtils {





public static Drawable getApkIcon(File file) {
	
        if (file.isDirectory()) return null;
        PackageInfo info = IdroidApplication.appContext().getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo applicationInfo = info.applicationInfo;
            applicationInfo.sourceDir = file.getAbsolutePath();
            applicationInfo.publicSourceDir = file.getAbsolutePath();
            return applicationInfo.loadIcon(IdroidApplication.appContext().getPackageManager());
        }
        return null;
    }
    
    
    
    }