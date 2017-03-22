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
    private static DatabaseReference numReference;

    public static void updateNum(String num, NumberList numList) {
        final NumberList mNumberList = numList;

        numReference =  FirebaseDatabase.getInstance().getReference().child("userhistory").child(mNumberList.getUserId());

        Map<String, Object> updateValues = mNumberList.maekeNumListMap(num);

        Log.v(TAG, "send data to server...");


        numReference.updateChildren(updateValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference reference) {
                if(error == null) mNumberList.setFinish();
            }
        });
    }
}
