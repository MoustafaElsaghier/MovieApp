package com.example.engahmed.movie_app.HelperClasses;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadFromURL {
    public static String readURL(String url) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;

        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
