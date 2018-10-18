package com.loosu.ringscrolllayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private RingScrollLayout mLayoutRing;
    private SeekBar mSeekBarRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutRing = (RingScrollLayout) findViewById(R.id.layout_ring);
        mSeekBarRadius = (SeekBar) findViewById(R.id.seek_radius);


        mSeekBarRadius.setOnSeekBarChangeListener(mSeekBarChangeListener);
    }

    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seek_radius:
                    mLayoutRing.setRadius(progress);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
