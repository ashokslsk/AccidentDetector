package com.example.hellomap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Driving_tips extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.driving_info);
		
		Button DashBoard= (Button)findViewById(R.id.Dash_board);
	    DashBoard.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(Driving_tips.this,MainActivity.class);
	                startActivity(intent);     
	                overridePendingTransition(R.anim.right_in, R.anim.left_out);
	                finish();
	        }
	    });
		
	}

}
