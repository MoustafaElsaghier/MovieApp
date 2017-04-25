package com.example.engahmed.movie_app.Addapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.engahmed.movie_app.R;

import java.util.ArrayList;

/**
 * Created by Eng Ahmed on 11/4/2016.
 */

public class trailersAddapter extends BaseAdapter {
    Context context;
    ArrayList<String> list;
    int layout_id;

    public trailersAddapter(Context context, ArrayList<String> list, int layout_id) {
        this.context = context;
        this.list = list;
        this.layout_id = layout_id;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(layout_id, viewGroup, false);

        TextView tx = (TextView) v.findViewById(R.id.trailer);
        tx.setText("Trailer " + (i + 1));
        return v;
    }
}
