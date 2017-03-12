package com.teamgms.gms.gms.models;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hello_DE on 2017-03-12.
 */

public class NumberList implements Serializable {
    private static final String TAG = NumberList.class.getSimpleName();

    public interface onSendNumCB {
        public void onSendNum(boolean finish);
    }

    public String numList;
    private onSendNumCB numCallBack;

    public NumberList() {
    }

    public NumberList(String numList) {
        this.numList = numList;
    }

    public void setOnSendNumCB(onSendNumCB callBack) {
        numCallBack = callBack;
    }

    public void setFinish() {
        numCallBack.onSendNum(true);
    }

    public void setNFinish() {
        numCallBack.onSendNum(false);
    }

    public String getNumList() {
        return numList.toString();
    }

    public void setNumList(String numList) {
        this.numList = numList;
    }

    public Map<String, Object> maekeNumListMap(String num) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        if(numList.equals(""))  {
            Log.v(TAG, "first responsed num setting....");
            numList = num;
        }
        else {
            Log.v(TAG, "responsed num setting....");
            numList = numList + "%" + num;
        }

        result.put("nums", numList);

        return result;
    }
}
