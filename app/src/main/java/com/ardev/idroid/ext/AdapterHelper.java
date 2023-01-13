package com.ardev.idroid.ext;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import java.io.IOException;
import java.net.URL;
import java.io.File;
import android.widget.ImageView;
import java.net.URLConnection;
import  com.ardev.idroid.R;

public class AdapterHelper {

public static void setFileIcon(ImageView icon, File file) {
	  String s = file.getAbsolutePath();
        String fileType = "";
        URL url = null;
		
        try {
            url = new URL("file://" + file.getPath());
            URLConnection connection = url.openConnection();
            fileType = connection.getContentType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.isDirectory()) icon.setImageResource(R.drawable.foldericon);
        else if (s.contains(".xml")) icon.setImageResource(R.drawable.xml);
        else if (s.contains(".java")) icon.setImageResource(R.drawable.java);
        else if (s.contains(".kt")) icon.setImageResource(R.drawable.kt);
        else if (s.contains(".apk")) {
               
                Glide.with(icon.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().override(100).encodeQuality(80))
                   // .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .load(ApkUtils.getApkIcon(file))
                    .error(R.drawable.apk)
                    .placeholder(R.drawable.apk)
                    .into(icon);
  
        }
        
       else if (fileType.contains("image/")) {
              
              Glide.with(icon.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().override(100).encodeQuality(80))
                   // .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .load(file)
                    .error(R.drawable.img)
                    .placeholder(R.drawable.img)
                    .into(icon);
              
              
              } else {
              icon.setImageResource(R.drawable.my);
	 

              
              }
             

        	 
	 
	 }
	 
	 
	 
	 
	 }
	