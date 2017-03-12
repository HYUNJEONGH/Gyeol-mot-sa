package com.teamgms.gms.gms.controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.teamgms.gms.gms.models.Question;
import com.teamgms.gms.gms.models.ServerConfigure;
import com.teamgms.gms.gms.utils.QuestionUtils;

import java.util.HashMap;
import java.util.Map;

public class QuestionController {

    public static void insertQuestion(Question question) {
        final Map<String, Object> newQuestionValues = QuestionUtils.makeQuestionMap(question);

        Map<String, Object> newQuestionMap = new HashMap<String, Object>();
        newQuestionMap.put("/questions/question" + question.getNum(), newQuestionValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(newQuestionMap);
    }

}
