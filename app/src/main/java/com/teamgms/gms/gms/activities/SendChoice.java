package com.teamgms.gms.gms.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.QuestionController;
import com.teamgms.gms.gms.models.Question;

/**
 *  Select a decision and send it to the server.
 */
public class SendChoice extends AppCompatActivity {
    private final String TAG = SendChoice.class.getSimpleName();

    private RadioGroup radioGroup_answer;
    private RadioButton rb_one;
    private RadioButton rb_two;
    private RadioButton rb_three;
    private RadioButton rb_four;
    private TextView tv_question;

    Question chkQuest;
    private int choice; //user decision
    private Long count;  //total user decision

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_choice);

        chkQuest = (Question)getIntent().getSerializableExtra("question");

        if(chkQuest == null) {
            Log.v(TAG, "intent error...");
            finish();
        }

        //Define and initialize
        radioGroup_answer = (RadioGroup)findViewById(R.id.radioGroup_answer);
        radioGroup_answer.setOnCheckedChangeListener(choiceChange);

        rb_one = (RadioButton)findViewById(R.id.rb_one);
        rb_two = (RadioButton)findViewById(R.id.rb_two);
        rb_three = (RadioButton)findViewById(R.id.rb_three);
        rb_four = (RadioButton)findViewById(R.id.rb_four);

        tv_question = (TextView)findViewById(R.id.tv_question);

        choice = 0;
        tv_question.setText(chkQuest.getQuestion());
        rb_one.setText(chkQuest.choice1);
        rb_two.setText(chkQuest.choice2);
        rb_three.setText(chkQuest.choice3);
        rb_four.setText(chkQuest.choice4);
    }

    /**
     * operates when the user selects the radio button.
     */
    protected RadioGroup.OnCheckedChangeListener choiceChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId) {
                case R.id.rb_one :
                    choice = 1;
                    break;
                case R.id.rb_two :
                    choice = 2;
                    break;
                case R.id.rb_three :
                    choice = 3;
                    break;
                case R.id.rb_four :
                    choice = 4;
                    break;
            }
            Log.v(TAG, "user decision : " + choice);
        }
    };

    /*
   * operates when the user presses send button.
   * */
    public void onClick(View v) {
        if(v.getId() == R.id.btn_send) {
            if(choice != 0) {
                switch (choice) {
                    case 1:
                        count = chkQuest.choice1Count;
                        count = count+ 1;
                        chkQuest.choice1Count = count;
                        break;
                    case 2:
                        count = chkQuest.choice2Count;
                        count = count+ 1;
                        chkQuest.choice2Count = count;
                        break;
                    case 3:
                        count = chkQuest.choice3Count;
                        count = count+ 1;
                        chkQuest.choice3Count = count;
                        break;
                    case 4:
                        count = chkQuest.choice4Count;
                        count = count+ 1;
                        chkQuest.choice4Count = count;
                        break;
                }
                count = count+ 1;

                QuestionController.updateChoice(chkQuest);

                finish();
            }
        }
    }
}
