package com.teamgms.gms.gms.controllers;

import android.os.Handler;
import android.os.Message;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.teamgms.gms.gms.activities.SendQuestionActivity;
import com.teamgms.gms.gms.models.ServerConfigure;
import com.teamgms.gms.gms.utils.ServerConfigureUtils;

public class ServerConfigureController {
    public static void setServerConfigure(Handler serverConfigureReceiveHandler) {
        final DatabaseReference serverConfigureReference = FirebaseDatabase.getInstance().getReference().child("serverConfigure");
        final Handler serverConfigureDeliveryHandler = serverConfigureReceiveHandler;

        serverConfigureReference.runTransaction(new Transaction.Handler() {
            ServerConfigure serverConfigure = null;

            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                serverConfigure = mutableData.getValue(ServerConfigure.class);
                if(serverConfigure == null)
                    return Transaction.success(mutableData);

                Long totalQuestionNumber = serverConfigure.getTotalQuestionNumber();
                serverConfigure.setTotalQuestionNumber(++totalQuestionNumber);

                mutableData.setValue(serverConfigure);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Message msg = serverConfigureDeliveryHandler.obtainMessage();
                msg.what = SendQuestionActivity.FLAG_GET_SERVERCONFIGURE;
                msg.obj = serverConfigure;

                serverConfigureDeliveryHandler.sendMessage(msg);
            }
        });
    }
}
