package com.example.engahmed.movie_app.Asyncs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.engahmed.movie_app.Addapters.trailersAddapter;
import com.example.engahmed.movie_app.R;
import com.example.engahmed.movie_app.HelperClasses.ReadFromURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class trailerAsync extends AsyncTask<String, Void, String> {
    ArrayList<String> list;
    com.example.engahmed.movie_app.Addapters.trailersAddapter trailersAddapter;
    Context context;
    ListView ls;

    public trailerAsync(Context context, ListView ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
                JSONObject movie = jsonArray.getJSONObject(i);
                list.add(movie.getString("key"));
            }
            trailersAddapter = new trailersAddapter(context, list, R.layout.trailers_item);
            //  get adapter
            ls.setAdapter(trailersAddapter);
            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v="+list.get(i)));

                    context.startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
