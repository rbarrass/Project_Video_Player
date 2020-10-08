package com.example.project_video_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newBluetoothConnection.disconnect();
    }
}