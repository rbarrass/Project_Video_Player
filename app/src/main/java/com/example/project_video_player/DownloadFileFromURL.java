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


/*class DownloadFileFromURL extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Starting download");
    }
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            Log.i(MainActivity.TAG, "DOWNLOADING");
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            // Output stream to write file
            OutputStream output = new FileOutputStream(root + "/downloadedfile.mp4");
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // writing data to file
                output.write(data, 0, count);
            }
            // flushing output
            output.flush();
            // closing streams
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(String file_url) {
        Log.i(MainActivity.TAG, "FILE SUCCESSFULLY DOWNLOADED.");
    }
}*/

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
            String root = Environment.getExternalStorageDirectory().toString();
            URLConnection connection = url.openConnection();
            Log.e(MainActivity.TAG,"test");
            connection.connect();
            int lenghtOfFile = connection.getContentLength();
            Log.e(MainActivity.TAG,"test2");
            System.out.println(root);
            System.out.println(sUrl[0]);



            // download the file
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            Log.e(MainActivity.TAG,"test3");
            OutputStream output = new FileOutputStream(root + "/downloadedfile.mp4");
            Log.e(MainActivity.TAG,"test4");

            byte data[] = new byte[1024];
            long total = 0;

            Log.e(MainActivity.TAG,"test5");

            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            Log.e(MainActivity.TAG,"test6");

            output.flush();
            Log.e(MainActivity.TAG,"test7");
            // closing streams
            output.close();
            Log.e(MainActivity.TAG,"test8");
            input.close();
            Log.e(MainActivity.TAG,"test9");

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
