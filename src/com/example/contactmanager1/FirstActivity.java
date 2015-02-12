package com.example.contactmanager1;

import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
* Author : Dhruv & Usha
* Date : 31/Oct/2014
* Purpose : Define the First Activity, the first screen that shows up with the contact list. We deifne custom fonts for the list along with using a Custom ArrayAdaptor to display the list
*/
public class FirstActivity extends Activity implements OnItemClickListener {
	
	private final Context context = this;
	private ListView listView1;
	private TextView textHeading,textName, textPhoneNo;
	
	// to count the no of clicks to reverse the sort 
	private static int noOfClicksName=0, noOfClicksPhone=0;
	
	// Custom ArrayAdapter for Person List
	PersonAdaptor adapter;
	
	/*
	* Author : Usha
	* Date : 31/Oct/2014
	* Purpose : On Create will fire the following events, customization of fonts is done
	*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        // Creating path for file and directory
        createPath();
        // Creating the welcome toasters
        showToasters();
        
        Typeface typeFace2=Typeface.createFromAsset(getAssets(),"fonts/Segoe-UI-Symbol.ttf");
	    textHeading = (TextView)findViewById(R.id.textHeading);
	    textName = (TextView)findViewById(R.id.textName);
	    textPhoneNo = (TextView)findViewById(R.id.textPhoneNo);
	    textHeading.setTypeface(typeFace2);
	    textName.setTypeface(typeFace2);
	    textPhoneNo.setTypeface(typeFace2);
		} // End of onCreate()
    
    /*
	* Author : Dhruv
	* Date : 03/Nov/2014
	* Purpose : On Resume will fire the following events
	*/
    @Override
	protected void onResume() {
    	super.onResume();

    	// Initialize to create the table.
		makeTable();

		// To refresh the List in case a data has been added or removed
		adapter.notifyDataSetChanged();
		
		// When Name is clicked sort by First name, toggles the sort from A-Z and from Z-A
		textName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				++noOfClicksName;
				// calls the sorting function
				sortListByName();
			}
		});
		
		// When Phone No is clicked sort by First name, toggles the sort from A-z and from Z-A
		textPhoneNo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				++noOfClicksPhone;
				// calls the sorting function
				sortListByPhoneNo();
			}
		});
    } // end of onResume()

	// For Menu Option layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
    	// format - r.menu.name_of_menu.xml, menu
    	// Menu XML defined in res/menu/name_of_menu.xml
        getMenuInflater().inflate(R.menu.first, menu);
        return true;
    }

    /*
     * Author - Usha M
     * Used for handling presses on action bar menu items 
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
	        case R.id.action_save:
	        	// Launch the method for save action
	        	actionSave();
	        	return true;
	            
	        case R.id.action_Help:
	        	// Show the about Dialog
	        	Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Segoe-Regular.ttf");
	        	LayoutInflater inflater = getLayoutInflater();
	        	View layout = inflater.inflate(R.layout.toast_layout_custom, (ViewGroup) findViewById(R.id.toast_layout_error));

	        	TextView textWelcome = (TextView) layout.findViewById(R.id.text);
	        	textWelcome.setTypeface(typeFace);
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
     * Purpose - Used for creating the path. A stub function.
     */
    public boolean createPath()
    {
    	FileIO fileIO = new FileIO();
    	fileIO.createPath( Environment.getExternalStorageDirectory() );
    	Log.i("Create Path: " , Environment.getExternalStorageDirectory().toString() );
    	return true;
    }
    
    /*
     * Author - Usha M
     * Purpose - Used for reading to the contact List. A stub function.
     */
    public List<Person> readContact(){
    	
    	FileIO fileIO = new FileIO();
    	List<Person> personList = fileIO.readFile( Environment.getExternalStorageDirectory() );
    	
    	return personList;
    }
    
    /*
	* Author : Dhruv
	* Date : 31/Oct/2014
	* Purpose : Creates a custom ArrayAdaptor List
	*/
    public boolean makeTable()
    {
    	// set the sort count to 0
    	noOfClicksName=0;
    	noOfClicksPhone=0;
    	
    	//For setting the font
    	Typeface fontHeading=Typeface.createFromAsset(getAssets(),"fonts/Segoe-UI-Symbol.ttf");
    	Typeface fontBody=Typeface.createFromAsset(getAssets(),"fonts/Segoe-Regular.ttf");
    	
    	// read list form the disk
    	final List<Person> listPerson = readContact();
        
    	// Call the Person Adaptor, Person Adaptor is our Customized ArrayAdaptor!
    	adapter = new PersonAdaptor(this, R.layout.listview_item_row, listPerson, fontHeading, fontBody);
        
    	listView1 = (ListView)findViewById(R.id.listView1);
             
        listView1.setAdapter(adapter);
        
        // set onTouch or onClick lister
        listView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                
            	// Call Edit function
            	actionEdit(position);
            }
        });
        
        //register for onLoongClick give context
        registerForContextMenu(listView1);  
    	return true;
    }
    
    
    /*
	* Author : Usha
	* Date : 31/Oct/2014
	* Purpose : Show start time coasters 
	*/
    public void showToasters()
    {
    	//First coaster
    	Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Segoe-Regular.ttf");
    	LayoutInflater inflater = getLayoutInflater();
    	View layout = inflater.inflate(R.layout.toast_layout_custom, (ViewGroup) findViewById(R.id.toast_layout_error));

    	TextView textWelcome = (TextView) layout.findViewById(R.id.text);
    	textWelcome.setTypeface(typeFace);
    	textWelcome.setText("Welcome to the Contact Manager app ! App used to store User Contact Information. This app is made by Usha & Dhruv!");

    	Toast toast = new Toast(getApplicationContext());
    	toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    	toast.setDuration(Toast.LENGTH_LONG);
    	toast.setView(layout);
    	toast.show();
    	
    	//Second coaster
    	LayoutInflater inflater2 = getLayoutInflater();
    	View layout2 = inflater2.inflate(R.layout.toast_layout_custom, (ViewGroup) findViewById(R.id.toast_layout_error));
    	TextView text1 = (TextView) layout2.findViewById(R.id.text);
    	text1.setTypeface(typeFace);
    	text1.setText("Click here, at the top to add contacts ...");

    	Toast toast2 = new Toast(getApplicationContext());
    	toast2.setGravity(Gravity.TOP, 0, 100);
    	toast2.setDuration(Toast.LENGTH_LONG);
    	toast2.setView(layout2);
    	toast2.show();
    	
    	LayoutInflater inflater3 = getLayoutInflater();
    	View layout3 = inflater3.inflate(R.layout.toast_layout_custom, (ViewGroup) findViewById(R.id.toast_layout_error));
    	TextView text3 = (TextView) layout3.findViewById(R.id.text);
    	text3.setTypeface(typeFace);
    	text3.setText("Click on any contact to open up edit view! Long press the contact to open more options!");

    	Toast toast3 = new Toast(getApplicationContext());
    	toast3.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
    	toast3.setDuration(Toast.LENGTH_LONG);
    	toast3.setView(layout3);
    	toast3.show();
    }// End of showToaster()


    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	// Create a context menu on long click on list
	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Quick Tasks");  
        menu.add(0, v.getId(), 0, "Edit Contact");
        menu.add(0, v.getId(), 0, "Delete Contact");
        menu.add(0, v.getId(), 0, "Add another contact");
        menu.add(0, v.getId(), 0, "Sort contacts list by Name");
        menu.add(0, v.getId(), 0, "Sort contacts list by Phone No");
    }  
  
	/*
	* Author : Dhruv
	* Date : 31/Oct/2014
	* Purpose : Define the context menu functions
	*/
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
    	
        if(item.getTitle()=="Edit Contact"){
        		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        		actionEdit( (int) info.id );
        	}  
        else if(item.getTitle()=="Delete Contact"){
        		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        		
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
						actionDelete( (int) info.id );
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
        else if(item.getTitle()=="Add another contact"){
        		actionSave();
        	}
        else if(item.getTitle()=="Sort contacts list by name"){
        		++noOfClicksName;
        		sortListByName();
        	}
        else if(item.getTitle()=="Sort contacts list by phone no"){
        		++noOfClicksPhone;
        		sortListByPhoneNo();
        	}
        else {
        		return false;
        	}  
    return true;  
    }  
      
    /*
	* Author : Usha
	* Date : 31/Oct/2014
	* Purpose : Start SAVE activity
	*/
    private void actionSave()
    {
    	// Creating an intent for the saveActivity
    	Intent saveIntent = new Intent(FirstActivity.this, SaveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("FName", "");
        bundle.putString("LName", "");
        bundle.putString("PNo", "");
        bundle.putString("Email", "");
        bundle.putString("EditMode", "Save");
        saveIntent.putExtras(bundle);
        startActivity(saveIntent);
    }
    
    /*
	* Author : Usha
	* Date : 31/Oct/2014
	* Purpose : Start edit activity
	*/
    private void actionEdit(int position)
    {
    	final List<Person> listPerson = readContact();
		Person p = listPerson.get( position );
    	
        Intent editintent = new Intent(FirstActivity.this, EditActivity.class);
         Bundle bundle = new Bundle();
         bundle.putString("FName", p.getFirstName() );
         bundle.putString("LName", p.getLastName() );
         bundle.putString("PNo", p.getPhoneNo() );
         bundle.putString("Email", p.getEmail() );
         bundle.putString("EditMode", "Edit");
         editintent.putExtras(bundle);
         startActivity(editintent);
    }
    
    /*
	* Author : Usha
	* Date : 31/Oct/2014
	* Purpose : Start delete activity
	*/
    private void actionDelete(int position)
    {
    	final List<Person> listPerson = readContact();
		Person p = listPerson.get( position );
		
		FileIO fileIO = new FileIO();
		
		// First delete the existing record
		fileIO.deleteRecord( Environment.getExternalStorageDirectory(), p.getFirstName(), p.getLastName(), p.getPhoneNo(), p.getEmail() );
		
		// Initialize to create the table.
		makeTable();

		// To refresh the List in case a data has been added or removed
		adapter.notifyDataSetChanged();
    }// end of actionDelete()
    
    /*
	* Author : Dhruv
	* Date : 03/Nov/2014
	* Purpose : Sorts the list by Name, both form A-Z and from Z-A
	*/
    private void sortListByName()
    {
    	adapter.sort(new Comparator<Person>() {
    	    @Override
    	    public int compare(Person lhs, Person rhs) {
    	    	if(noOfClicksName%2==0)
    	    		return rhs.getFirstName().compareTo(lhs.getFirstName());
    	    	else
    	    		return lhs.getFirstName().compareTo(rhs.getFirstName());
    	    }
    	});
    }
    
    /*
	* Author : Dhruv
	* Date : 03/Nov/2014
	* Purpose : Sorts the list by Phone No, both from 0-9 and from 9-0
	*/
    private void sortListByPhoneNo()
    {
    	adapter.sort(new Comparator<Person>() {
    	    @Override
    	    public int compare(Person lhs, Person rhs) {
    	    	if(noOfClicksPhone%2==0)
    	    		return rhs.getPhoneNo().compareTo(lhs.getPhoneNo());
    	    	else
    	    		return lhs.getPhoneNo().compareTo(rhs.getPhoneNo());
    	    }
    	});
    }
} // end of class