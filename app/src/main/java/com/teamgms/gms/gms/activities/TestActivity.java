package com.teamgms.gms.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.QuestionController;

/**
 * 테스트용 질문 올리기...
 */
public class TestActivity extends AppCompatActivity {
    private final String TAG = TestActivity.class.getSimpleName();

    private String userId;

    EditText et_question;
    EditText et_choice1;
    EditText et_choice2;
    EditText et_choice3;
    EditText et_choice4;
    EditText et_endTime;
    EditText et_endCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        et_question = (EditText)findViewById(R.id.et_question);
        et_choice1 = (EditText)findViewById(R.id.et_choice1);
        et_choice2 = (EditText)findViewById(R.id.et_choice2);
        et_choice3 = (EditText)findViewById(R.id.et_choice3);
        et_choice4 = (EditText)findViewById(R.id.et_choice4);
        et_endTime = (EditText)findViewById(R.id.et_endTime);
        et_endCount = (EditText)findViewById(R.id.et_endCount);

        //userId 가져오기
        userId = "...";
    }

    public void onClick(View v) {
        if(userId == null || userId.equals("")) {
            Log.v(TAG, "user Id error....");
        }

        QuestionController.createQuestion(new String("0"),
                userId,
                et_question.getText().toString(),
                et_choice1.getText().toString(),
                et_choice2.getText().toString(),
                et_choice3.getText().toString(),
                et_choice4.getText().toString(),
                new Long(0),
                new Long(0),
                new Long(0),
                new Long(0),
                new Boolean(false),
                et_endTime.getText().toString(),
                Long.parseLong(et_endCount.getText().toString()));

        Log.v(TAG, "send data to server...");

        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }

}
