package com.teamgms.gms.gms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.models.Question;

import java.util.ArrayList;

/**
 * NoResponse Qeuiston Adapter....
 * Created by hello_DE on 2017-03-20.
 */

public class NRQuestionAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Question> data;
    private LayoutInflater inflater;

    public NRQuestionAdapter(Context context, int layout, ArrayList<Question> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return data.get(pos);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null)
            convertView = inflater.inflate(layout, parent, false);

        TextView tv_nrq_title = (TextView)convertView.findViewById(R.id.tv_nrq_title);
        tv_nrq_title.setText(data.get(position).getQuestion());

        return convertView;
    }
}
