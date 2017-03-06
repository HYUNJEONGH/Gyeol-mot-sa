package com.teamgms.gms.gms.controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.teamgms.gms.gms.models.Question;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hello_DE on 2017-03-04.
 */

public class QuestionController {
    private static final String TAG = QuestionController.class.getSimpleName();

    /*
    * not use transaction
    * */
    public static void updateChoiceNotUseTransaction(Question chkQuest) {
        Map<String, Object> updateValues = chkQuest.makeQuestionMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/questions/" + chkQuest.getNum(), updateValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    /*
    * use transaction
    * */
    public static void updateChoice(Question chkQuest) {
        final Question mChkQuest = chkQuest;

        DatabaseReference questionReference = FirebaseDatabase.getInstance().getReference().child("questions").child("question" + mChkQuest.getNum());

        questionReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Question question = mutableData.getValue(Question.class);

                if (question == null) {
                    return Transaction.success(mutableData);
                }

                question.choice1Count = mChkQuest.getChoice1Count();
                question.choice2Count = mChkQuest.getChoice2Count();
                question.choice3Count = mChkQuest.getChoice3Count();
                question.choice4Count = mChkQuest.getChoice4Count();

                // Set value and report transaction success
                mutableData.setValue(question);
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
}
