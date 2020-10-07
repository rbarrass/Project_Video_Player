package com.example.project_video_player;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// This class create an adapter for our objects list of devices items
public class AdapterDeviceItem extends ArrayAdapter<DeviceItem> {
    public AdapterDeviceItem(Context context, ArrayList<DeviceItem> device) {
        super(context, 0, device);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DeviceItem device = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_device, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        // Populate the data into the template view using the data object
        tvName.setText(device.name);
        tvAddress.setText(device.address);
        // Return the completed view to render on screen
        return convertView;
    }
}