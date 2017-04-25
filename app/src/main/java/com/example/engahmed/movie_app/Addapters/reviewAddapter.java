package com.example.engahmed.movie_app.Addapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.engahmed.movie_app.R;
import com.example.engahmed.movie_app.DataModels.reviewData;

import java.util.ArrayList;


public class reviewAddapter extends ArrayAdapter {
    Context context;
    ArrayList<reviewData> list;
    int layout_id;

    public reviewAddapter(Context context, ArrayList<reviewData> list, int layout_id) {
        super(context,  layout_id,list);
        this.context = context;
        this.list = list;
        this.layout_id = layout_id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(layout_id, viewGroup, false);

        TextView auther = (TextView) v.findViewById(R.id.reviewerName);
        TextView txt = (TextView) v.findViewById(R.id.revieweText);

        auther.setText(list.get(i).getRev_name());
        txt.setText(list.get(i).getRev_word());

        return v;
    }
}
