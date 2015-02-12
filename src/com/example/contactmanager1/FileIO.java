package com.example.contactmanager1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/*
 * Author - Usha, Dhruv
 * Date : 30/Oct/2014
 * Purpose : Function creates the directory and file at the path if they don't exist, and insert dummy data inside the file.
 *  Debugging Mechanism has been addded, contains logging at all most all levels
 */
public class FileIO {
	
	/*
	 * Author - Usha
	 * Date : 30/Oct/2014
	 * Purpose : Function creates the directory and file at the path if they don't exist, and insert dummy data inside the file.
	 */
	public boolean createPath(File path)
    {
		try {
			File directory = new File(path, "Contact");
//			File directory = new File("/data/contact/", "Contact");
			File file = new File(path, "Contact.txt");
			
			Log.i("Path of directory : ", path.toString());
			Log.i("Path of file : ", file.toString());
			
			/*
			 * For testing , deleting the file in each increment
			 */
//			System.out.println();
//			Log.i("Remove this line ", "Deleting file for testing first...");
//			file.delete(); 
    	
	    	// Checks if directory doesn't exist, if it doesn't then calls the create dir. function
	    	if(!directory.exists())
	    	{
	    		// Creates a dir. referenced by this file
	    		directory.mkdir();
	    	}
	    	// Checks if file doesn't exist, if it doesn't then calls the create file function
	    	if(!file.exists())
	    	{
	    		// Creates a file
	    		file.createNewFile();
	    	}
	    	
	    	// if false that means file is empty, insert it with dummy data
	    	if(fileIsEmpty(file))
	    	{
	    		Log.i("Is file empty ? :", "True");
	    		Log.i("Saving dummy records to file : ", "");
	    		String line = "Usha|M|+1009|email4@.com|";
	    		saveToDisk(path, line);
	    		line = "Dhruv|Arora|+0306|email@.com|";
	    		saveToDisk(path, line);
	    		line = "Dost|Arora|+1706|email2@.com|";
	    		saveToDisk(path, line);
	    		line = "Soniya|Arora|+2201|email3@.com|";
	    		saveToDisk(path, line);
	    		line = "Ajay|Arora|+1612|email4@.com|";
	    		saveToDisk(path, line);
	    		line = "Guru|Mahesh|+0812|email5@.com|";
	    		saveToDisk(path, line);
	    		
	    	}
	    	else
	    		Log.i("Is file empty ? :", "False");

	    	return true; // if everything successful, return true
		}
		catch(Exception e)
		{
			Log.e("dir/file not created : ", e.getMessage());
			return false;
		}
    }
	
