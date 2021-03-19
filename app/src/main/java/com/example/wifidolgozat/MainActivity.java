package com.example.wifidolgozat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
   // Button btnOn,btnOff,btnInfo;
    TextView tw;
    BottomNavigationView bottomNavigationView;
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    MediaPlayer mediaPlayerWifiOn,mediaPlayerWifiOff,mediaPlayerWifiInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        bottomNavigationView.setOnNavigationItemSelectedListener
            (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.wifiOn:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        {
                            tw.setText("nincs engedélyezve a wifi állapot módosítására.");
                            Intent panel = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                            startActivityForResult(panel,0);

                        }
                        wifiManager.setWifiEnabled(true);
                        mediaPlayerWifiOn.start();
                        tw.setText("wifi bekapcsolva");
                        break;
                    case R.id.wifiOff:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        {
                            tw.setText("nincs engedélyezve a wifi állapot módosítására.");
                            Intent panel = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                            startActivityForResult(panel,0);

                        }
                        wifiManager.setWifiEnabled(false);
                        mediaPlayerWifiOff.start();
                        tw.setText("wifi kikapcsolva");
                        break;
                    case R.id.wifiInfo:
                        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        if (networkInfo.isConnected())
                        {
                            int ip_int = wifiInfo.getIpAddress();
                            String ip = Formatter.formatIpAddress(ip_int);

                            tw.setText("IP cím: " +ip);
                            mediaPlayerWifiInfo.start();
                        }
                        else
                        {
                            tw.setText("nincs csatlakozva egy hálozathoz se");
                            mediaPlayerWifiInfo.start();
                        }
                        break;
                }
                return true;
            }
        });


    }



    @Override
    protected void onPause() {
        mediaPlayerWifiOn.pause();
        super.onPause();
    }


    private void init() {
        tw = findViewById(R.id.textView);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        mediaPlayerWifiOn = MediaPlayer.create(this,R.raw.wifi_on);
        mediaPlayerWifiOff = MediaPlayer.create(this,R.raw.wifi_off);
        mediaPlayerWifiInfo = MediaPlayer.create(this,R.raw.wifi_info);

    }

}