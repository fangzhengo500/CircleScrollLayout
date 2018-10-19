package com.loosu.ringscrolllayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private RingScrollLayout mLayoutRing;
    private SeekBar mSeekBarRadius;
    private SeekBar mSeekBarCenterX;
    private SeekBar mSeekBarCenterY;
    private SeekBar mSeekBarDegrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutRing = (RingScrollLayout) findViewById(R.id.layout_ring);
        mSeekBarRadius = (SeekBar) findViewById(R.id.seek_radius);
        mSeekBarCenterX = (SeekBar) findViewById(R.id.seek_center_x);
        mSeekBarCenterY = (SeekBar) findViewById(R.id.seek_center_y);
        mSeekBarDegrees = (SeekBar) findViewById(R.id.seek_degrees);

        mSeekBarRadius.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSeekBarCenterX.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSeekBarCenterY.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSeekBarDegrees.setOnSeekBarChangeListener(mSeekBarChangeListener);
    }

    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seek_radius:
                    mLayoutRing.setRadius(progress);
                    break;

                case R.id.seek_center_x:
                    mLayoutRing.setCenterX(progress);
                    break;

                case R.id.seek_center_y:
                    mLayoutRing.setCenterY(progress);
                    break;

                case R.id.seek_degrees:
                    mLayoutRing.setDegree(progress);
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
