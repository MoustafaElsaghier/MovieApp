package com.example.engahmed.movie_app.Asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.engahmed.movie_app.Addapters.reviewAddapter;
import com.example.engahmed.movie_app.R;
import com.example.engahmed.movie_app.HelperClasses.ReadFromURL;
import com.example.engahmed.movie_app.DataModels.reviewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class reviewsAsync extends AsyncTask<String, Void, String> {

    ArrayList<reviewData>list  ;
    ListView ls ;
    Context context;
    reviewAddapter adpt;

    public reviewsAsync(Context context,ListView ls) {
        this.ls = ls;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        return ReadFromURL.readURL(strings[0]);
    }
    @Override
    protected void onPostExecute(String s) {

        if (s == null) return;
        try {
            list = new ArrayList<>();
            if (!list.isEmpty()) {
                list.clear();
            }
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject reviwer = jsonArray.getJSONObject(i);
                list.add(new reviewData(reviwer.getString("author")
                        ,reviwer.getString("content")));
            }
           /* for(int i=0;i<list.size();++i){
                System.out.print(list.get(i).getRev_name()+"\t\t");
                System.out.println(list.get(i).getRev_word());
            }*/
            adpt = new reviewAddapter(context,list, R.layout.review_item);
            ls.setAdapter(adpt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    }
