package com.teamgms.gms.gms.controllers;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hello_DE on 2017-03-11.
 */

public class NumController {
    private static final String TAG = NumController.class.getSimpleName();

    private static String numList;

    public static void setNum(String nums) {
        numList = nums;
    }

    public static void updateNum(String userId, String num) {
        Log.v(TAG, numList);

        Map<String, Object> updateValues = new HashMap<String, Object>();
        updateValues.put("nums", numList + "%" + num);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/userhistory/" + userId, updateValues);

        Log.v(TAG, "send data to server...");

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }
}
