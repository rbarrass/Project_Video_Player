package com.example.project_video_player;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;


public class ServerActivity extends AppCompatActivity {

    private boolean serverIsRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        //final String downloadUrl = "https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        final MultiAutoCompleteTextView tap_your_link_here = (MultiAutoCompleteTextView) findViewById(R.id.fieldURL);
        final Button downloadBtn = (Button)findViewById(R.id.downloadButtonID);
        final Button launchServer = (Button)findViewById(R.id.threadLauncherID);

        downloadBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String downloadUrl = tap_your_link_here.getText().toString();
                System.out.println("clic");
                new DownloadFileFromURL().execute(downloadUrl);
            }
        });

        // Here we define socket button and activate/deactivate bluetooth server mode
        launchServer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Server_Connect_Thread SocketThread = new Server_Connect_Thread();
                if (!serverIsRunning) {
                    Log.e(MainActivity.TAG, "ACTIVE THE THREAD");
                    serverIsRunning = true;
                    Toast.makeText(getApplicationContext(), "Server activation...", Toast.LENGTH_SHORT).show();
                    launchServer.setText("Server is activated");
                    SocketThread.start();
                } else {
                    Log.e(MainActivity.TAG, "STOP THE THREAD");
                    serverIsRunning = false;
                    Toast.makeText(getApplicationContext(), "Server Deactivation...", Toast.LENGTH_SHORT).show();
                    launchServer.setText("Server is stopped");
                    SocketThread.interrupt();
                }
            }
        });
    }
}


class Server_Connect_Thread extends Thread {

    BluetoothServerSocket mmServerSocket;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private boolean running = true;

    public Server_Connect_Thread() {
        // Try to connect to server
        try {
            // It's our UUID. It is the same as the UUID server, it authorize the connexion
            mmServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME", UUID.fromString("6559a8ad-d9e9-4a4d-9c96-fbda95d2496c"));
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error on bluetooth connexion because of listenUsingRfcommWithServiceRecord failed. Reason:" + e);
        }
    }
    // Running the connexion
    public void run() {
        // The bluetooth socket
        BluetoothSocket TheBluetoothSocket;
        while (running) {
            try {
                TheBluetoothSocket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            if (TheBluetoothSocket != null) {
                // Transfer of data
                Log.e(MainActivity.TAG, "The socket is created");
                try{
                    Log.e(MainActivity.TAG, "Client is connected");
                    TheBluetoothSocket.close();
                } catch (IOException e) {

                }
            }
        }
    }
}