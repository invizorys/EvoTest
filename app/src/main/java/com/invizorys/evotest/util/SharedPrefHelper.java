package com.invizorys.evotest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.invizorys.evotest.Constants;

/**
 * Created by Roma on 26.02.2017.
 */

public class SharedPrefHelper {
    private static final String SETTINGS_FILE = "com.invizorys.evotest.settings";
    private static final String CATALOG_GRID_ON = "catalogGridOn";
    private static final String SORT_TYPE = "sortType";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static boolean isGridOn(Context context) {
        return getPreferences(context).getBoolean(CATALOG_GRID_ON, true);
    }

    public static void saveGridOn(Context context, boolean isGridOn) {
        getPreferences(context).edit().putBoolean(CATALOG_GRID_ON, isGridOn).apply();
    }

    public static void saveSortType(Context context, String sortType) {
        getPreferences(context).edit().putString(SORT_TYPE, sortType).apply();
    }

    public static String getSortType(Context context) {
        return getPreferences(context).getString(SORT_TYPE, Constants.SORT_ASCENDING_PRICE);
    }

}
