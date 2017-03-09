package com.teamgms.gms.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.models.Question;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //testing
    public void onClick(View v) {
        if(v.getId() == R.id.btn_test) {
            Intent i = new Intent(this, TestActivity.class);
            Log.d(TAG, "TEST START");
            startActivity(i);
        }
    }

    /*
    *
    * */
    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = new Intent(this, SendChoice.class);

        DatabaseReference questionReference = FirebaseDatabase.getInstance().getReference().child("questions");

        ValueEventListener questionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question question = null;

                Log.v(TAG, "rcv data....");

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    question = Question.parseQuestionSnapshot(child);

                    Log.v(TAG, "is end :" + question.isEnd);
                    if(!question.isEnd) break;
                }

                if(question != null) {
                    intent.putExtra("question", question);
                    Log.d(TAG, "GET QUESTION, START ACTIVITY");
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //testing
        questionReference.addListenerForSingleValueEvent(questionListener);
        //questionReference.addValueEventListener(questionListener);
    }

}
