package beta.test.supravasbeta;

import java.io.Flushable;

import java.util.ArrayList;
import java.util.Timer;

    import org.achartengine.GraphicalView;

    import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

    public class Main extends Activity implements SensorEventListener,AccelerometerListener{
        public boolean init = false;
        SensorManager sMngr;
        Sensor snsr;

        Context context;
        Timer tmr ;

        ProgressBar pb;
        public static LinearLayout ll;
        Button btnStart;
        Button btnStop;

        Graph mGraph;
        GraphicalView view;

        ArrayList<Double> x,y,z;

        Graph graph;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);    
            setContentView(R.layout.main_activity);
            ll= (LinearLayout)findViewById(R.id.chart_container);
            sMngr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
            snsr = (Sensor)sMngr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            x = new ArrayList<Double>();
            y = new ArrayList<Double>();
            z = new ArrayList<Double>();



            btnStart = (Button)findViewById(R.id.btnStart);
            btnStart.setOnClickListener(new OnClickListener()
            
            {
            	

                @Override
                public void onClick(View view) {
                    sMngr.registerListener(Main.this, snsr,SensorManager.SENSOR_DELAY_NORMAL);
                }
                
            });
            btnStop = (Button)findViewById(R.id.btnStop);
            btnStop.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    sMngr.unregisterListener(Main.this);
                }
            });
        }
        public boolean onCreateOptionsMenu(Menu menu)
        {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.layout.supravas_menu, menu);
            return true;
        }
    		 public boolean onOptionsItemSelected(MenuItem item)
    		    {
    		         
    		        switch (item.getItemId())
    		        {
    		        case R.id.about:
    		            // Single menu item is selected do something
    		            // Ex: launching new activity/screen or show alert message
    		        	final Toast tag = Toast.makeText(getBaseContext(), "Inception of this project is initiated by our beloved Assistant Professor Mohan B.A . Team consists of 4 members for this experimental Project work namely 1.Ashok Kumar.S  2.Sanjay Y.M  3.Shilpa GunaShekar  4.Ankita panda",Toast.LENGTH_LONG);

    		        	tag.show();

    		        	new CountDownTimer(9000, 1000)
    		        	{

    		        	    public void onTick(long millisUntilFinished) {tag.show();}
    		        	    public void onFinish() {tag.show();}

    		        	}.start();// 8 Standard seconds Su-Pravas about menu appears
    		        	
    		            return true;
    		        default:
    		            return super.onOptionsItemSelected(item);
    		        }
    		    }    
    	
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            x.add((double) event.values[0]);
            y.add((double) event.values[1]);
            z.add((double) event.values[2]);
            mGraph = new Graph(this);
            mGraph.initData(x, y, z);
            mGraph.setProperties();
            if(!init){
                view = mGraph.getGraph();
                ll.addView(view);
                init = true;
            }else{
            	ll.removeAllViews();
            	view = mGraph.getGraph();
              	ll.addView(view);
              	view.repaint(TRIM_MEMORY_RUNNING_MODERATE, TRIM_MEMORY_MODERATE, TRIM_MEMORY_MODERATE, TRIM_MEMORY_MODERATE);       	
                
            }
        }
		@Override
		public void onAccelerationChanged(float x, float y, float z) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onShake(float force) {
			// TODO Auto-generated method stub
			
			final Toast toast= Toast.makeText(getApplicationContext(), "An High Frequency Motion is been Detected !!!!", Toast.LENGTH_SHORT);
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
		@Override
	    public void onResume() {
	            super.onResume();
	            Toast.makeText(getBaseContext(), "Su-pravas Started",
	                    Toast.LENGTH_SHORT).show();
	             
	            //Check device supported Accelerometer senssor or not
	            if (AccelerometerManager.isSupported(this)) {
	                 
	                //Start Accelerometer Listening
	                AccelerometerManager.startListening(this);
	            }
	    }
		 @Override
		    public void onStop() {
		            super.onStop();
		             
		            //Check device supported Accelerometer senssor or not
		            if (AccelerometerManager.isListening()) {
		                 
		                //Start Accelerometer Listening
		                AccelerometerManager.stopListening();
		                 
		                Toast.makeText(getBaseContext(), " Application Stoped",
		                         Toast.LENGTH_SHORT).show();
		            }
		            
		    }
		     
		    @Override
		    public void onDestroy() {
		        super.onDestroy();
		        Log.i("Sensor", "Service  distroy");
		         
		        //Check device supported Accelerometer sensor or not
		        if (AccelerometerManager.isListening()) {
		             
		            //Start Accelerometer Listening
		            AccelerometerManager.stopListening();
		             
		            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped",
		                   Toast.LENGTH_SHORT).show();
		        }          
		    }
		    
    }