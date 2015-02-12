package com.example.contactmanager1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
* Purpose : Define the Edit scrren. USer can view his info, edit it as needed, or delete the record
*/
public class EditActivity extends Activity {
	final Context context = this;
	String oldFName;
    String oldLName; 
    String oldPNo; 
    String oldEmail;
	
	EditText FirstName;
	EditText LastName;
	EditText PhoneNo;
	EditText EMail;
	Button save, clear, delete;
	String EditMode="Save"; // Default Editing Mode for this Activity is "Save" Mode. It also supports an "Edit" Mode.
	
	/*
	* Author : Dhruv & Usha
	* Date : 31/Oct/2014
	* Purpose : Sets the oncreate fun
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		// Displays the back button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		FirstName = (EditText) findViewById(R.id.editFName);
		LastName = (EditText) findViewById(R.id.editLName);
		PhoneNo = (EditText) findViewById(R.id.editPhoneNo);
		EMail = (EditText) findViewById(R.id.editEmail);
		save = (Button) findViewById(R.id.buttonSave);
		clear = (Button) findViewById(R.id.buttonClear);
		delete = (Button) findViewById(R.id.buttonDelete);
		
		// Get the message from the intent
	    Bundle bundle = getIntent().getExtras();
	    oldFName = bundle.getString("FName");
	    oldLName = bundle.getString("LName"); 
	    oldPNo = bundle.getString("PNo"); 
	    oldEmail = bundle.getString("Email");
	    EditMode = bundle.getString("Edit");
	    
	    // Set the values of the edit fields
	    FirstName.setText(oldFName);
	    LastName.setText(oldLName);
	    PhoneNo.setText(oldPNo);
	    EMail.setText(oldEmail);
	    
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
	    save.setTypeface(typeFace2);
	    clear.setTypeface(typeFace2);
	    delete.setTypeface(typeFace2);
	    
		// action listner for clear, will clear all fields
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearFields();
				Log.i("Fields:", "All fields cleared!");
			}
		});
		
		// action listner for save, when save is clicked , will upadte record
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Save pressed :", "Will try to save edited record!");
				editRecord();
			}
		});
		
		// add button listener
		delete.setOnClickListener(new OnClickListener() {
 
		  @Override
		  public void onClick(View arg0) {
 
			// in case person pressed clear by mistake before deleting, this will bring the details for which we need to delete..
			FirstName.setText(oldFName);
			LastName.setText(oldLName);
			PhoneNo.setText(oldPNo);
			EMail.setText(oldEmail);
				
			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Segoe-UI-Symbol.ttf");
			// custom dialog
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.delete_box_custom);
			dialog.setTitle("Confirm your deletion!");
			
			// set the custom dialog components - text, image and button
			TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setTypeface(typeFace);
//			ImageView image = (ImageView) dialog.findViewById(R.id.image);
 
			Button dialogBtnYes = (Button) dialog.findViewById(R.id.dialogBtnYes);
			dialogBtnYes.setTypeface(typeFace);
			// if button is clicked, close the custom dialog
			dialogBtnYes.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("Delete pressed :", "Then Pressed YES on confirmation");
					Log.i("Delete pressed :", "Trying to delete record");
					// we cancel the dialog so that the window is not leaked
					dialog.cancel();
					// call the delete operation
					actionDelete();
				}
			});
			Button dialogBtnNo = (Button) dialog.findViewById(R.id.dialogBtnNo);
			dialogBtnNo.setTypeface(typeFace);
			// if button is clicked, close the custom dialog
			dialogBtnNo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("Delete pressed :", "Then Pressed NO on confirmation");
					dialog.dismiss();
				}
			});
			dialog.show();
		  }
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second_edit, menu);
		return true;
	}

	/*
     * Author - Usha M
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) 
        {
	        case R.id.action_delete:

	        	Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Segoe-UI-Symbol.ttf");
				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.delete_box_custom);
				dialog.setTitle("Confirm your deletion!");
				
				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setTypeface(typeFace);
//				ImageView image = (ImageView) dialog.findViewById(R.id.image);
	 
				Button dialogBtnYes = (Button) dialog.findViewById(R.id.dialogBtnYes);
				dialogBtnYes.setTypeface(typeFace);
				// if button is clicked, close the custom dialog
				dialogBtnYes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.i("Delete pressed :", "Then Pressed YES on confirmation");
						Log.i("Delete pressed :", "Trying to delete record");
						// we cancel the dialog so that the window is not leaked
						dialog.cancel();
						// call the delete operation
						actionDelete();
					}
				});
				Button dialogBtnNo = (Button) dialog.findViewById(R.id.dialogBtnNo);
				dialogBtnNo.setTypeface(typeFace);
				// if button is clicked, close the custom dialog
				dialogBtnNo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.i("Delete pressed :", "Then Pressed NO on confirmation");
						dialog.dismiss();
					}
				});
				dialog.show();
				
	        	return true;
	        case R.id.action_Help:
	        	// Show the about Dialog
	        	Typeface typeFace2=Typeface.createFromAsset(getAssets(),"fonts/Segoe-Regular.ttf");
	        	LayoutInflater inflater = getLayoutInflater();
	        	View layout = inflater.inflate(R.layout.toast_layout_custom, (ViewGroup) findViewById(R.id.toast_layout_error));

	        	TextView textWelcome = (TextView) layout.findViewById(R.id.text);
	        	textWelcome.setTypeface(typeFace2);
	        	textWelcome.setText("Welcome to the Contact Manager app ! App made by Usha and Dhruv . . . " +
	        			"This is an address book app! Have fun entering your personal information and viewing them ! Thanks for using our app ! :)");

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
     * Used for saving an edited record to a file
     */
	public void editRecord()
	{
		String FName = FirstName.getText().toString().trim();
		String LName = LastName.getText().toString().trim();
		String PNo = PhoneNo.getText().toString().trim();
		String Email = EMail.getText().toString().trim();
		
		FileIO fileIO = new FileIO();
		
		if(checkValidity( FName, LName, PNo, Email))
		{
			// First delete the existing record
			fileIO.deleteRecord( Environment.getExternalStorageDirectory(), oldFName, oldLName, oldPNo, oldEmail );
			
			// Then add the new record with new details...
			String lineToWrite = FName + "|" + LName + "|" + PNo + "|" + Email + "|";
			
			Log.i("Will try to to save : ", lineToWrite);
			
			boolean flg = fileIO.saveToDisk( Environment.getExternalStorageDirectory(),lineToWrite );
			if(flg)
			{
				// Logs & Toast to let the user an developer know the record was saved successfully
				Log.i("Record saved to file successfully", "");
				Toast.makeText(getBaseContext(), "Record saved to File !", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
	
	/*
     * Author - Dhruv
     * Used for saving a record to a file
     */
	public void actionDelete()
	{
		FileIO fileIO = new FileIO();
		
		// First delete the existing record
		fileIO.deleteRecord( Environment.getExternalStorageDirectory(), oldFName, oldLName, oldPNo, oldEmail );
		
		// End this activity
		finish();
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
	 * Purpose : Clears all fields
	 */
	private boolean checkValidity(String FName,String LName,String PNo,String Email)
	{
		// checks first name for nulls or if it's empty
		if(FName.equals(null) || FName.length() <= 0)
		{
			// Non-Invasive Toast to tell the user he nned to enter a data first 
			Toast.makeText(getBaseContext(), "Please enter your First Name !", Toast.LENGTH_SHORT).show();
			Log.i("Error won't save:", "First Name Field was empty!");
			return false;
		}
		return true;
	}
}
