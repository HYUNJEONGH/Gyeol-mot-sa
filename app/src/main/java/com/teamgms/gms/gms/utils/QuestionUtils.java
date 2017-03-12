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
        questionMap.put("isEnd", question.getIsEnd());
        questionMap.put("endTime", question.getEndTime());
        questionMap.put("endCount", question.getEndCount());
        questionMap.put("isChecked", question.getIsChecked());

        return questionMap;
    }
}
