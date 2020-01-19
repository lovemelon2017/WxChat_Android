package com.winderinfo.wechat.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JsonUtils {
    /**
     * json转换成类
     */
    public static <T> T parse(String data, Class<T> class1) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(java.sql.Date.class, new DateTypeAdapter())
                .create();
        return new Gson().fromJson(data, class1);
    }

    /**
     * json转换成列表
     */
    public static <T> List<T> parseList(String data, Class<T> class1) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        List<T> mList = new ArrayList<T>();
        try {
            JSONArray mArray = new JSONArray(data);
            final int size = mArray.length();
            for (int i = 0; i < size; i++) {
                T t = parse(mArray.getJSONObject(i).toString(), class1);
                mList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }

    /**
     * json转换成列表
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * json转换成列表
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static <T> ArrayList<T> json2List(String json, Class<T> clazz) {
        ArrayList<T> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(json)) {
            return arrayList;
        }

        try {
            Type type = new TypeToken<ArrayList<JsonObject>>() {
            }.getType();
            ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .registerTypeAdapter(java.sql.Date.class, new DateTypeAdapter())
                    .create();
            for (JsonObject jsonObject : jsonObjects) {
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
