package com.example.practise;

/** 
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 * Class CS 6301.022
 * Professor John Cole
 * This class is responsible for managing the second intent i.e. the fields of the contact and their
 * operations in the action bar.
 */


import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends ActionBarActivity{
	
	Contact contactObj;
	FileIO ioObj = new FileIO();
	EditText fname, lname, email, phone;
	KeyListener nameType, mailType, numberType;
	boolean isEditMode = false, isSave = true, isDelete = false;
	private Menu menu;
	
	/**
	 * @author pujitha pxp142730
	 * This creates the intent
	 */
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactadd);
        int selectedPosition=-1;
        Intent intent = getIntent();
		 String name = intent.getStringExtra("com.practise.FirstName");
		 if(intent.getStringExtra("com.practise.Position")!=null)
		   selectedPosition = Integer.parseInt(intent.getStringExtra("com.practise.Position"));
		    if(name!=null ){
		    	isSave=false;
		    	FileIO oper = new FileIO();
		    	List<Contact> contactList = oper.readFile();
		    	Contact obj = contactList.get(selectedPosition);
		    	isEditMode = true;
		    	isDelete = true;
		    	fname = (EditText)findViewById(R.id.fname);
		    	nameType = fname.getKeyListener();
				lname = (EditText)findViewById(R.id.lname);
				email = (EditText)findViewById(R.id.email);
				mailType = email.getKeyListener();
				phone = (EditText)findViewById(R.id.phone);
				numberType = phone.getKeyListener();
				fname.setText(obj.getFirstName());
				lname.setText(obj.getLastName());
				email.setText(obj.getEmail());
				phone.setText(obj.getPhoneNumber());
				fname.setKeyListener(null);
				lname.setKeyListener(null);
				email.setKeyListener(null);
				phone.setKeyListener(null);
		    }
	}
	
	/**
	 * @author pujitha pxp142730
	 * This method saves the contact to the file.
	 */
	
	public void saveContact(){
		fname = (EditText)findViewById(R.id.fname);
		lname = (EditText)findViewById(R.id.lname);
		email = (EditText)findViewById(R.id.email);
		phone = (EditText)findViewById(R.id.phone);
		if ((fname.getText().toString().trim().equals("")) || (lname.getText().toString().trim().equals("")) ||
				(email.getText().toString().trim().equals("")) || (phone.getText().toString().trim().equals(""))){
			String message = "Please enter all the fields to save the contact.";
			Toast.makeText(AddContact.this, message, Toast.LENGTH_LONG).show();
		}
		else {
			contactObj = new Contact(fname.getText().toString(), lname.getText().toString(),
					email.getText().toString(), phone.getText().toString());
			ioObj.writeFile(contactObj);
			isEditMode = true;
			isSave = false;
			isDelete = true;
			menu.getItem(0).setVisible(isSave);
			menu.getItem(1).setVisible(isDelete);
			menu.getItem(2).setVisible(isEditMode);
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
			startActivity(intent);
			finish();
		}
	}
	
	/**
	 * @author pujitha pxp142730
	 * This method deletes the contact from the file
	 */
	
	public void deleteContact(){
		fname = (EditText)findViewById(R.id.fname);
		lname = (EditText)findViewById(R.id.lname);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				AddContact.this);
			alertDialogBuilder.setTitle("Delete Contact");
			alertDialogBuilder
				.setMessage("Do you want to delete?")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {						
						ioObj.deleteRow(fname.getText().toString() + " " + lname.getText().toString());
						isEditMode = true;
						isSave = false;
						isDelete = true;
						menu.getItem(0).setVisible(isSave);
						menu.getItem(1).setVisible(isDelete);
						menu.getItem(2).setVisible(isEditMode);
						Intent intent = new Intent(AddContact.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
						startActivity(intent);
						finish();
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	}
	
	/**
	 * @author pujitha pxp142730
	 * This method modifies the contact in the file and updates the table.
	 */
	
	public void modifyContact(){
		fname = (EditText)findViewById(R.id.fname);
		lname = (EditText)findViewById(R.id.lname);
		ioObj.deleteRow(fname.getText().toString() + " " + lname.getText().toString());
		fname.setKeyListener(nameType);
		lname.setKeyListener(nameType);
		email.setKeyListener(mailType);
		phone.setKeyListener(numberType);
		isEditMode = false;
		isSave = true;
		isDelete = true;
		menu.getItem(0).setVisible(isSave);
		menu.getItem(1).setVisible(isDelete);
		menu.getItem(2).setVisible(isEditMode);
	}
	
	/**
	 * @author pujitha pxp142730
	 * This method creates the action bar and creates the menu.
	 */
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contact_screen, menu);
		return true;
	}
	
	/**
	 * @author pujitha pxp142730
	 * This method initiates the items in the action bar.
	 */
	
	public boolean onPrepareOptionsMenu(Menu menu){
		this.menu=menu;
		
		//if(isEditMode){
			menu.getItem(1).setVisible(isDelete);
			menu.getItem(2).setVisible(isEditMode);
			menu.getItem(0).setVisible(isSave);
		//}
		return true;
	}
	
	/**
	 * @author pujitha pxp142730
	 * The action to be performed when the actionbar item is selected.
	 */
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save)
			saveContact();
		else if (id == R.id.delete)
			deleteContact();
		else if (id == R.id.edit)
			modifyContact();
		return super.onOptionsItemSelected(item);
	}
}
