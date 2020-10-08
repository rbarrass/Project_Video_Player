package com.example.project_video_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

// Intent of client socket
public class ClientSocket extends AppCompatActivity {

    private String device_address_transmitted;
    protected ClientSocketFunctions newBluetoothConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_socket);

        // Init the intend
        Intent newIntent = getIntent();
        // Get MAC address transmited
        device_address_transmitted = newIntent.getStringExtra(ClientActivity.EXTRA_ADDRESS);
        // Call our connect by socket functions
        newBluetoothConnection = new ClientSocketFunctions(this, device_address_transmitted);
        newBluetoothConnection.execute();

        // Read video
        VideoView vidView = (VideoView)findViewById(R.id.streaming);

        // Add playback controls.
        MediaController vidControl = new MediaController(this);
        // Set it to use the VideoView instance as its anchor.
        vidControl.setAnchorView(vidView);
        // Set it as the media controller for the VideoView object.
        vidView.setMediaController(vidControl);

        File dir = Environment.getExternalStorageDirectory();
        File manualFile = new File(dir, "/" + "downloadedfile.mp4");
        Log.e(MainActivity.TAG,"CLIENT FILE LOCATION : "+manualFile);

        Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", manualFile);
        // Parse the address string as a URI so that we can pass it to the VideoView object.
        vidView.setVideoURI(uri);
        // Start playback.
        vidView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newBluetoothConnection.disconnect();
    }
}