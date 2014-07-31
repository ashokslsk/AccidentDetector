package com.example.hellomap;

import android.content.Context;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import org.achartengine.GraphicalView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends FragmentActivity implements LocationListener,SensorEventListener{
    private GoogleMap mMap;    
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    
    public boolean init = false;
    SensorManager sMngr;
    Sensor snsr;

    Context context;
    Timer tmr ;

    ProgressBar pb;
    public static LinearLayout ll;
    Button btnStart;
    

    Accel_Graph mGraph;
    GraphicalView view;

    ArrayList<Double> x,y,z;

    Accel_Graph graph;
    private static final int POLL_INTERVAL = 300;
	private Handler mHandler = new Handler();
	
	private SoundMeter mSensor;
	private PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        ImageView microphone = (ImageView) findViewById(R.id.microphone);
        // Display the GIF (from raw resource) into the ImageView
        loadGifIntoImageView(microphone, R.raw.noise1);
        mSensor = new SoundMeter();
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "NoiseAlert");
        setUpMapIfNeeded();
        ll= (LinearLayout)findViewById(R.id.chart_container);
        sMngr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        snsr = (Sensor)sMngr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
        z = new ArrayList<Double>();



       /* btnStart = (Button)findViewById(R.id.Accel_sense);
        
        btnStart.setOnClickListener(new OnClickListener() {
        	

            @Override
            public void onClick(View view) {
                sMngr.registerListener(MainActivity.this, snsr,SensorManager.SENSOR_DELAY_NORMAL);
                displayPopupWindow(view);
            }

			private void displayPopupWindow(View view) {
				
				// TODO Auto-generated method stub
				PopupWindow popup = new PopupWindow(MainActivity.this);
			    View layout = getLayoutInflater().inflate(R.layout.pop_up_content, null);
			    popup.setContentView(layout);
			    // Set content width and height
			    popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
			    popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
			    // Closes the popup window when touch outside of it - when looses focus
			    popup.setOutsideTouchable(true);
			    popup.setFocusable(true);
			    // Show anchored to button
			    popup.setBackgroundDrawable(new BitmapDrawable());
			    popup.showAsDropDown(view);
				
			}
        });
            /*btnStop = (Button)findViewById(R.id.stop);
        btnStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sMngr.unregisterListener(MainActivity.this);
            }
        })
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override*/
    }
    
    
    	   protected void loadGifIntoImageView(ImageView ivImage, int rawId) {
        try {
            GifAnimationDrawable anim = new GifAnimationDrawable(getResources().openRawResource(rawId));
            ivImage.setImageDrawable(anim);
            ((GifAnimationDrawable) ivImage.getDrawable()).setVisible(true, true);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	// ...onCreate above...
    	  // Display popup attached to the button as a position anchor
    private Runnable mPollTask = new Runnable() {
        public void run() {
                double amp = mSensor.getAmplitude();
                double REFERENCE = 1.0;
                double db = (20* Math.log10(amp/REFERENCE));
                
                //Log.i("Noise Alert", "Value : " + String.valueOf(amp));
                ((TextView) findViewById(R.id.Acousticnoise)).setText("Acoustic Noise : " + String.valueOf(db)+"Db");
                mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
};

     
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        x.add((double) event.values[0]);
        y.add((double) event.values[1]);
        z.add((double) event.values[2]);
        mGraph = new Accel_Graph(this);
        mGraph.initData(x, y, z);
        mGraph.setProperties();
        if(!init){
            view = mGraph.getGraph();
            ll.addView(view);
            init = true;
        }else{
            ll.removeView(view);
            view = mGraph.getGraph();
            ll.addView(view);
        }
    }





    @Override
    protected void onResume() {
        super.onResume();
        sMngr.registerListener(MainActivity.this, snsr,SensorManager.SENSOR_DELAY_NORMAL);
       
        if (!mWakeLock.isHeld()) 
            mWakeLock.acquire();
		try {
			mSensor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        setUpMapIfNeeded();
       
    }
    
    protected void onStop() {
		super.onStop();
		if (mWakeLock.isHeld()) 
            mWakeLock.release();
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
	}


    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap == null) {
            return;
        }
        // Initialize map options. For example:
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        
    
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); 
        //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER        
    
    }

	@Override
		public void onLocationChanged(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
	    mMap.animateCamera(cameraUpdate);
	    locationManager.removeUpdates(this);
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
}
