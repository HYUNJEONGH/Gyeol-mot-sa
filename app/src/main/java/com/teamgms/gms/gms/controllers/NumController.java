package com.teamgms.gms.gms.controllers;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamgms.gms.gms.models.NumberList;

import java.util.Map;

/**
 * Created by hello_DE on 2017-03-11.
 */

public class NumController {
    private static final String TAG = NumController.class.getSimpleName();
    private static DatabaseReference dbReference;
    private static DatabaseReference childReference;

    public static void updateNum(String userId, String num, NumberList numList) {
        dbReference =  FirebaseDatabase.getInstance().getReference();
        childReference = dbReference.child("userhistory").child(userId);

        final NumberList mNumberList = numList;

        Map<String, Object> updateValues = mNumberList.maekeNumListMap(num);

        Log.v(TAG, mNumberList.numList);
        Log.v(TAG, "send data to server...");


       childReference.updateChildren(updateValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference reference) {
                if(error == null) mNumberList.setFinish();
            }
        });
    }
}
