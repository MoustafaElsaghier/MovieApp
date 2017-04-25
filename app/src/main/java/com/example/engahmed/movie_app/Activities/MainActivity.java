package com.example.engahmed.movie_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.engahmed.movie_app.DataModels.MovieData;
import com.example.engahmed.movie_app.MovieListener;
import com.example.engahmed.movie_app.R;

public class MainActivity extends AppCompatActivity implements MovieListener {
    boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FrameLayout pane2 = (FrameLayout) findViewById(R.id.panel_Two);
        if (pane2 == null) {
            twoPane = false;
        } else {
            twoPane = true;
        }
        MainActivityFragment mi = new MainActivityFragment();
        mi.setFilm(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.panel_one, mi).commit();
        }
    }

    @Override
    public void setSelectedFilm(MovieData film) {
        // Case Single pane UI
        if (!twoPane) {
            Intent intent = new Intent(this, detailActivity.class);
            detailActivityFragment.movieData = film;
            startActivity(intent);
        } else {
            // Case Two pane UI
            detailActivityFragment d = new detailActivityFragment();
            d.setArguments(new Bundle());
            d.movieData = film;
            getSupportFragmentManager().beginTransaction().replace(R.id.panel_Two, d).commit();
        }
    }
}
