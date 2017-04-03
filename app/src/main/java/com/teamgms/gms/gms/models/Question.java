package com.teamgms.gms.gms.models;
import java.io.Serializable;

import io.realm.RealmObject;

public class Question extends RealmObject implements Serializable {
    private String userId;

    private Long num;
    private String question;

    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    private Long choice1Count;
    private Long choice2Count;
    private Long choice3Count;
    private Long choice4Count;

    private Boolean isEnd;
    private String endTime;
    private Long endCount;

    private Boolean isChecked;

    public Question() {
        choice1Count = new Long(0);
        choice2Count = new Long(0);
        choice3Count = new Long(0);
        choice4Count = new Long(0);
        this.isEnd = false;
        this.isChecked = false;
    }

    public Question(String userId, String question, String choice1, String choice2, String choice3, String choice4, Boolean isEnd, String endTime, long endCount) {
        this.userId = userId;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        choice1Count = new Long(0);
        choice2Count = new Long(0);
        choice3Count = new Long(0);
        choice4Count = new Long(0);
        this.isEnd = isEnd;
        this.endTime = endTime;
        this.endCount = new Long(endCount);
        this.isChecked = false;
    }
  
   public Question(Long num, String userId, String question, String choice1, String choice2, String choice3, String choice4, Long choice1Count, Long choice2Count, Long choice3Count, Long choice4Count, Boolean isEnd, String endTime, Long endCount) {
        this.num = num;
        this.userId = userId;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.choice1Count = choice1Count;
        this.choice2Count = choice2Count;
        this.choice3Count = choice3Count;
        this.choice4Count = choice4Count;
        this.isEnd = isEnd;
        this.endTime = endTime;
        this.endCount = endCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public Long getChoice1Count() {
        return choice1Count;
    }

    public void setChoice1Count(Long choice1Count) {
        this.choice1Count = choice1Count;
    }

    public Long getChoice2Count() {
        return choice2Count;
    }

    public void setChoice2Count(Long choice2Count) {
        this.choice2Count = choice2Count;
    }

    public Long getChoice3Count() {
        return choice3Count;
    }

    public void setChoice3Count(Long choice3Count) {
        this.choice3Count = choice3Count;
    }

    public Long getChoice4Count() {
        return choice4Count;
    }

    public void setChoice4Count(Long choice4Count) {
        this.choice4Count = choice4Count;
    }

    public Boolean getEnd() {
        return isEnd;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getEndCount() {
        return endCount;
    }

    public void setEndCount(Long endCount) {
        this.endCount = endCount;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}