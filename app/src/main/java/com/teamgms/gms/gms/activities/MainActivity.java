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
import com.google.firebase.database.FirebaseDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = "tempUserId";
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_sendQuestionActivity:
                Intent intent = new Intent(this, SendQuestionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
