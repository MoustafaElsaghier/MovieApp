package com.example.engahmed.movie_app.Addapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.engahmed.movie_app.DataModels.MovieData;
import com.example.engahmed.movie_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAddapter extends BaseAdapter {
    ArrayList<MovieData> list;
    Context context;
    int layout_id;

    public CustomAddapter(Context context, ArrayList<MovieData> list, int layout_id) {
        this.list = list;
        this.context = context;
        this.layout_id = layout_id;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(layout_id, viewGroup, false);
//        if(view == null ){
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = layoutInflater.inflate(layout_id, viewGroup, false);
//        }
        MovieData movieData = list.get(position);
        ImageView imageView = (ImageView) v.findViewById(R.id.posterImg);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + movieData.getImgUrl()).into(imageView);
        return v;
    }
}
