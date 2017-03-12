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
import com.teamgms.gms.gms.models.NumberList;
import com.teamgms.gms.gms.models.Question;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private String mFirebaseUid;
    private ArrayList<String> numList;
    private NumberList numberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UserUtil 클래스가 아직 없어서 주석처리.
        //mFirebaseUid = UserUtil.loadUserFirebaseUid(this);

        //테스트용
        mFirebaseUid = "123";

        numList = new ArrayList<String> ();
        numberList = new NumberList();
        numberList.setNumList("");

        //numberList.setNFinish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getResponsedNum();
    }

    /**
     * 사용자가 이전에 답변했던 질문 num리스트를 가져옴.
     */
    public void getResponsedNum() {
        DatabaseReference userIdReference = FirebaseDatabase.getInstance().getReference().child("userhistory").child(mFirebaseUid);

        ValueEventListener userIdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nums = (String)dataSnapshot.child("nums").getValue();

                if(nums != null) {
                    Log.v(TAG, "numList setting...");
                    numberList.setNumList(nums);

                    StringTokenizer st = new StringTokenizer(nums, "%");

                    while (st.hasMoreTokens()) {
                        numList.add(st.nextToken());
                    }
                }
                else {
                    Log.v(TAG, "numList is null...");
                }

                getQuestion();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userIdReference.addValueEventListener(userIdListener);
    }

    /**
     * db내 질문들 가져옴.
     */
    public void getQuestion() {
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

                    if(!question.userId.equals(mFirebaseUid)) {
                        for (int i = 0; i < numList.size(); i++) {
                            if (question.num.equals(numList.get(i))) {
                                continue label;
                            }
                        }
                        if (!question.isEnd) {
                            if (question != null) {
                                intent.putExtra("question", question);
                                intent.putExtra("numberList", numberList);
                                Log.d(TAG, "GET QUESTION, START ACTIVITY");
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        questionReference.addListenerForSingleValueEvent(questionListener);
    }
}