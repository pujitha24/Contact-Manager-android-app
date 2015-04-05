package com.example.practise;

/**
 * This is the model of the contact manager which will initialise all the variables.
 * @author vijaykrishn
 */
public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    
    public Contact(String firstName, String lastName, String email, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

	public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}

