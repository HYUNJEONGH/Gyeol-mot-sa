package com.teamgms.gms.gms.utils;

import com.google.firebase.database.DataSnapshot;
import com.teamgms.gms.gms.models.Question;

import java.util.HashMap;
import java.util.Map;

public class QuestionUtils {
    public static Map<String, Object> makeQuestionMap(Question question) {
        HashMap<String, Object> questionMap = new HashMap<String, Object>();

        questionMap.put("userId", question.getUserId());
        questionMap.put("num", question.getNum());
        questionMap.put("question", question.getQuestion());
        questionMap.put("choice1", question.getChoice1());
        questionMap.put("choice2", question.getChoice2());
        questionMap.put("choice3", question.getChoice3());
        questionMap.put("choice4", question.getChoice4());
        questionMap.put("choice1Count", question.getChoice1Count());
        questionMap.put("choice2Count", question.getChoice2Count());
        questionMap.put("choice3Count", question.getChoice3Count());
        questionMap.put("choice4Count", question.getChoice4Count());
        questionMap.put("isEnd", question.getEnd());
        questionMap.put("endTime", question.getEndTime());
        questionMap.put("endCount", question.getEndCount());
        questionMap.put("isChecked", question.getChecked());

        return questionMap;
    }

    public static Question parseQuestionDataSnapshot(DataSnapshot dataSnapshot) {
        Question question = new Question();

        question.setUserId((String)dataSnapshot.child("userId").getValue());
        question.setNum((Long)dataSnapshot.child("num").getValue());
        question.setQuestion((String)dataSnapshot.child("question").getValue());
        question.setChoice1((String)dataSnapshot.child("choice1").getValue());
        question.setChoice2((String)dataSnapshot.child("choice2").getValue());
        question.setChoice3((String)dataSnapshot.child("choice3").getValue());
        question.setChoice4((String)dataSnapshot.child("choice4").getValue());
        question.setChoice1Count((Long)dataSnapshot.child("choice1Count").getValue());
        question.setChoice2Count((Long)dataSnapshot.child("choice2Count").getValue());
        question.setChoice3Count((Long)dataSnapshot.child("choice3Count").getValue());
        question.setChoice4Count((Long)dataSnapshot.child("choice4Count").getValue());
        question.setEnd((Boolean)dataSnapshot.child("isEnd").getValue());
        question.setEndTime((String)dataSnapshot.child("endTime").getValue());
        question.setEndCount((Long)dataSnapshot.child("endCount").getValue());
        question.setChecked((Boolean)dataSnapshot.child("isChecked").getValue());

        return question;
    }
}
