package com.example.hellomap;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Driving_tips extends Activity {
	private TextView batteryInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.driving_info);
		//textview setting for supravas battery status
		batteryInfo=(TextView)findViewById(R.id.Battery_Status);
		
		 this.registerReceiver(this.batteryInfoReceiver,	
		 new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		ImageView welcome = (ImageView) findViewById(R.id.welcome);
        // Display the GIF (from raw resource) into the ImageView
        loadGifIntoImageView(welcome, R.raw.welcome_chosen);
		
		Button DashBoard= (Button)findViewById(R.id.Dash_board);
	    DashBoard.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	    	    displayPopupWindow(v);
	            Intent intent = new Intent(Driving_tips.this,MainActivity.class);
	                startActivity(intent);     
	                overridePendingTransition(R.anim.right_in, R.anim.left_out);
	                finish();
	        }

		private void displayPopupWindow(View v) {
			
			// TODO Auto-generated method stub
			PopupWindow popup = new PopupWindow(Driving_tips.this);
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
		    popup.showAsDropDown(v);
			
		}
	});
	    

	    Button quit_supra = (Button) findViewById(R.id.Quit_supravas);
	    quit_supra.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	        	showDialog(DIALOG_XYZ);
	            
	        }
	    });
}
	
	private final static int DIALOG_XYZ = 0;

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	        case DIALOG_XYZ:
	            AlertDialog.Builder builder = new AlertDialog.Builder(this);
	            builder.setMessage("Are you sure you want to quit 'Supravas' ")
	                   .setCancelable(false)
	                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                       public void onClick(DialogInterface dialog, int id) {
	                            Driving_tips.this.finish();
	                            System.exit(0);
	                       }
	                   })
	                   .setNegativeButton("No", new DialogInterface.OnClickListener() {
	                       public void onClick(DialogInterface dialog, int id) {
	                            dialog.cancel();
	                       }
	                   });
	            Dialog alert = builder.create();
	            return alert;
	    }
	    return null;
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
   
	private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
		 @Override
		 public void onReceive(Context context, Intent intent) {
			 int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
			 batteryInfo.setText(" Battery status:                  " +level+"%"+"\n");
		 }
	};
	
	public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.second_screen_menu, menu);
        return true;
    }
	public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.about:
            LayoutInflater inflater= LayoutInflater.from(this);
        	View view=inflater.inflate(R.layout.about_supravas, null);
        	TextView textview_about=(TextView)view.findViewById(R.id.textmsg);
        	textview_about.setText("just try");
        	AlertDialog.Builder alertDialog_about = new AlertDialog.Builder(this);  
        	alertDialog_about.setTitle("About Supravas");  
        	alertDialog_about.setMessage("supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!.");  
        	alertDialog_about.setPositiveButton("OK", null);  
        	AlertDialog alert = alertDialog_about.create();
        	alert.show();
            return true;
        case R.id.tips:
        	LayoutInflater inflater1= LayoutInflater.from(this);
        	View view1=inflater1.inflate(R.layout.driving_tips, null);
        	TextView textview_tips=(TextView)view1.findViewById(R.id.textview_tips);
        	textview_tips.setText("fill the tips its scrollable space");
        	AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);  
        	alertDialog.setTitle("Few major driving tips ");  
        	alertDialog.setMessage("supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!. supravas!.");  
        	alertDialog.setPositiveButton("OK", null);  
        	AlertDialog alert_tips = alertDialog.create();
        	alert_tips.show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	           .setMessage("Are you sure you want to exit Supravas?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    Driving_tips.this.finish();
	               }
	           })
	           .setNegativeButton("No", null)
	           .show();
	}



}
