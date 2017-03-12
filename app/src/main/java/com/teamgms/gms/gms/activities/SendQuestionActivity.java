package com.teamgms.gms.gms.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.QuestionController;
import com.teamgms.gms.gms.controllers.ServerConfigureController;
import com.teamgms.gms.gms.models.Question;
import com.teamgms.gms.gms.models.ServerConfigure;

public class SendQuestionActivity extends AppCompatActivity {

    private static String userId;

    EditText etQuestion;
    EditText etChoice1;
    EditText etChoice2;
    EditText etChoice3;
    EditText etChoice4;
    EditText etEndTime;
    EditText etEndCount;

    Question question;

    final public static int FLAG_GET_SERVERCONFIGURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_question);

        etQuestion = (EditText)findViewById(R.id.et_question);
        etChoice1 = (EditText)findViewById(R.id.et_choice1);
        etChoice2 = (EditText)findViewById(R.id.et_choice2);
        etChoice3 = (EditText)findViewById(R.id.et_choice3);
        etChoice4 = (EditText)findViewById(R.id.et_choice4);
        etEndTime = (EditText)findViewById(R.id.et_endTime);
        etEndCount = (EditText)findViewById(R.id.et_endCount);

        userId = "tempUserId";
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_sendQuestion:
                if(userId == null || userId.equals("")) {
                    //Error: No userId
                }

                question = new Question(userId,
                        etQuestion.getText().toString(),
                        etChoice1.getText().toString(),
                        etChoice2.getText().toString(),
                        etChoice3.getText().toString(),
                        etChoice4.getText().toString(),
                        new Boolean(false),
                        etEndTime.getText().toString(),
                        Long.parseLong(etEndCount.getText().toString()));

                ServerConfigureController.setServerConfigure(serverConfigureReceiveHandler);

                etQuestion.setText("");
                etChoice1.setText("");
                etChoice2.setText("");
                etChoice3.setText("");
                etChoice4.setText("");
                etEndTime.setText("");
                etEndCount.setText("");

                finish();

                break;
            case R.id.btn_closeActivity:
                finish();
        }
    }

    Handler serverConfigureReceiveHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case FLAG_GET_SERVERCONFIGURE:
                    ServerConfigure serverConfigure = (ServerConfigure)msg.obj;

                    question.setNum(serverConfigure.getTotalQuestionNumber());
                    QuestionController.insertQuestion(question);
                    break;
            }
        }
    };
}
