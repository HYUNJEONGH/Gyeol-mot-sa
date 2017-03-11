package com.teamgms.gms.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.NumController;
import com.teamgms.gms.gms.models.Question;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private String mFirebaseUid;
    private ArrayList<String> numList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UserUtil 클래스가 아직 없어서 주석처리.
        //mFirebaseUid = UserUtil.loadUserFirebaseUid(this);

        numList = new ArrayList<String> ();

        getResponsedNum();
    }

    //testing
    public void onClick(View v) {
        if(v.getId() == R.id.btn_test) {
            Intent i = new Intent(this, TestActivity.class);
            Log.d(TAG, "TEST START");
            startActivity(i);
        }
    }

    public void getResponsedNum() {
        DatabaseReference userIdReference = FirebaseDatabase.getInstance().getReference().child("userhistory").child(mFirebaseUid);

        ValueEventListener userIdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nums = (String)dataSnapshot.child("nums").getValue();
                NumController.setNum(nums);

                StringTokenizer st = new StringTokenizer(nums, "%");

                while(st.hasMoreTokens()) {
                    numList.add(st.nextToken());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userIdReference.addListenerForSingleValueEvent(userIdListener);
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

                label:
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    question = Question.parseQuestionSnapshot(child);

                    Log.v(TAG, "is end :" + question.isEnd);

                    for (int i = 0; i < numList.size(); i++) {
                        if (question.num.equals(numList.get(i))) {
                            continue label;
                        }
                    }

                    if (!question.isEnd) break;
                }

                if (question != null) {
                    intent.putExtra("question", question);
                    Log.d(TAG, "GET QUESTION, START ACTIVITY");
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //테스팅
        questionReference.addListenerForSingleValueEvent(questionListener);
        //questionReference.addValueEventListener(questionListener);
    }
}