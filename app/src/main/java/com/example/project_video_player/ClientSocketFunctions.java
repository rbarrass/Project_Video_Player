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

    private boolean mConnected = true;
    private ProgressDialog mProgressDialog;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSocket mBluetoothSocket = null;
    private AppCompatActivity mCurrentActivity = null;
    private String mAddress = null;

    private static final UUID myUUID = UUID.fromString("6559a8ad-d9e9-4a4d-9c96-fbda95d2496c");

    ClientSocketFunctions(AppCompatActivity activity, String address) {
        mCurrentActivity = activity;
        mAddress =  address;
    }

    @Override
    protected void onPreExecute()     {
        mProgressDialog = ProgressDialog.show(mCurrentActivity, "Connecting in progress", "Connecting in progress");  //show a progress dialog
    }

    @Override
    protected Void doInBackground(Void... devices) { //while the progress dialog is shown, the connection is done in background

        try {
            if (mBluetoothSocket == null || !mConnected) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mAddress);//connects to the device's address and checks if it's available
                mBluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                mBluetoothSocket.connect();//start connection
                Log.e(MainActivity.TAG, "Socket connected");
            }
        }
        catch (IOException e) {
            mConnected = false;//if the try failed, you can check the exception here
            Log.e(MainActivity.TAG, "error catch");
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result) { //after the doInBackground, it checks if everything went fine

        super.onPostExecute(result);

        if (!mConnected){
            message("Connection Failed. Is it a SPP Bluetooth running a server? Try again.");
            mCurrentActivity.finish();

        }
        else {
            message("Connected.");
        }
        mProgressDialog.dismiss();
    }


    public void disconnect() {
        if (mBluetoothSocket!=null) //If the btSocket is busy
        {
            try  {
                mBluetoothSocket.close(); //close connection
            }
            catch (IOException e) {
                message("Error");
            }
        }

        message("Disconnected");

        mCurrentActivity.finish();
    }


    private void message(String s) {
        Toast.makeText(mCurrentActivity.getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }

}
