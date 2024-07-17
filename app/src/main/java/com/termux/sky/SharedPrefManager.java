package com.termux.sky;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String PREF_NAME = "my_shared_prefs";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Save a string value to SharedPreferences with a specific key.
     *
     * @param key   The key for the value.
     * @param value The value to save.
     */
    public void saveText(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Retrieve a string value from SharedPreferences using a specific key.
     *
     * @param key The key for the value.
     * @return The value associated with the key, or an empty string if the key doesn't exist.
     */
    public String getText(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * Remove a value from SharedPreferences using a specific key.
     *
     * @param key The key for the value.
     */
    public void removeText(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * Clear all data from SharedPreferences.
     */
    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}
