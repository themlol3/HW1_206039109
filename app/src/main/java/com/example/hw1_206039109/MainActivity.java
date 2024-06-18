package com.example.hw1_206039109;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button logB;
    private EditText passET;
    private Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passET = findViewById(R.id.passET);
        logB = findViewById(R.id.logB);

        logB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passET.getText().toString();
                if (goodLogin(password)) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean IsQuiet() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int mode = audioManager.getRingerMode();

        return mode == AudioManager.RINGER_MODE_VIBRATE || mode == AudioManager.RINGER_MODE_SILENT;
    }

    private boolean goodLogin(String pass) {
        if (!pass.equals(getBattery() + "")) {
            Toast.makeText(MainActivity.this, "Text not equals Battery.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!isBTOn())
        {
            Toast.makeText(this, "Bluetooth is off.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!IsQuiet())
        {
            Toast.makeText(this, "Device is not quiet.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isBTOn() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    private int getBattery() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return (int) ((level / (float) scale) * 100);
    }

}