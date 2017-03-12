package com.teamgms.gms.gms.models;

public class ServerConfigure {
    private Long totalQuestionNumber;

    public ServerConfigure() {
        this.totalQuestionNumber = new Long(-1);
    }

    public Long getTotalQuestionNumber() {
        return totalQuestionNumber;
    }
    public void setTotalQuestionNumber(Long totalQuestionNumber) {
        this.totalQuestionNumber = totalQuestionNumber;
    }
}
