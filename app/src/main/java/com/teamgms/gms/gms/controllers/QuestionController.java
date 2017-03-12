package com.teamgms.gms.gms.controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.teamgms.gms.gms.models.NumberList;
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

        Log.v(TAG, "send data to server...");

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    /*
    * use transaction
    * */
    public static void updateChoice(Question chkQuest, NumberList numberList) {
        final Question mChkQuest = chkQuest;
        final NumberList mNumberList = numberList;

        Log.v(TAG, "numberList" + numberList.getNumList());
        Log.v(TAG, "mNumberList" + mNumberList.getNumList());

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

                //여기서 쓰는게 맞는지 onComplete안에서 쓰는게 맞는지 테스트 해보기...
                //NumController.updateNum(mChkQuest.userId, mChkQuest.num);

                // Set value and report transaction success
                mutableData.setValue(question);
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);

                //테스팅용
                //userid 유저 아이디 얻어오는 클래스 통해서 얻어오기!
                NumController.updateNum("123", mChkQuest.num, mNumberList);
            }
        });
    }
}