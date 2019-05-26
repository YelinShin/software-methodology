package model;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.EOFException;

public class UserList implements Serializable{
	private static final long serialVersionUID = 51L;
	private ArrayList<User> userList;
	
	static String userDir = System.getProperty("user.dir");
	public static String userListFile = userDir + "/userList.dat";
	static File file = new File(userListFile);
	
	public UserList() {
		this.userList = new ArrayList<User>();
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<User> getUserList(){
		return this.userList;
	}
	
	/**
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		userList.add(user);
	}
	
	/**
	 * 
	 * @param user
	 */
	public void removeUser(User user) {
		userList.remove(user);
	}
	
	/**
	 * 
	 */
	public String toString() {
		String tmpUserList ="";
		if (this.userList == null) {
			return "There are no users.";
		}
		
		for(User x: userList) {
			tmpUserList = tmpUserList + x.getUsername() +"\n";
		}
		
		return tmpUserList;
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User login (String username) {
		for(User x: userList) {
			if(username.equals(x.getUsername())) {
				return x;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean dupUser (String username) {
		for(User x: userList) {
			if(username.equals(x.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static UserList read() throws IOException, ClassNotFoundException{
		UserList allUsers = null;
		ObjectInputStream o;
		file.createNewFile();
		FileInputStream f = new FileInputStream(file);
		
		o = new ObjectInputStream(f);
		allUsers = (UserList)o.readObject();
		o.close();
		return allUsers;
	}
	
	/**
	 * 
	 * @param userList
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void write(UserList userList) throws IOException, ClassNotFoundException{
		file.createNewFile();
		FileOutputStream f = new FileOutputStream(file);
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(userList);
		o.close();
	}
}
