package com.example.practise;

/** 
 * @author vijaykrishn vxv140430
 * @author pujitha sri lakshmi pxp142730
 * Class CS 6301.022
 * Professor John Cole
 * This class is responsible for reading, writing and deleting the contact to and from
 * the file. 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import android.os.Environment;

public class FileIO {

	File file = null;
    FileWriter fw = null;
    int linect = 0;
    Scanner s = null;
    boolean bExists;
    String strFilename = "Contacts.txt";
    
    /**
     * This method reads the file and stores as a list of contact objects and returns the list.
     * @author vijaykrishn
     * @return List<Contact>
     *  */
    
    public List<Contact> readFile(){
    	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ContactFolder");
    	List<Contact> myContactList = new ArrayList<Contact>();
    	if (newFolder.exists()){
    		file = new File(newFolder, strFilename);
    		if (!file.canRead()){
    	    	System.out.println("Can't read");
    	    }
    		if(file.canRead()){
    			try {
    				FileReader filein = new FileReader(file);
    		        BufferedReader br = new BufferedReader(filein);
    		        String line = null, con[];
    		        while((line = br.readLine()) != null){
    		        	con = line.split("\t");
                        Contact filedata = new Contact(con[0].split(" ")[0], con[0].split(" ")[1], 
                        		con[1], con[2]);
                        myContactList.add(filedata);
    		        }
    		        filein.close();
    		        br.close();
    			}
    			catch (FileNotFoundException ex) {
                    Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
                }
    		}
    	}
    	return myContactList;
    }
    
    /**
     * @author vijaykrishn
     * This method writes the contact object to the file.
     * @param objContact
     *  */
    
    public void writeFile(Contact objContact){
	    try {
	    	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ContactFolder");
	    	if (!newFolder.exists()){
	    		bExists = newFolder.mkdirs();
	        }
	    	try{
	    		file = new File(newFolder, strFilename);
	    		file.createNewFile();
	        }
	    	catch (Exception ex){
	    		System.out.println("ex: " + ex);
	        }
	    }
	    catch (Exception e){
	    	System.out.println("e: " + e);
	    }
	    if (!file.canWrite()){
	    	System.out.println("Can't write");
	    }
	    try {
	    	fw = new FileWriter(file, true);
	    	String contact;
	    	
            contact = objContact.getFirstName().toString().trim()+ " "
                    + objContact.getLastName().toString().trim()+ "\t"
                    + objContact.getEmail().toString().trim() + "\t"
                    + objContact.getPhoneNumber().toString().trim();
            fw.write(contact + "\n");
            fw.close();
	    }
	    catch (Exception ex){
	    	System.out.println("Error creating PW: " + ex.getMessage());
	    }
    }
    
    /**
     * @author vijaykrishn
     * This method takes the complete name as parameter and returns true if it deletes successfully. 
     * @param String
     * @return boolean
     */
    
    public boolean deleteRow(String name){
        boolean isSuccess = false;
        try{
        	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ContactFolder");
        	file = new File(newFolder, "Contacts.txt");
        	file.createNewFile();
            File tmpfile = new File(newFolder, "ContactsTmp.txt");
            tmpfile.createNewFile();
            FileInputStream filein;
            BufferedWriter bw = new BufferedWriter(new FileWriter(tmpfile));
            filein = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(filein));
            String line, parts[];
            while((line = br.readLine()) != null){
                parts = line.split("\t");
                if(parts[0].equals(name)){
                    
                }
                else
                    bw.write(line + "\n");
            }
            bw.close();
            br.close();
            file.delete();
            tmpfile.renameTo(file);
            isSuccess= true;
        }
        catch (FileNotFoundException ex) {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return isSuccess;
    }
}
