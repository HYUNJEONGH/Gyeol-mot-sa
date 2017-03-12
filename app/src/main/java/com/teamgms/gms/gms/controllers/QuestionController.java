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
import com.teamgms.gms.gms.utils.QuestionUtils;

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
        Map<String, Object> updateValues = QuestionUtils.makeQuestionMap(chkQuest);
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

        Log.v(TAG, "mNumberList" + mNumberList.getNumList());

        DatabaseReference questionReference = FirebaseDatabase.getInstance().getReference().child("questions").child("question" + mChkQuest.getNum().toString());

        questionReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Question question = mutableData.getValue(Question.class);

                if (question == null) {
                    return Transaction.success(mutableData);
                }

                question.setChoice1Count(mChkQuest.getChoice1Count());
                question.setChoice2Count(mChkQuest.getChoice2Count());
                question.setChoice3Count(mChkQuest.getChoice3Count());
                question.setChoice4Count(mChkQuest.getChoice4Count());

                // Set value and report transaction success
                mutableData.setValue(question);
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);


                //userid 유저 아이디 얻어오는 클래스 통해서 얻어오기!
                NumController.updateNum("tempUserId", mChkQuest.getNum().toString(), mNumberList);
            }
        });
    }
    public static void insertQuestion(Question question) {
        final Map<String, Object> newQuestionValues = QuestionUtils.makeQuestionMap(question);

        Map<String, Object> newQuestionMap = new HashMap<String, Object>();
        newQuestionMap.put("/questions/question" + question.getNum(), newQuestionValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(newQuestionMap);
    }

    public static DatabaseReference receiveAllQuestions() {
        return FirebaseDatabase.getInstance().getReference().child("questions");
    }
}

