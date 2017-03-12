//Test용 Activity
package com.teamgms.gms.gms.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.QuestionController;
import com.teamgms.gms.gms.models.Question;

public class TestActivity extends AppCompatActivity {

    Question question;

    TextView tvQuestionTest;
    RadioButton rbChoice1;
    RadioButton rbChoice2;
    RadioButton rbChoice3;
    RadioButton rbChoice4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvQuestionTest = (TextView)findViewById(R.id.tv_questionTest);
        rbChoice1 = (RadioButton)findViewById(R.id.rb_choice1);
        rbChoice2 = (RadioButton)findViewById(R.id.rb_choice2);
        rbChoice3 = (RadioButton)findViewById(R.id.rb_choice3);
        rbChoice4 = (RadioButton)findViewById(R.id.rb_choice4);

        question = (Question)(getIntent().getSerializableExtra("question"));

        tvQuestionTest.setText(question.getQuestion());
        rbChoice1.setText(question.getChoice1());
        rbChoice2.setText(question.getChoice2());
        rbChoice3.setText(question.getChoice3());
        rbChoice4.setText(question.getChoice4());
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_sendChoice:
                if(rbChoice1.isChecked())
                    question.setChoice1Count(question.getChoice1Count() + 1);
                if(rbChoice2.isChecked())
                    question.setChoice2Count(question.getChoice2Count() + 1);
                if(rbChoice3.isChecked())
                    question.setChoice3Count(question.getChoice3Count() + 1);
                if(rbChoice4.isChecked())
                    question.setChoice4Count(question.getChoice4Count() + 1);

                QuestionController.insertQuestion(question);

                finish();
        }
    }
}
