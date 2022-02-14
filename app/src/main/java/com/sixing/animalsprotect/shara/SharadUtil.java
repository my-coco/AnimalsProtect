package com.sixing.animalsprotect.shara;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class SharadUtil implements SharedPreferences {
    private volatile static SharedPreferences sharadPreferences;
    public volatile static SharadUtil sharadUtil;
    private volatile static Editor editor;
    public static SharadUtil getInstance(Context context){
        if (sharadUtil==null){
            sharadUtil=new SharadUtil();
            sharadPreferences=context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
            editor=sharadPreferences.edit();
        }
        return sharadUtil;
    }

    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return sharadPreferences.getString(key,defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return sharadPreferences.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return sharadPreferences.getInt(key,defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharadPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharadPreferences.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharadPreferences.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return sharadPreferences.contains(key);
    }

    @Override
    public Editor edit() {
        return editor;
    }

    public <T> void put(String key, T value){
        if (value instanceof String){
            editor.putString(key,(String) value);
        }else if (value instanceof Integer){
            editor.putInt(key,(Integer)value);
        }else if (value instanceof Float){
            editor.putFloat(key,(Float) value);
        }else if(value instanceof Boolean){
            editor.putBoolean(key,(Boolean) value);
        }else if (value instanceof Long){
            editor.putLong(key,(Long) value);
        }
        editor.apply();
        editor.commit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }
}
