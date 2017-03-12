//2번
package com.teamgms.gms.gms.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.controllers.QuestionController;
import com.teamgms.gms.gms.models.Question;

public class ResultActivity extends AppCompatActivity {

    private static String userId = "";
    Question result = null;

    TextView tvQuestion;
    TextView tvChoice1;
    TextView tvChoice2;
    TextView tvChoice3;
    TextView tvChoice4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvQuestion = (TextView)findViewById(R.id.tv_question);
        tvChoice1 = (TextView)findViewById(R.id.tv_choice1);
        tvChoice2 = (TextView)findViewById(R.id.tv_choice2);
        tvChoice3 = (TextView)findViewById(R.id.tv_choice3);
        tvChoice4 = (TextView)findViewById(R.id.tv_choice4);

        userId = "tempUserId";

        Intent intent = getIntent();
        result = (Question)intent.getSerializableExtra("result");

        if(result == null) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            finish();
        }

        tvQuestion.setText(result.getQuestion());
        tvChoice1.setText(result.getChoice1());
        tvChoice2.setText(result.getChoice2());
        tvChoice3.setText(result.getChoice3());
        tvChoice4.setText(result.getChoice4());

        if(result.getChoice1Count() >= result.getChoice2Count()
                && result.getChoice1Count() >= result.getChoice3Count()
                && result.getChoice1Count() >= result.getChoice4Count())
            tvChoice1.setTextColor(Color.BLUE);
        if(result.getChoice2Count() >= result.getChoice1Count()
                && result.getChoice2Count() >= result.getChoice3Count()
                && result.getChoice2Count() >= result.getChoice4Count())
            tvChoice2.setTextColor(Color.BLUE);
        if(result.getChoice3Count() >= result.getChoice1Count()
                && result.getChoice3Count() >= result.getChoice2Count()
                && result.getChoice3Count() >= result.getChoice4Count())
            tvChoice3.setTextColor(Color.BLUE);
        if(result.getChoice4Count() >= result.getChoice1Count()
                && result.getChoice4Count() >= result.getChoice2Count()
                && result.getChoice4Count() >= result.getChoice3Count())
            tvChoice4.setTextColor(Color.BLUE);

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_ok:
                result.setIsChecked(new Boolean(true));
                QuestionController.insertQuestion(result);

                finish();
        }
    }
}
