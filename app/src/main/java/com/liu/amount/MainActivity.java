package com.liu.amount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.library.VolumeView;

public class MainActivity extends AppCompatActivity {

    private VolumeView volumeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volumeView = (VolumeView) findViewById(R.id.volume);
        volumeView.setOnVolumeChangeListener(new VolumeView.OnVolumeChangeListener() {
            @Override
            public void onVolumeChange(View view, int Volume) {
                Log.d("MainActivity", "Volume:" + Volume);
            }
        });
    }
}
