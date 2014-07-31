package com.example.hellomap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Profile extends Activity {
	 SharedPreferences pref;
	    Editor editor;
	    Button btn_register;
	    EditText et_name, et_num1, et_num2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.profile);
		et_name = (EditText) findViewById(R.id.editText_name);
	    et_num1 = (EditText) findViewById(R.id.editText_number1);
	    et_num2 = (EditText) findViewById(R.id.editText_number2);
	    btn_register = (Button) findViewById(R.id.button_register);
	   
	    // creating an shared Preference file for the information to be stored
	    // first argument is the name of file and second is the mode, 0 is private mode
	   
	    pref = getSharedPreferences("Registration", 0);
	    // get editor to edit in file
	    editor = pref.edit();
	   
	    // the tap of button we will fetch the information from three edittext    
	    btn_register.setOnClickListener(new View.OnClickListener() {
	       
	        public void onClick(View v) {
	        String name = et_name.getText().toString();
	        String num1 = et_num1.getText().toString();
	        String num2 = et_num2.getText().toString();
	       
	        if(et_name.getText().length()<=0){
	            Toast.makeText(Profile.this, "Enter name", Toast.LENGTH_SHORT).show();
	        }
	        else if( et_num1.getText().length()<=0){
	            Toast.makeText(Profile.this, "Enter First Responder Number", Toast.LENGTH_SHORT).show();
	        }
	        else if( et_num2.getText().length()<=0){
	            Toast.makeText(Profile.this, "Enter Second Responder Number", Toast.LENGTH_SHORT).show();
	        }
	        else{
	       
	        // as now we have information in string. Lets stored them with the help of editor
	        
	        editor.putString("num1",num1);
	        editor.putString("num2",num2);
	        editor.putString("Name", name);
	        editor.commit();   // commit the values
	        
	        // after saving the value open next activity
	        Intent ob = new Intent(Profile.this, MainActivity.class);
	        startActivity(ob);
	       
	        }   
	        }
	    });
}
}	

