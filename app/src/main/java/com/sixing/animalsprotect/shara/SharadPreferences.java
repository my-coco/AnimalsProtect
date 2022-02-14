package com.sixing.animalsprotect.shara;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class SharadPreferences implements SharedPreferences {
    private volatile static SharadPreferences sharadPreferences;
    public SharadPreferences(String user_info, int modePrivate){}
    public static SharadPreferences getInstance(){
        if (sharadPreferences==null){
            synchronized (SharadPreferences.class){
                if (sharadPreferences==null){
                    sharadPreferences=new SharadPreferences("user_info", Context.MODE_PRIVATE);
                }
            }
        }
        return sharadPreferences;
    }

    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return null;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return null;
    }

    @Override
    public int getInt(String key, int defValue) {
        return 0;
    }

    @Override
    public long getLong(String key, long defValue) {
        return 0;
    }

    @Override
    public float getFloat(String key, float defValue) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return false;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }

    @Override
    public Editor edit() {
        return null;
    }

    public <T> void put(String key, T value){
        if (value instanceof String){
            sharadPreferences.edit().putString(key,(String) value);
        }else if (value instanceof Integer){
            sharadPreferences.edit().putInt(key,(Integer)value);
        }else if (value instanceof Float){
            sharadPreferences.edit().putFloat(key,(Float) value);
        }else if(value instanceof Boolean){
            sharadPreferences.edit().putBoolean(key,(Boolean) value);
        }else if (value instanceof Long){
            sharadPreferences.edit().putLong(key,(Long) value);
        }
        sharadPreferences.edit().commit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }
}
