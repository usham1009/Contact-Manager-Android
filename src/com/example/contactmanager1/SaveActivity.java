package com.example.contactmanager1;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
* Author : Dhruv & Usha
* Date : 31/Oct/2014
* Purpose : Define the Save Activity, the save screen, user can enter info and save it. or clear the screen or go back.
*/
public class SaveActivity extends Activity {
	
	EditText FirstName;
	EditText LastName;
	EditText PhoneNo;
	EditText EMail;
	Button save, clear;
	String EditMode="Save"; // Default Editing Mode for this Activity is "Save" Mode. It also supports an "Edit" Mode.
	
	/*
	* Author : Dhruv & Usha
	* Date : 31/Oct/2014
	* Purpose : Sets the oncreate fun
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		FirstName = (EditText) findViewById(R.id.editFName);
		LastName = (EditText) findViewById(R.id.editLName);
		PhoneNo = (EditText) findViewById(R.id.editPhoneNo);
		EMail = (EditText) findViewById(R.id.editEmail);
		save = (Button) findViewById(R.id.buttonSave);
		clear = (Button) findViewById(R.id.buttonClear);
		
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearFields();
				Log.i("Fields:", "All fields cleared!");
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Save pressed :", "Will try to save record!");
				saveToFile();
			}
		});
		
		// Get the message from the intent
	    Bundle bundle = getIntent().getExtras();
	    String FName = bundle.getString("FName");
	    String LName = bundle.getString("LName"); 
	    String PNo = bundle.getString("PNo"); 
	    String Email = bundle.getString("Email");
	    EditMode = bundle.getString("Edit");
	    
	    // Set the values of the edit fields
	    FirstName.setText(FName);
	    LastName.setText(LName);
	    PhoneNo.setText(PNo);
	    EMail.setText(Email);
	    
	    
	    //Setting fonts and styles
//	    Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Segoe-Regular.ttf");
	    Typeface typeFace2=Typeface.createFromAsset(getAssets(),"fonts/Segoe-UI-Symbol.ttf");
	    TextView myTextView=(TextView)findViewById(R.id.textFName);
	    myTextView.setTypeface(typeFace2);
	    myTextView=(TextView)findViewById(R.id.textLName);
	    myTextView.setTypeface(typeFace2);
	    myTextView=(TextView)findViewById(R.id.textPhoneNo);
	    myTextView.setTypeface(typeFace2);
	    myTextView=(TextView)findViewById(R.id.textEmail);
	    myTextView.setTypeface(typeFace2);
	    FirstName.setTypeface(typeFace2);
	    LastName.setTypeface(typeFace2);
	    PhoneNo.setTypeface(typeFace2);
	    EMail.setTypeface(typeFace2);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	/*
     * Author - Usha M
     * Used for handling presses on action bar menu items 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
        int id = item.getItemId(); // fetches the id of the button pressed
        
        // Performs the corresponding action to the button pressed
        switch (id) 
        {
	        case R.id.action_Help:
	        	
	        	Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Segoe-Regular.ttf");
	        	LayoutInflater inflater = getLayoutInflater();
	        	View layout = inflater.inflate(R.layout.toast_layout_custom, (ViewGroup) findViewById(R.id.toast_layout_error));

	        	TextView textWelcome = (TextView) layout.findViewById(R.id.text);
	        	textWelcome.setTypeface(typeFace);
	        	textWelcome.setText("Welcome to the Contact Manager app ! App made by Usha and Dhruv . . . This is an address book app! Have fun entering your personal information and storing them ! Thanks for using our app ! :)");

	        	Toast toast = new Toast(getApplicationContext());
	        	toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	        	toast.setDuration(Toast.LENGTH_LONG);
	        	toast.setView(layout);
	        	toast.show();
	        	
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
	
    /*
     * Author - Usha M
     * Used for saving a record to a file
     */
	public void saveToFile()
	{
		String FName = FirstName.getText().toString().trim();
		String LName = LastName.getText().toString().trim();
		String PNo = PhoneNo.getText().toString().trim();
		String Email = EMail.getText().toString().trim();
		
		FileIO fileIO = new FileIO();
		
		if( checkValidity(FName) )
		{
			// if Rest of fields are empty, insert a space between them
			if(LName.equals(null) || LName.length() <= 0)
			{
				LName = " ";
			}
			if(PNo.equals(null) || PNo.length() <= 0)
			{
				PNo = " ";
			}
			if(Email.equals(null) || Email.length() <= 0)
			{
				Email = " ";
			}
			
			String lineToWrite = FName + "|" + LName + "|" + PNo + "|" + Email + "|";
			
			Log.i("Will try to to save: ", lineToWrite);
			
			boolean flg = fileIO.saveToDisk( Environment.getExternalStorageDirectory(),lineToWrite );
			if(flg)
			{
				// Logs & Toast to let the user an developer know the record was saved successfully
				Log.i("Record saved to file successfully", "");
				Toast.makeText(getBaseContext(), "Record saved to File !", Toast.LENGTH_SHORT).show();
				// Will go back to the first activity
				finish();
			}
		}
	}
	
	/*
	 * Author : Dhruv
	 * Purpose : Clears all fields
	 */
	public void clearFields()
	{
		//Set text of all editable fields as blank
		 	FirstName.setText("");
		    LastName.setText("");
		    PhoneNo.setText("");
		    EMail.setText("");
	}
	
	/*
	 * Author : Dhruv
	 * Purpose : Checks validity, first name field should not be empty
	 */
	private boolean checkValidity(String FName)
	{
		// checks first name for nulls or if it's empty
		if(FName.equals(null) || FName.length() <= 0)
		{
			// Non-Invasive Toast to tell the user he needs to enter a data first 
			Toast.makeText(getBaseContext(), "Please enter your First Name !", Toast.LENGTH_SHORT).show();
			Log.i("Error won't save:", "First Name Field was empty!");
			return false;
		}
		
		return true;
	}
	
}
