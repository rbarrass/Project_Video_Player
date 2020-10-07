package com.example.project_video_player;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Starting download");
    }

    @Override
    protected String doInBackground(String... sUrl) {
        int count;
        try {

            URL url = new URL(sUrl[0]);
            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            URLConnection connection = url.openConnection();
            Log.e("tag","poil");
            connection.connect();
            Log.e("tag","poil2");

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            Log.e("tag","poil3");
            OutputStream output = new FileOutputStream(root + "/downloadedfile.mp4");
            Log.e("poiltest",root);
            Log.e("tag","poil4");

            byte data[] = new byte[1024];
            long total = 0;

            Log.e("tag","poil5");

            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            Log.e("tag","poil6");

            output.flush();
            Log.e("tag","poil7");
            // closing streams
            output.close();
            Log.e("tag","poil8");
            input.close();
            Log.e("tag","poil9");

        } catch (Exception e) {
            return e.toString();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        Log.i(MainActivity.TAG, "FILE SUCCESSFULLY DOWNLOADED.");
    }
}
