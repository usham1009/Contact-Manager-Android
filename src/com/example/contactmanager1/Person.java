package com.example.contactmanager1;


/*
 * Author : Usha
 * Date : 30/Oct/2014
 * Purpose - This is a bean class for Person information. Class is used for storing Person Info. 
 * 
 */
public class Person {

	private String FirstName;
	private String LastName;
	private String PhoneNo;
	private String Email;
    
    public Person(){
        super();
    }

    public Person(String firstName, String lastName, String phoneNo,String email) {
		super();
		FirstName = firstName;
		LastName = lastName;
		PhoneNo = phoneNo;
		Email = email;
	}

	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getPhoneNo() {
		return PhoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
}// end of class
