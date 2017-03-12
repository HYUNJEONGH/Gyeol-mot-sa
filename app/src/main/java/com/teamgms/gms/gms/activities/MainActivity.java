package com.teamgms.gms.gms.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.QuestionController;
import com.teamgms.gms.gms.controllers.ServerConfigureController;
import com.teamgms.gms.gms.models.Question;
import com.teamgms.gms.gms.models.ServerConfigure;
import com.teamgms.gms.gms.utils.QuestionUtils;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static String userId;

    final static int FLAG_GET_SERVERCONFIGURE = 100;
    long totalQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = "tempUserId";

        ServerConfigureController.getServerConfigure(serverConfigureHandler);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference changedQuestionReference = QuestionController.receiveAllQuestions();

        ValueEventListener receiveUpdatedQuestionsListener = new ValueEventListener() {
            Question updatedQuestion = null;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator updatedQuestionIterator = dataSnapshot.getChildren().iterator();

                while(updatedQuestionIterator.hasNext()) {
                    updatedQuestion = QuestionUtils.parseQuestionDataSnapshot((DataSnapshot)updatedQuestionIterator.next());

                    if(updatedQuestion.getChoice1Count() + updatedQuestion.getChoice2Count() + updatedQuestion.getChoice3Count() + updatedQuestion.getChoice4Count() == updatedQuestion.getEndCount())
                        updatedQuestion.setIsEnd(new Boolean(true));

                    if ((updatedQuestion.getUserId()).equals(userId) && updatedQuestion.getIsEnd() && !updatedQuestion.getIsChecked()) {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("result", updatedQuestion);
                        startActivity(intent);
                    }

                    //Test용
                    else if (!(updatedQuestion.getUserId()).equals(userId) && !updatedQuestion.getIsEnd()) {
                        Intent intent = new Intent(MainActivity.this, TestActivity.class);
                        intent.putExtra("question", updatedQuestion);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        changedQuestionReference.addValueEventListener(receiveUpdatedQuestionsListener);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_sendQuestionActivity:
                Intent intent = new Intent(this, SendQuestionActivity.class);
                startActivity(intent);
                break;
        }
    }

    Handler serverConfigureHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case FLAG_GET_SERVERCONFIGURE:
                    ServerConfigure serverConfigure = (ServerConfigure)msg.obj;
                    totalQuestionNumber = serverConfigure.getTotalQuestionNumber();
                    break;
            }
        }
    };
}
