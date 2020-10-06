package com.example.project_video_player;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;

public class Peripherique
{
    private String nom;
    private String adresse;
    private Handler handler = null;
    private BluetoothDevice device = null;

    public Peripherique(BluetoothDevice device, Handler handler)
    {
        if(device != null)
        {
            this.device = device;
            this.nom = device.getName();
            this.adresse = device.getAddress();
            this.handler = handler;
        }
        else
        {
            this.device = device;
            this.nom = "Aucun";
            this.adresse = "";
            this.handler = handler;
        }

        // TODO
    }

    public String getNom()
    {
        return nom;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public boolean estConnecte()
    {
        // TODO

        return false;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String toString()
    {
        return "\nNom : " + nom + "\nAdresse : " + adresse;
    }

    public void envoyer(String data)
    {
        // TODO
    }

    public void connecter()
    {
        // TODO
    }

    public void deconnecter()
    {
        // TODO
    }
}
