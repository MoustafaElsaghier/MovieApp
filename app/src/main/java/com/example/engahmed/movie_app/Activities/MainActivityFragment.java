package com.example.engahmed.movie_app.Activities;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.engahmed.movie_app.Addapters.CustomAddapter;
import com.example.engahmed.movie_app.DataModels.MovieData;
import com.example.engahmed.movie_app.HelperClasses.MyDBHelper;
import com.example.engahmed.movie_app.HelperClasses.ReadFromURL;
import com.example.engahmed.movie_app.MovieListener;
import com.example.engahmed.movie_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment {
    ArrayList<MovieData> list;
    CustomAddapter customAddapter;
    GridView gridView;
    View v;
    SQLiteDatabase db;
    Switch fav;
    boolean menuSelect = false;
    ArrayList<MovieData> favouriteFilms;
    MyDBHelper myDBHelper = null;
    MovieListener movieListener;

    public MainActivityFragment() {
    }

    static int Activitystate = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_main, container, false);
        fav = (Switch) v.findViewById(R.id.favourite);
        favouriteFilms = new ArrayList<>();
        myDBHelper = new MyDBHelper(getContext());
        list = new ArrayList<>();
        detailActivityFragment.newOrFav = false;
        gridView = (GridView) v.findViewById(R.id.GridID);
        if (Activitystate == 0) {
            new ReadJSON().execute("http://api.themoviedb.org/3/movie/popular?api_key=50afdb8a06eb4f5d7320f4f4729a993e");
        } else if (Activitystate == 1) {
            Activitystate = 1;
            menuSelect = true;
            getDataFrom_db();
            if (favouriteFilms.size() == 0) {
                Toast.makeText(getContext(), "There is no Favourite Films Selected yet", Toast.LENGTH_LONG).show();
                gridView.setAdapter(null);
            } else {
                detailActivityFragment.newOrFav = true;
                customAddapter = new CustomAddapter(getContext(), favouriteFilms, R.layout.grid_item);
                customAddapter.notifyDataSetChanged();
                gridView.setAdapter(customAddapter);
            }
        } else if (Activitystate == 2) {
            menuSelect = false;
            new ReadJSON().execute("http://api.themoviedb.org/3/movie/top_rated?api_key=50afdb8a06eb4f5d7320f4f4729a993e");
        }
        getDataFrom_db();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!menuSelect) {
                    movieListener.setSelectedFilm(list.get(i));
                } else {
                    movieListener.setSelectedFilm(favouriteFilms.get(i));
                }
            }
        });
        setHasOptionsMenu(true);
        return v;
    }

    public void setFilm(MovieListener m) {
        movieListener = m;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.topRated:
                Activitystate = 2;
                menuSelect = false;
                new ReadJSON().execute("http://api.themoviedb.org/3/movie/top_rated?api_key=50afdb8a06eb4f5d7320f4f4729a993e");
                return true;
            case R.id.Popularity:
                Activitystate = 0;
                menuSelect = false;
                new ReadJSON().execute("http://api.themoviedb.org/3/movie/popular?api_key=50afdb8a06eb4f5d7320f4f4729a993e");
                return true;
            case R.id.Favourite:
                Activitystate = 1;
                menuSelect = true;
                getDataFrom_db();
                if (favouriteFilms.size() == 0) {
                    Toast.makeText(getContext(), "There is no Favourite Films Selected yet", Toast.LENGTH_LONG).show();
                    gridView.setAdapter(null);
                } else {
                    detailActivityFragment.newOrFav = true;
                    customAddapter = new CustomAddapter(getContext(), favouriteFilms, R.layout.grid_item);
                    customAddapter.notifyDataSetChanged();
                    gridView.setAdapter(customAddapter);
                }
                return true;
        }

        return false;
    }

    public void getDataFrom_db() {
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

    class ReadJSON extends AsyncTask<String, Void, String> {
        ProgressDialog pg;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(getContext());
            pg.setMessage("Loading From Server...");
            pg.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return ReadFromURL.readURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) return;
            try {
                if (!list.isEmpty()) {
                    list.clear();
                }
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject movie = jsonArray.getJSONObject(i);
                    list.add(new MovieData(movie.getString("poster_path")
                            , movie.getString("overview")
                            , movie.getString("original_title")
                            , movie.getString("vote_average")
                            , movie.getString("release_date")
                            , movie.getString("vote_count")
                            , movie.getString("backdrop_path")
                            , movie.getString("id")
                    ));
                }
                customAddapter = new CustomAddapter(getActivity(), list, R.layout.grid_item);
                gridView.setAdapter(customAddapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pg.hide();
        }
    }

}