	/*
	 * Author - Dhruv
	 * Date : 30/Oct/2014
	 * Purpose : Function to save a record to disk. Parameters are the file path and the line to be saved. Return true if save successful
	 */
	public boolean saveToDisk( File path, String lineToWrite )
	{
		File file = new File(path, "Contact.txt");
		try{
			// Assume default encoding
			// true denotes file will not be overwritten each time, but will append data in same file.
			FileWriter fileWriter =  new FileWriter(file, true);
			
			//wrap FileWriter in BufferedWriter
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			//write the line to file
			writer.write(lineToWrite);
			//add a new line at the end
			writer.newLine();
			
			// close and flush connections
	        fileWriter.flush();
	        writer.flush();
			writer.close();
			fileWriter.close();
			Log.i("Record written to file : ", "success");
			Log.i("Path where file was stored : ", file.toString());
			return true;
		}
		catch(Exception e)
		{
			Log.e("Record not written to file : ", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Author - Usha
	 * Date : 30/Oct/2014
	 * Purpose : Function to check if file on disk is empty. Parameters are the file path. Returns true if file was empty
	 */
	public boolean fileIsEmpty(File file)
	{
		Boolean flg = true; // flag which tells us if the file is completely empty or not. True assumes file is empty
		try
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line ;
			
			while((line = reader.readLine())!=null)
			{
				String delimited[] = null;
				System.out.println("line"+line);
				// Split each line into it's constituent properties, then the result is stored in a string array.
				delimited = line.split("\\|");
				
				
				System.out.println("length "+delimited.length);
				
				// This will make sure to take only the correct data in the correct format
				if(delimited.length >= 1)
				{
					flg=false;
				}
			}
			// close and flush connections
			reader.close();
			fileReader.close();
			Log.i("Path where file was check for empty : ", file.toString());
			return flg;
		}
		catch(Exception e)
		{
			Log.e("File Error : ", e.getMessage());
			e.printStackTrace();
			return flg;	
		}
	}
	
	/*
	 * Author - Dhruv
	 * Date : 30/Oct/2014
	 * Purpose : Function to read the file from disk. Parameters are the file path. Returns the List read.
	 */
	public List<Person> readFile(File path)
	{
		File file = new File(path, "Contact.txt");
		List<Person> personList = new  ArrayList<Person>();
		try
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line;
			
			while((line = reader.readLine())!=null)
			{
				String delimited[] = null;
				// Split each line into it's constituent properties, then the result is stored in a string array.
				delimited = line.split("\\|");
				// This will make sure to take only the correct data in the correct format
				if(delimited.length == 4)
				{
					//	String array is used to initialize a Person object.
					Person p1 = new Person( delimited[0], delimited[1], delimited[2], delimited[3] );
					// Each Person object is stored in a List of Person
					personList.add(p1);
				}
			}
			// close and flush connections
			reader.close();
			fileReader.close();
			Log.i("File read successfully", "");
			Log.i("Path where file was read from : ", file.toString());
			return personList;
		}
		catch(Exception e)
		{
			Log.e("File not Read : ", e.getMessage());
			return null;	
		}
	}
	
	/*
	 * Author - Dhruv
	 * Date - 01/Nov/2014
	 * Purpose - Used for deleting a record the user selects from the GUI...
	 * First it reads the list from the disk. Then it searches for the record from the read List and then deletes the record if found and then stores the new list in the file...
	 */
	public boolean deleteRecord(File path, String varFirstName, String varLastName, String varPhoneNo, String varEmail)
	{
		File file = new File(path, "Contact.txt");
		// Boolean to let us know if the record was found and deleted
		boolean wasDeleted = false;
		try{
			// will be used for storing the list after reading from the disk
			List <Person> listRead = new ArrayList<Person>();
				
			// Read the current data into the list
			listRead = readFile(path);
			file.delete();
			file.createNewFile();
			/*
			 * Iterate over each object and check if the name property of each object is the same as the one we have to delete, if so then, delete the object and then write the list again to file
			 */
			for(Person person : listRead)
			{
				if(person.getFirstName().equalsIgnoreCase(varFirstName) && person.getLastName().equalsIgnoreCase(varLastName) 
						&& person.getPhoneNo().equalsIgnoreCase(varPhoneNo) && person.getEmail().equalsIgnoreCase(varEmail) )
				{
					Log.i("Record found.. .deleting...", "");
					listRead.remove(person);
					wasDeleted = true; // set to true, means record was deleted
					break; // Break here, as as only one occurrence of a particular combination of First Name + Middle Name + Last Name are allowed
				}
			}
			
			// if record was deleted then write the new list to disk..
			if(wasDeleted)
			{
			    boolean wasWritten = writeList(file, listRead); // Boolean to know if the file was successfully written
			    Log.i("Record Deleted Successfully", "");
			    wasDeleted=wasWritten;
			}
			Log.i("Path where record was del from : ", file.toString());
			return wasDeleted;							
		}
		catch (Exception e)
		{
			Log.e("File not deleted : ", e.getMessage());
		}
		return wasDeleted;
	}
	
	/*
	 * Author - Usha
	 * Date - 01/Nov/2014
	 * Purpose - Used to write the whole list to the Disk.. works similar to saveRecordsToDisk function, only difference is it iterates over each record and saves it
	 */
	public boolean writeList(File file, List<Person> listToWrite)
	{
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null; 
		try{
			Log.i("Trying to write the whole list to disk ", "");
			// Assume default encoding
			// We usually write as, FileWriter(fileName,true). BUT NO 'true' here denotes file will not be overwritten each time, but will insert data in same file.
			fileWriter = new FileWriter(file);
			//wrap FileWriter in BufferedWriter
			bufferedWriter= new BufferedWriter(fileWriter);
			
			for(Person p : listToWrite)
		    {
				bufferedWriter.write( p.getFirstName()+"|"+p.getLastName()+"|"+p.getPhoneNo()+"|"+p.getEmail()+"|" );
				bufferedWriter.newLine();
			}

			// close and flush connections
	        fileWriter.flush();
	        bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
			Log.i("Whole list written : ", "success");
			Log.i("Path where list was stored : ", file.toString());
			return true;
		}
		catch (Exception e)
		{
			Log.e("list not written : ", e.getMessage());
			return false;
		}
	}
	
	
	
}// end of Class FileIO
