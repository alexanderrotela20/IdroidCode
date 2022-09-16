package com.ardev.idroid.ext;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import android.text.TextUtils;
import java.util.ArrayList;


public class JsonUtils {
	
public static <T> ArrayList<T> jsonToList(String json, Class<T> clazz) {
    if (TextUtils.isEmpty(json)) {
        return null;
    }
	
    Type type = new TypeToken<ArrayList<JsonObject>>() {
    }.getType();
    ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

    ArrayList<T> arrayList = new ArrayList<>();
    for (JsonObject jsonObject : jsonObjects) {
        arrayList.add(new Gson().fromJson(jsonObject, clazz));
    }
     
    return arrayList;
}

}