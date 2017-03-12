package com.teamgms.gms.gms.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamgms.gms.gms.BaseActivity;
import com.teamgms.gms.gms.Config;
import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.QuestionController;
import com.teamgms.gms.gms.controllers.ServerConfigureController;
import com.teamgms.gms.gms.models.NumberList;
import com.teamgms.gms.gms.models.Question;
import com.teamgms.gms.gms.models.ServerConfigure;
import com.teamgms.gms.gms.models.User;
import com.teamgms.gms.gms.utils.QuestionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final String TAG = MainActivity.class.getSimpleName();
    private static String userId;
    private ArrayList<String> numList;
    private NumberList numberList;
    final static int FLAG_GET_SERVERCONFIGURE = 100;
    long totalQuestionNumber = 0;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.Toolbar)
    Toolbar toolbar;

    private CharSequence mTitle, mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    Button btnSignOut, btnLogin;
    Unbinder unbinder;
    Bundle bundle;
    private User userInfo;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numList = new ArrayList<String> ();
        numberList = new NumberList();
        //numberList.setNFinish();

        userId = "tempUserId";

        ServerConfigureController.getServerConfigure(serverConfigureHandler);
      
        setSupportActionBar(toolbar);
        unbinder = ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        if(bundle != null) {
          userInfo = (User)bundle.getSerializable(Config.USER);
        }

        //home activity navigation drawer

//        mTitle = mDrawerTitle = getTitle();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,
                toolbar,/* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
            );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        updateHeaderView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getResponsedNum();

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
//                    else if (!(updatedQuestion.getUserId()).equals(userId) && !updatedQuestion.getIsEnd()) {
//                        Intent intent = new Intent(MainActivity.this, TestActivity.class);
//                        intent.putExtra("question", updatedQuestion);
//                        startActivity(intent);
//                    }
                }
            }
              @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        changedQuestionReference.addValueEventListener(receiveUpdatedQuestionsListener);
    }

    /**
     * 사용자가 이전에 답변했던 질문 num리스트를 가져옴.
     */
    public void getResponsedNum() {
        DatabaseReference userIdReference = FirebaseDatabase.getInstance().getReference().child("userhistory").child(userId);

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
                    question = QuestionUtils.parseQuestionDataSnapshot(child);

                    if(!question.getUserId().equals(userId)) {
                        String num = question.getNum().toString();
                        for (int i = 0; i < numList.size(); i++) {
                            if (num.equals(numList.get(i))) {
                                continue label;
                            }
                        }
                        if (!question.getIsEnd()) {
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

    public void updateHeaderView(){
        LinearLayout mHeader = (LinearLayout)navigationView.getHeaderView(0);
        btnSignOut = ButterKnife.findById(mHeader, R.id.btnSignOut);
        btnLogin = ButterKnife.findById(mHeader, R.id.btnLogin);
        TextView tvName = ButterKnife.findById(mHeader, R.id.tvNickname);
        ImageView imgPhoto = ButterKnife.findById(mHeader, R.id.imgPhoto);
        tvName.setText(userInfo.userName);
        Glide.with(this).load(userInfo.profilePictureUrl).into(imgPhoto);
        btnSignOut.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                btnSignOut.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin(MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_myPage) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
