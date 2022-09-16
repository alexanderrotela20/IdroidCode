package com.ardev.idroid.ui.home.wizard;



import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class Template {
	

    public static Template fromFile(File parent) {
        if (!parent.exists()) {
            return null;
        }

        if (!parent.isDirectory()) {
            return null;
        }

        File infoFile = new File(parent, "data.json");
        if (!infoFile.exists()) {
            return null;
        }

       Template template = new Template();
        try {
            JSONObject jsonObject = new JSONObject(FileUtils.readFileToString(infoFile,
                    StandardCharsets.UTF_8));
             
                    
            template.setMinSdk(jsonObject.getInt("minSdk"));
            template.setName(jsonObject.getString("name"));
            template.setPath(parent.getAbsolutePath());
            template.setJavaSupport(jsonObject.getBoolean("isJavaSupported"));
           template.setKotlinSupport(jsonObject.getBoolean("isKotlinSupported"));

            return template;
        } catch (JSONException | IOException e) {
            return null;
        }
    }
    private String name;

    private int minSdk;

    private String path;
	private boolean isJava;
	private boolean isKotlin;
	
    public Template() {

    }

    public int getMinSdk() {
        return minSdk;
    }

    public void setMinSdk(int minSdk) {
        this.minSdk = minSdk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public void setJavaSupport(boolean isJava) {
    this.isJava = isJava;
    }
    
    public boolean isJavaSupported() {
    return isJava;
   
    }
     
      public void setKotlinSupport(boolean isKotlin) {
    this.isKotlin = isKotlin;
    }
    
    public boolean isKotlinSupported() {
   return isKotlin;
 
    }
    
    public List<String> getLanguageList() {
    List<String> list = new ArrayList<>();
    if(isJavaSupported()) list.add("Java");
    if(isKotlinSupported()) list.add("Kotlin");
    
    return list;
    }
    
    
    public List<String> getSdkList() {
            int min = getMinSdk();
            List<String> list = new ArrayList<>();  
            for ( int i = min; i <= 31; i++) {
               if(getSdk().containsKey(i)){
                   
                   list.add(getSdk().get(i));
               }
                
                
            }
            
            
            return list;
        }
    
    
    private HashMap<Integer, String> getSdk() {
    HashMap<Integer, String> sdkList = new HashMap<>();
         
        sdkList.put(21, "API 21: Android 5.0 (Lollipop)");
        sdkList.put(22, "API 22: Android 5.1 (Lollipop)");
        sdkList.put(23, "API 23: Android 6.0 (Marshmallow)");
        sdkList.put(24, "API 24: Android 7.0 (Nougat)");
        sdkList.put(25, "API 25: Android 7.1 (Nougat)");
        sdkList.put(26, "API 26: Android 8.0 (Oreo)");
        sdkList.put(27, "API 27: Android 8.1 (Oreo)");
        sdkList.put(28, "API 28: Android 9.0 (Pie)");
        sdkList.put(29, "API 29: Android 10.0 (Q)");
        sdkList.put(30, "API 30: Android 11.0 (R)");
        sdkList.put(31, "API 31: Android 12.0 (S)");
         
        
        return sdkList;
    }

}
