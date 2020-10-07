package com.example.project_video_player;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;

public class ClientActivity extends AppCompatActivity {

    // Var if bluetooth is active
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    // List of bluetoothDevices
    private Set<BluetoothDevice> devices;
    // Bluetooth adapter
    private BluetoothAdapter bluetoothAdapter;
    // List of bounded devices
    private ArrayList<DeviceItem> deviceItemList;
    //private DeviceListFragment mDeviceListFragment;
    private BroadcastReceiver mReceiver;
    public static String EXTRA_ADDRESSE = "client_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        // Create our list view of bounded devices
        final ListView listView = (ListView) findViewById(R.id.pairedDevicesListID);
        // Init our bluetoothAdapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Init our bounded devices list
        deviceItemList = new ArrayList<DeviceItem>();
        // Check if bluetooth is available on this device
        if (bluetoothAdapter == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth unactivated !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Check if bluetooth is activated
            if (!bluetoothAdapter.isEnabled())
            {
                // Display to user a message to inform of the unactivated bluetooh
                Toast.makeText(getApplicationContext(), "Bluetooth non activé !", Toast.LENGTH_SHORT).show();
                // Possibility 1 : make a demand to user for bluetooth activation
                Intent activeBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(activeBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
                // Possibility 2: enable it with no demand
                //bluetoothAdapter.enable();

            }
            else
            {
                // Display to user about blutooth activation
                Toast.makeText(getApplicationContext(), "Bluetooth activé", Toast.LENGTH_SHORT).show();
                // Get bounded devices
                devices = bluetoothAdapter.getBondedDevices();
                // List all bounded devices
                for (BluetoothDevice blueDevice : devices)
                {
                    // Init a device object for all bounded devices and add them to an arraylist
                    DeviceItem newDevice= new DeviceItem(blueDevice.getName(),blueDevice.getAddress(),"false");
                    deviceItemList.add(newDevice);
                    //Toast.makeText(getApplicationContext(), "Device = " + newDevice.getName() + " Adresse = " + newDevice.getAddress(), Toast.LENGTH_SHORT).show();
                }
                // Generate our adapter for our list of device objects
                AdapterDeviceItem adapter = new AdapterDeviceItem(this, deviceItemList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(myListClickListener);


            }
        }

        /*FragmentManager fragmentManager = getSupportFragmentManager();

        mDeviceListFragment = DeviceListFragment.newInstance(bluetoothAdapter);
        fragmentManager.beginTransaction().replace(R.id.container, mDeviceListFragment).commit();*/
        Button scanBtn = (Button)findViewById(R.id.buttonBlueScanID);
        scanBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                test();
            }
        });
    }

    // Possibility 1 :
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;
        if (resultCode == RESULT_OK)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth activé", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Bluetooth non activé !", Toast.LENGTH_SHORT).show();
        }
    }


    public void test() {
        Toast.makeText(getApplicationContext(), "Apouillé", Toast.LENGTH_SHORT).show();
        /*if (!bluetoothAdapter.isDiscovering()) {
            Toast.makeText(getApplicationContext(), "is discovering", Toast.LENGTH_SHORT).show();
            bluetoothAdapter.startDiscovery();
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        //filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver2, filter);*/

        if (!bluetoothAdapter.isDiscovering()) {
            Toast.makeText(getApplicationContext(), "is discovering", Toast.LENGTH_SHORT).show();
            bluetoothAdapter.startDiscovery();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        registerReceiver(mReceiver2, filter);



        }


    private final BroadcastReceiver mReceiver2 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("Bluetooth", "got action " + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.i("Bluetooth", "got device " + deviceName );
            }
        }

/*        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //Finding devices
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                Toast.makeText(getApplicationContext(), "Guitaupe", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Device : " + device.getName() + " MAC : " + device.getAddress(), Toast.LENGTH_LONG).show();

                //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }*/
    };

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView a = (TextView) view.findViewById(R.id.tvName);
            TextView b = (TextView) view.findViewById(R.id.tvAddress);
            String deviceName = a.getText().toString();
            String deviceAddress = b.getText().toString();
            Toast.makeText(getApplication(), "Device : " + deviceName + " MAC : " + deviceAddress, Toast.LENGTH_LONG).show();

            // Make an intent to start next activity.
            Intent i = new Intent(ClientActivity.this, ClientSocketActivity.class);
            //Change the activity.
            i.putExtra(EXTRA_ADDRESS, deviceAddress); //this will be received at CommunicationsActivity
            startActivity(i);
        }
    };

    /*private AdapterView.OnItemClickListener clickOnItemList = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            TextView something = (TextView) v.findViewById(R.id.tvName);
            String info = ((TextView) v).getText().toString();
            Toast.makeText(getApplicationContext(), something.getText() , Toast.LENGTH_LONG).show();

            //String address = info.substring(info.length() - 17);
            // Make an intent to start next activity.
            //Intent i = new Intent(ClientActivity.this, ClientSocketActivity.class);
            //Change the activity.
            //i.putExtra(EXTRA_ADDRESS, address); //this will be received at CommunicationsActivity
            //startActivity(i);
        }
    };*/

    // TODELETE
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (bluetoothAdapter != null)
        {
            bluetoothAdapter.cancelDiscovery();
            //unregisterReceiver(bluetoothAdapter);
            //unregisterReceiver(bluetoothReceiver);
        }
    }
}


