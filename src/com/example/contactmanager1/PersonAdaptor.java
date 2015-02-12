package com.example.contactmanager1;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

	/*
	* Author : Dhruv
	* Date : 31/Oct/2014
	* Purpose : Custom ArrayAdapter for Person List.
	* This method overrides the public View getView Method..
	* This method can display an icon, Name_text and then Phone_Text as a list 
	*/
	public class PersonAdaptor extends ArrayAdapter<Person> {

		Context context; 
		int layoutResourceId;    
		List<Person> personList = null;
		Typeface fontHeading;
		Typeface fontText;
		
		// Constructor
		// Context - we can pass the reference of the activity in which we will use our class
		// resource id - of the layout file we want to use for displaying each ListView item. eg: listview_item_row.xml
		// Person[] - array of Person class objects that will be used by the Adapter to display data. 
		public PersonAdaptor(Context context, int layoutResourceId, List<Person> list, Typeface fontHeading, Typeface fontText) {
		    super(context, layoutResourceId, list);
		    this.layoutResourceId = layoutResourceId;
		    this.context = context;
		    this.personList = list;
		    this.fontHeading = fontHeading;
			this.fontText = fontText;
		}
		
		//  Method will be called for every item in the ListView to create views with their properties set as we want.
		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
			
	        View row = convertView;
	        PersonHolder holder = null;
	        
	        if(row == null)
	        {
	        	// Uses the Android built in Layout Inflater to inflate (parse) the xml layout file. 
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new PersonHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtName = (TextView)row.findViewById(R.id.txtTitle);
	            holder.txtPhone = (TextView)row.findViewById(R.id.txt_Phone);
	            
	            holder.txtName.setId(position);
            	holder.txtName.setTypeface(fontText);
            	holder.txtPhone.setTypeface(fontText);
        	  	
	            	
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (PersonHolder)row.getTag();
	        }
	        
	        if(position==7)
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_f6);
	        else if(position==8)
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_f7);
	        else if(position==9)
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m7);
	        else if(position==10)
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m1);
	        else if(position==11)
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_f1);
	        else if(position==12)
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_f7);
	        else if(position==13)
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m7);
	        else
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_gen);
	        
	        // for icons in images
	        if( personList.get(position).getFirstName().equalsIgnoreCase("Usha") )
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_f2);
	        if( personList.get(position).getFirstName().equalsIgnoreCase("Dhruv") )
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m2);
	        if( personList.get(position).getFirstName().equalsIgnoreCase("Dost") )
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m3);
	        if( personList.get(position).getFirstName().equalsIgnoreCase("Soniya") )
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_f3);
	        if( personList.get(position).getFirstName().equalsIgnoreCase("Ajay") )
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m4);
	        if( personList.get(position).getFirstName().equalsIgnoreCase("Dhruv") )
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m5);
	        if( personList.get(position).getFirstName().equalsIgnoreCase("Guru") )
	        	holder.imgIcon.setImageResource(R.drawable.ic_person_m6);
	        
	        holder.txtName.setText( personList.get(position).getFirstName()+" "+personList.get(position).getLastName());
	        holder.txtPhone.setText( "\t\t\t"+personList.get(position).getPhoneNo() );
	        
	        return row;
	    }
	    
	    static class PersonHolder
	    {
	        ImageView imgIcon;
	        TextView txtName;
	        TextView txtPhone;
	    }
	}// end of class
