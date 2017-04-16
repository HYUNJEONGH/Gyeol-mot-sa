package com.teamgms.gms.gms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.models.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;


/**
 * Created by yunjeonghwang on 2017. 3. 13..
 */

public class MyQuestionAdapter extends RealmRecyclerViewAdapter<Question, MyQuestionAdapter.ViewHolder> {
    private Realm realm;

    public MyQuestionAdapter(RealmResults<Question> questions) {
        super(questions, true);
        setHasStableIds(true);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_question_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Question question = getItem(position);
        holder.data = question;
        holder.tv_question.setText(question.getQuestion());
        holder.tv_choice_one.setText(question.getChoice1());
        holder.tv_choice_two.setText(question.getChoice2());
        holder.tv_choice_three.setText(question.getChoice3());
        holder.tv_choice_four.setText(question.getChoice4());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMyQuestion(question.getNum());
            }
        });
    }

    private void deleteMyQuestion(final Long num) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Question question = realm.where(Question.class).equalTo("num", num).findFirst();
                if(question != null)
                    question.deleteFromRealm();
            }
        });
    }

    @Override
    public long getItemId(int index) {
        return getItem(index).getNum();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_question)
        TextView tv_question;
        @BindView(R.id.tv_choice_one)
        TextView tv_choice_one;
        @BindView(R.id.tv_choice_two)
        TextView tv_choice_two;
        @BindView(R.id.tv_choice_three)
        TextView tv_choice_three;
        @BindView(R.id.tv_choice_four)
        TextView tv_choice_four;
        @BindView(R.id.btn_delete)
        Button btn_delete;

        public Question data;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}
