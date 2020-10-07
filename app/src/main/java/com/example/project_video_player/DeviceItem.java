package com.example.project_video_player;

// This class is our object of devices items
public class DeviceItem {
    // Name of the device
    public String name;
    // MAC address of the device
    public String address;
    private boolean connected;

    public DeviceItem(String name, String address, String connected){
        this.name = name;
        this.address = address;
        if (connected == "true") {
            this.connected = true;
        }
        else {
            this.connected = false;
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean getConnected() {
        return connected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
