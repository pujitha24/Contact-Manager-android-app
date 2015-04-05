package com.example.practise;

/** 
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 * Class CS 6301.022
 * Professor John Cole
 * This is an android app that allows user to add edit and modify a contact. The contact
 * is written to a text file stored inside contacts folder created inside downloads folder. 
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Mainactivity describes the starting point of the project.
 * This activity extends the ActionBar.
 */


public class MainActivity extends ActionBarActivity {

	private List<Contact> contacts = new ArrayList<Contact>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateContacts();
        populateListview();
        registerClickCallback();
    }
    
    /**
     * @author vijaykrishn vxv140430
     * This method is used to select a contact from the list view, which in turn brings
     * the second activity. 
     */
    
    private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.Contacts);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override 
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LinearLayout layOutObj = (LinearLayout)view;
				Intent intent = new Intent(MainActivity.this, AddContact.class);
				
				TextView firstNameText = (TextView) layOutObj.getChildAt(0);
				String firstName = firstNameText.getText().toString();
				intent.putExtra("com.practise.FirstName", firstName);
				intent.putExtra("com.practise.Position", position+"");
				startActivity(intent);
			}
		});
	}
    
    /**
     * @author vijaykrishn vxv140430
     * This method populate the contacts after reading from the file.
     */
    
    private void populateContacts() {
    	FileIO obj = new FileIO();
    	contacts = obj.readFile();
	}
    
    /**
     * @author vijaykrishn vxv140430
     * This sets the list view to the activity.
     */
    
    private void populateListview() {
		ArrayAdapter<Contact> adapter = new MyContactAdapter();
		ListView list = (ListView)findViewById(R.id.Contacts);
		list.setAdapter(adapter);
	}
    
    /**
     * @author vijaykrishn
     * Inner class to create our custom defined ArrayAdapter by overriding the methods.
     */
    
    private class MyContactAdapter extends ArrayAdapter<Contact>{
		public MyContactAdapter(){
			super(MainActivity.this, R.layout.item_view,contacts);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if(itemView==null)
			{
				itemView= getLayoutInflater().inflate(R.layout.item_view, parent,false);
			}
			Contact current = contacts.get(position);
			
			TextView textName = (TextView) itemView.findViewById(R.id.namesField);
			textName.setText(current.getFirstName() + " " + current.getLastName());
			TextView textContact = (TextView) itemView.findViewById(R.id.contactField);
			textContact.setText(current.getPhoneNumber());
			
			return  itemView;
		}
	}

    /**
	 * @author vijaykrishn vxv140430
	 * This method creates the action bar and creates the menu.
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	/**
	 * @author vijaykrishn vxv140430
	 * The action to be performed when the actionbar item is selected.
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.newSave){
        	Intent intent = new Intent("com.example.practise.AddContact");
        	startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
