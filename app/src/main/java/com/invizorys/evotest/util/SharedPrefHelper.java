package com.invizorys.evotest.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Roma on 26.02.2017.
 */

public class SharedPrefHelper {
    private static final String SETTINGS_FILE = "com.invizorys.evotest.settings";
    private static final String CATALOG_GRID_ON = "catalogGridOn";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static boolean isGridOn(Context context) {
        return getPreferences(context).getBoolean(CATALOG_GRID_ON, true);
    }

    public static void saveGridOn(Context context, boolean isGridOn) {
        getPreferences(context).edit().putBoolean(CATALOG_GRID_ON, isGridOn).apply();
    }

}
