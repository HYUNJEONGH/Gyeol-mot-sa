package com.teamgms.gms.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.models.Question;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    *
    * */
    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = new Intent(this, SendChoice.class);

        DatabaseReference questionReference = FirebaseDatabase.getInstance().getReference().child("questions").child("question");

        ValueEventListener questionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question question = Question.parseQuestionSnapshot(dataSnapshot);

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    question =  Question.parseQuestionSnapshot(child);
                    if(!question.isEnd) break;
                }

                intent.putExtra("question", question);
                Log.d(TAG, "GET QUESTION, START ACTIVITY");
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        questionReference.addValueEventListener(questionListener);
    }
}
