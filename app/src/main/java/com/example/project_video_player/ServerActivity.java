package com.example.project_video_player;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;


public class ServerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        //final String downloadUrl = "https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        final MultiAutoCompleteTextView tap_your_link_here = (MultiAutoCompleteTextView) findViewById(R.id.fieldURL);
        final Button downloadBtn = (Button)findViewById(R.id.downloadButtonID);

        downloadBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String downloadUrl = tap_your_link_here.getText().toString();
                System.out.println("clic");
                new DownloadFileFromURL().execute(downloadUrl);
            }
        });
    }
}

class Server_Connect_Thread extends Thread {

    BluetoothServerSocket mmServerSocket;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private boolean running = true;

    public Server_Connect_Thread() {

        try {
            mmServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME", UUID.fromString("6559a8ad-d9e9-4a4d-9c96-fbda95d2496c"));
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Bluetooth Error! listenUsingRfcommWithServiceRecord failed. Reason:" + e);
        }
    }

    public void run() {
        BluetoothSocket mBluetoothSocket;
        while (running) {
            try {
                mBluetoothSocket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            if (mBluetoothSocket != null) {
                // transfer the data here
                Log.e(MainActivity.TAG, "#####Socket created#####");
                try{
                    Log.e(MainActivity.TAG, "#####*client connected#####");
                    mBluetoothSocket.close();
                } catch (IOException e) {

                }
            }
        }
    }
}