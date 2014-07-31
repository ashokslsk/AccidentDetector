package com.example.hellomap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends FragmentActivity implements LocationListener,SensorEventListener,AccelerometerListener{
    private GoogleMap mMap;    
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 100;
    
    
    
    private LinearLayout lyGforce;

    int greater;
    double db;
    float force;
    
    //=== G-force =======
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float gOffset;
    private boolean calibrate;
    private static TimeSeries timeSeriesGforce;
    private static XYMultipleSeriesDataset datasetGforce;
    private static XYMultipleSeriesRenderer rendererGforce;
    private static XYSeriesRenderer rendererSeriesGforce;
    private static GraphicalView viewGforce;

    
    TextView  num1, num2;
    SharedPreferences pref;
   
    

    private static final int POLL_INTERVAL = 300;
	private Handler mHandler = new Handler();
	
	private SoundMeter mSensor;
	private PowerManager.WakeLock mWakeLock;
	SmsManager sms = SmsManager.getDefault();
	   
    public String message = "MayDay MayDay!! please cross check whether he/she is safe";
    String numbers[]={"9901356240","9738123597"};



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
        
        lyGforce = (LinearLayout) findViewById(R.id.chart_container);
        
      
        

        //=========== G-force =======
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        datasetGforce = new XYMultipleSeriesDataset();
        rendererGforce = new XYMultipleSeriesRenderer();
        rendererGforce.setBackgroundColor(Color.WHITE);
        rendererGforce.setMarginsColor(Color.WHITE);
               
        rendererGforce.setApplyBackgroundColor(true);
        rendererGforce.setAxisTitleTextSize(16);
        rendererGforce.setChartTitleTextSize(20);
        rendererGforce.setLabelsTextSize(15);
        rendererGforce.setLegendTextSize(15);
        rendererGforce.setMargins(new int[] { 20, 30, 15, 0 });
        rendererGforce.setZoomButtonsVisible(true);
        rendererGforce.setPointSize(10);
        rendererSeriesGforce = new XYSeriesRenderer();
        rendererSeriesGforce.setColor(Color.BLUE);
        rendererSeriesGforce.setLineWidth(2);

        rendererGforce.addSeriesRenderer(rendererSeriesGforce);

        timeSeriesGforce = new TimeSeries("Supravas-Sense");

        datasetGforce.addSeries(timeSeriesGforce);
             
        viewGforce = ChartFactory.getTimeChartView(this, datasetGforce, rendererGforce, "");
        viewGforce.refreshDrawableState();
        viewGforce.repaint();

        lyGforce.addView(viewGforce);



      


       /* btnStart = (Button)findViewById(R.id.Accel_sense);
        
        btnStart.setOnClickListener(new OnClickListener() {
        	

            @Override
            public void onClick(View view) {
                sMngr.registerListener(MainActivity.this, snsr,SensorManager.SENSOR_DELAY_NORMAL);
               // to push pop up menu
                displayPopupWindow(view);
            }

			
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
    	                 db = (20* Math.log10(amp));
    	                 if(db >88)
    	         		{
    	                	 Toast.makeText(getBaseContext(), "Acoustic noise impact",
    	                             Toast.LENGTH_LONG).show();
    	         		}
    	                 
    	                 //MeaningExtract();
    	                //Log.i("Noise Alert", "Value : " + String.valueOf(amp));
    	                ((TextView) findViewById(R.id.Acousticnoise)).setText("Acoustic Noise : " + Integer.toString((int) Math.round(db))+" dB");
    	                mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    	        }
    	};


     
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }
    
    
    @Override
	public void onAccelerationChanged(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShake(float force) {
		// TODO Auto-generated method stub
		
		
			MeaningExtract();	
		
		
		
		final Toast toast= Toast.makeText(getApplicationContext(), "Impact detected !!!!", Toast.LENGTH_SHORT);
        toast.show();
        // To Close the toast After 1 Second
		 Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   toast.cancel(); 
               }
        }, 500);
		
		
		
	}
	
	/*public void MeaningCheck()
	{
		if(db >88)
		{
			MeaningExtract();
		}
	}*/
	
	
	public void MeaningExtract()
	{		  
				    new AlertDialog.Builder(this)
				           .setMessage("Supravas Detected Auxilary impact of sensor and its about to communicate to concern person ")
				           .setCancelable(false)
				           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				               public void onClick(DialogInterface dialog, int id) {
				            	   for(String number : numbers)
				                      {
				                          sms.sendTextMessage(number, null, message, null, null);
				                      }  
				            	   
				               }
				           })
				           
				           .setNegativeButton("No", null)
				           .show();
	}
	@Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub

        switch (event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:

                float x = event.values[0];
                float y = event.values[1]; // Use to calculate G-Force value
                float z = event.values[2];

                if(calibrate)
                    gOffset = y - SensorManager.GRAVITY_EARTH;

                double gForce = ((y - gOffset) / SensorManager.GRAVITY_EARTH);

                timeSeriesGforce.add(new Date(), gForce);

                  rendererGforce.setXAxisMax(rendererGforce.getXAxisMax() + 30);
                  rendererGforce.setXAxisMin(rendererGforce.getXAxisMin() + 30);

                  viewGforce.repaint();

                Log.e("Ash","G-Force: "+String.valueOf(gForce));

                calibrate = false;

            break;

            default:
                break;
        }
    }


   



    @Override
    protected void onResume() {
        super.onResume();
        calibrate = true;
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
             
        if (!mWakeLock.isHeld()) 
            mWakeLock.acquire();
		try {
			mSensor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        setUpMapIfNeeded();
        
        Toast.makeText(getBaseContext(), "Su-pravas Started",
                Toast.LENGTH_SHORT).show();
         
        if (AccelerometerManager.isSupported(this)) {
            
            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
        }
       
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        //========== G-force=============//
        mSensorManager.unregisterListener(this);
    }
    
    protected void onStop() {
		super.onStop();
		mSensorManager.unregisterListener(this);
		if (mWakeLock.isHeld()) 
            mWakeLock.release();
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
		
	}


    private void setUpMapIfNeeded() {
        if (mMap != null) {
        	mMap.setMyLocationEnabled(true);
            return;
        }
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
        if (!enabledGPS) {
        	new AlertDialog.Builder(this)
	           .setMessage("GPS and NETWORK is not available would you like to open Settings")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                   startActivity(intent);
	               }
	           })
	           .setNegativeButton("No", null)
	           .show();
	} else{
		final Toast toast= Toast.makeText(getApplicationContext(), "GPS AVAILABLE ..! Please Make sure that you turned your internet 'ON'...! ", Toast.LENGTH_LONG);
        toast.show();
        // To Close the toast After 1 Second
		 Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   toast.cancel(); 
               }
        }, 5000);
	}
        
        
        
        if (mMap == null) {
            return;
        }
        // Initialize map options. For example:
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        
    
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this); 
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); 
        //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER        
    
    }

	@Override
		public void onLocationChanged(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
	    mMap.animateCamera(cameraUpdate);
	    locationManager.removeUpdates(this);
	    Log.e("Ash","onStatusChanged - called");
	    Toast.makeText(this, "Location " + latLng.latitude+","+latLng.longitude,
                Toast.LENGTH_LONG).show();
	    Marker startPerc = mMap.addMarker(new MarkerOptions()
        .position(latLng)
        .title("current location")
        .snippet("Supravas is here")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon1)));
	    

	    double latitude = location.getLatitude();


	    // Getting longitude of the current location

	    double longitude = location.getLongitude();


	    float speed = location.getSpeed();
	    // Creating a LatLng object for the current location

	    

	    
	    CameraPosition camPos = new CameraPosition.Builder()
	    .target(new LatLng(latitude, longitude))   
	    .zoom(18)        
	    .bearing(location.getBearing())
	    .tilt(70)
	    .build();

	    CameraUpdate camUpd3 = 
	            CameraUpdateFactory.newCameraPosition(camPos);

	    mMap.animateCamera(camUpd3);
	    
	    
	    // ... get a map.
	    // Add a circle in Sydney
	    Circle circle = mMap.addCircle(new CircleOptions()
	        .center(new LatLng(latitude, longitude))
	        .radius(50)
	        .strokeColor(Color.CYAN)
	        .fillColor(Color.TRANSPARENT));


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
	@Override
	public void onBackPressed() 
	{
	    Intent myIntent = new Intent(MainActivity.this, Driving_tips.class);
	    startActivity(myIntent);
	    super.onBackPressed();
	    overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}
	
	
}
