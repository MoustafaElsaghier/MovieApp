package com.example.engahmed.movie_app.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.engahmed.movie_app.Asyncs.reviewsAsync;
import com.example.engahmed.movie_app.Asyncs.trailerAsync;
import com.example.engahmed.movie_app.DataModels.MovieData;
import com.example.engahmed.movie_app.HelperClasses.MyDBHelper;
import com.example.engahmed.movie_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class detailActivityFragment extends Fragment {
    static MovieData movieData;
    static TextView title, date, voteAVG, overView;
    static ListView trailers, reivews;
    Switch fav;
    static com.example.engahmed.movie_app.Asyncs.trailerAsync trailerAsync;
    SQLiteDatabase db;
    static boolean newOrFav = false;
    static com.example.engahmed.movie_app.Asyncs.reviewsAsync reviewsAsync;
    static LayoutInflater inflater;
    MyDBHelper myDBHelper = null;
    ArrayList<MovieData> favouriteFilms = new ArrayList<>();


    public detailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        myDBHelper = new MyDBHelper(getContext());
        fav = (Switch) v.findViewById(R.id.favourite);
        trailers = (ListView) v.findViewById(R.id.trailers);
        reivews = (ListView) v.findViewById(R.id.reviews);
        trailerAsync = new trailerAsync(getContext(), trailers);
        reviewsAsync = new reviewsAsync(getContext(), reivews);
        getDB();
        int i = 0;
        for (; i < favouriteFilms.size(); ++i) {
            if (movieData.getId().equals(favouriteFilms.get(i).getId())) {
                fav.setChecked(true);
                break;
            }
        }
        if (i == favouriteFilms.size()) {
            fav.setChecked(false);
        }
        ImageView img = (ImageView) v.findViewById(R.id.backdrop);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185" + movieData.getBackdrop()).into(img);
        title = (TextView) v.findViewById(R.id.title);
        title.setText(movieData.getTitle());

        trailerAsync.execute("http://api.themoviedb.org/3/movie/" + movieData.getId() + "/videos?api_key=50afdb8a06eb4f5d7320f4f4729a993e");
        reviewsAsync.execute("http://api.themoviedb.org/3/movie/" + movieData.getId() + "/reviews?api_key=50afdb8a06eb4f5d7320f4f4729a993e");

        date = (TextView) v.findViewById(R.id.date);
        date.setText(movieData.getDate());

        voteAVG = (TextView) v.findViewById(R.id.voteAVG);
        voteAVG.setText(movieData.getVoteAVG());

        overView = (TextView) v.findViewById(R.id.overView);
        overView.setText(movieData.getOverView());

        fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    db = myDBHelper.getWritableDatabase();
                    ContentValues con = new ContentValues();
                    con.put(MyDBHelper.id, movieData.getId());
                    con.put(MyDBHelper.imgUrl, movieData.getImgUrl());
                    con.put(MyDBHelper.overview, movieData.getOverView());
                    con.put(MyDBHelper.original_title, movieData.getTitle());
                    con.put(MyDBHelper.vote_average, movieData.getVoteAVG());
                    con.put(MyDBHelper.release_date, movieData.getDate());
                    con.put(MyDBHelper.vote_count, movieData.getVoteCount());
                    con.put(MyDBHelper.backdrop_path, movieData.getBackdrop());
                    if (movieData.isSelected() == "1") {
                        movieData.setSelected("0");
                    } else {
                        movieData.setSelected("1");
                    }
                    con.put(MyDBHelper.isSelected, movieData.isSelected());
                    db.replace(MyDBHelper.Table_Name, null, con);
                    db.close();
                    Toast.makeText(getContext(), "added to Favourite Movies", Toast.LENGTH_LONG).show();
                } else {
                    db = myDBHelper.getWritableDatabase();
                    db.delete(MyDBHelper.Table_Name, MyDBHelper.id + "=?", new String[]{movieData.getId()});
                    db.close();
                    Toast.makeText(getContext(), "removed to Favourite Movies", Toast.LENGTH_LONG).show();

                }
            }
        });
        return v;
    }


    public void getDB() {
        db = myDBHelper.getWritableDatabase();
        favouriteFilms.clear();
        Cursor c = db.query(myDBHelper.Table_Name, new String[]{MyDBHelper.id,
                MyDBHelper.imgUrl,
                MyDBHelper.overview,
                MyDBHelper.original_title,
                MyDBHelper.vote_average,
                MyDBHelper.release_date,
                MyDBHelper.vote_count,
                MyDBHelper.backdrop_path
                , MyDBHelper.isSelected}, null, null, null, null, null);
        if (c.moveToNext()) {
            do {
                MovieData m = new MovieData(c.getString(1), c.getString(2), c.getString(3),
                        c.getString(4), c.getString(5), c.getString(6),
                        c.getString(7), c.getString(0));
                m.setSelected(c.getString(8));
                favouriteFilms.add(m);
            } while (c.moveToNext());
        }
        db.close();
    }
}
