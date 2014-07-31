package com.example.soundanalyzer;

import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

/**
 * http://code.google.com/p/android-labs/source/browse/trunk/NoiseAlert/src/com/google/android/noisealert/NoiseAlert.java
 * 
 * http://www.animations.physics.unsw.edu.au/jw/dB.htm
 * @author arindam
 *
 */
public class MainActivity extends Activity {

	private static final int POLL_INTERVAL = 300;
	private Handler mHandler = new Handler();
	
	private SoundMeter mSensor;
	private PowerManager.WakeLock mWakeLock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSensor = new SoundMeter();
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "NoiseAlert");
	}
	
	private Runnable mPollTask = new Runnable() {
        public void run() {
                double amp = mSensor.getAmplitude();
                /*double REFERENCE = 1;
                double db = (20* Math.log10(amp/REFERENCE));*/
                
                //Log.i("Noise Alert", "Value : " + String.valueOf(amp));
                ((TextView) findViewById(R.id.amplitude_value_text)).setText("Value : " + String.valueOf(amp));
                mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!mWakeLock.isHeld()) 
            mWakeLock.acquire();
		try {
			mSensor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (mWakeLock.isHeld()) 
            mWakeLock.release();
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
	}
}
