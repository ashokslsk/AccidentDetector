package com.example.hellomap;

import android.content.Context;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Timer;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


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
    Button btnStop;

    Accel_Graph mGraph;
    GraphicalView view;

    ArrayList<Double> x,y,z;

    Accel_Graph graph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        setUpMapIfNeeded();
        ll= (LinearLayout)findViewById(R.id.chart_container);
        sMngr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        snsr = (Sensor)sMngr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
        z = new ArrayList<Double>();



        btnStart = (Button)findViewById(R.id.button1);
        btnStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                sMngr.registerListener(MainActivity.this, snsr,SensorManager.SENSOR_DELAY_NORMAL);
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
        setUpMapIfNeeded();
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER        
    
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
