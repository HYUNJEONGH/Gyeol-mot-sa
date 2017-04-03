package com.teamgms.gms.gms.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.models.Question;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;


/**
 * Created by yunjeonghwang on 2017. 3. 13..
 */

public class MyQuestionAdapter extends RealmRecyclerViewAdapter<Question, MyQuestionAdapter.ViewHolder> {

    public MyQuestionAdapter(RealmResults<Question> questions) {
        super(questions, true);
        setHasStableIds(true);
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
        public Question data;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}
