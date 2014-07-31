package com.example.hellomap;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Supravas_splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.supravas_splash);
		
		// Thread For displaying SupravasBeta Image
		Thread splash_screen = new Thread(){
			public void run(){
				try{
					sleep(4000);// 4 seconds
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					 startActivity(new Intent(getApplicationContext(),Driving_tips.class));
					 finish();
				}
			}
		};
		splash_screen.start();
		Toast.makeText(this, "WELCOME !!!", Toast.LENGTH_LONG)
        .show();
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

}
	
		/* public boolean onOptionsItemSelected(MenuItem item)
		    {
		         
		        switch (item.getItemId())
		        {
		        case R.id.about:
		            // Single menu item is selected do something
		            // Ex: launching new activity/screen or show alert message
		        	final Toast toast= Toast.makeText(getApplicationContext(), "Inception of this project is initiated by our beloved Assistant Professor Mr.Mohan B.A .  Team consists of 4 members for this experimental Project work namely 1.Ashok Kumar.S  2.Sanjay Y.M  3.Shilpa G  4.Ankita Panda ", Toast.LENGTH_LONG);
			        toast.show();
			        // To Close the toast After 1 Second
					 Handler handler = new Handler();
			            handler.postDelayed(new Runnable() {
			               @Override
			               public void run() {
			                   toast.cancel(); 
			               }
			        }, 8000);
		        	
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		        }
		    }    
	}


*/