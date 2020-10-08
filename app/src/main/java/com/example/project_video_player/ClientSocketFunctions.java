package com.example.project_video_player;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ClientSocketFunctions extends AsyncTask<Void, Void, Void> {

    private boolean isConnected = true;
    private ProgressDialog dialog;
    private BluetoothAdapter adapter = null;
    private BluetoothSocket socket = null;
    private AppCompatActivity appActivity = null;
    private String myAddress = null;

    private static final UUID myUUID = UUID.fromString("6559a8ad-d9e9-4a4d-9c96-fbda95d2496c");

    ClientSocketFunctions(AppCompatActivity activity, String address) {
        appActivity = activity;
        myAddress =  address;
    }


    @Override
    protected void onPreExecute()     {
        // show on the screen a dialog
        dialog = ProgressDialog.show(appActivity, "Processing...", "Processing...");
    }

    @Override
    protected Void doInBackground(Void... devices) {
        //if the dialog is shown, the connection will be run in background

        try {
            if (socket == null || !isConnected) {
                //get the device by his bluetooth
                adapter = BluetoothAdapter.getDefaultAdapter();
                //connection to the device and checks if it's ok
                BluetoothDevice device = adapter.getRemoteDevice(myAddress);
                //create a connection by the UUID
                socket = device.createRfcommSocketToServiceRecord(myUUID);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                socket.connect();//start connection
                Log.e(MainActivity.TAG, "Socket connected");
            }
        }
        catch (IOException e) {
            //Show the error if the try fail
            isConnected = false;
            Log.e(MainActivity.TAG, "error catch");
        }
        return null;
    }

    //the post execute checks if it's alright before the doInBackground
    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);

        if (!isConnected){
            message("Connection Failed. Is it a SPP Bluetooth running a server? Try again.");
            appActivity.finish();

        }
        else {
            message("Connected.");
        }
        dialog.dismiss();
    }


    public void disconnect() {
        if (socket!=null) //If the socket is already full
        {
            try  {
                socket.close(); // then try to close connection
            }
            catch (IOException e) {
                message("Error");
            }
        }

        message("Disconnected");

        appActivity.finish();
    }


    private void message(String s) {
        Toast.makeText(appActivity.getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }

}
