package com.teamgms.gms.gms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.teamgms.gms.gms.Config;

/**
 * Created by yunjeonghwang on 2017. 3. 18..
 */

public class AccountUtils {

    private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirebaseUid(final Context context, String uid) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(Config.SHARED_USERID, uid).apply();
    }

    public String getFirebaseUid(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(Config.SHARED_USERID, null);
    }
}
