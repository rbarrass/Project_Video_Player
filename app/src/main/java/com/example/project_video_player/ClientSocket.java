package com.example.project_video_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class ClientSocket extends AppCompatActivity {

    private String device_address_transmitted;
    protected ClientSocketFunctions mBluetoothConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_socket);

        Intent newIntent = getIntent();
        device_address_transmitted = newIntent.getStringExtra(ClientActivity.EXTRA_ADDRESS);

        mBluetoothConnection = new ClientSocketFunctions(this, device_address_transmitted);
        mBluetoothConnection.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothConnection.disconnect();
    }
}