package com.example.engahmed.movie_app.HelperClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHelper extends SQLiteOpenHelper {
    public static String db_name = "myMoovies";
    public static int dateBase_Version = 1;
    public static String Table_Name = "Movies";
    public static String id = "id";
    public static String imgUrl = "poster_path";
    public static String overview = "overview";
    public static String original_title = "original_title";
    public static String vote_average = "vote_average";
    public static String release_date = "release_date";
    public static String vote_count = "vote_count";
    public static String backdrop_path = "backdrop_path";
    public static String isSelected = "isSelected" ;


    public String createTable = "Create Table " + Table_Name + "(" + id + " Text primary key" +
            ", " + imgUrl + " Text ," + overview + " Text ," +
            original_title + " Text, " + vote_average + " Text , " +
            release_date + " Text ," + vote_count + " Text," + backdrop_path + " Text ,"+isSelected+" Text)";

    public MyDBHelper(Context c) {
        super(c, db_name, null, dateBase_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop Table if exists " + Table_Name);
        onCreate(db);
    }
}
