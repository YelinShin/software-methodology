package controller;

import java.io.*;
import java.util.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;


import model.User;
import model.UserList;
import model.Album;


public class AdminPageController {
	public static Stage admin_mainstage;
	private UserList userList; 
	private ArrayList<User> users;
	private ObservableList<String> str_users;
	
	@FXML public ListView<String> list_users; 
	
	@FXML private Button btn_Logout;
	@FXML private Button btn_Create;
	@FXML private Button btn_Delete;
	
	/**
	 * @author Yelin
	 * @param stage
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void setStage (Stage stage) throws ClassNotFoundException, IOException{
		try {
			userList = UserList.read();
			users = userList.getUserList();
		}catch(Exception err) {
			return;
		}
		
		str_users = FXCollections.observableArrayList();
		 for(int i = 0; i < users.size(); i++){
			 if(users.isEmpty()) {
				 break;
			 }else {
				 str_users.add(users.get(i).getUsername());
			 }
			 
		 }
		 
		 list_users.setItems(str_users);
		 list_users.getSelectionModel().select(0);
	}
	
	
	@FXML
	public void logout() throws IOException{
		Stage primaryStage = (Stage) btn_Logout.getScene().getWindow();
		Parent root = FXMLLoader.load((getClass().getResource("/view/Login.fxml")));
		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	@FXML
	public void addUser() throws IOException{
		try {
			
			
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminCreateUser.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Create user");
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    CreateUserController createusercontroller = fxmlLoader.getController();
		    createusercontroller.setStage(stage);
		    admin_mainstage = (Stage)btn_Create.getScene().getWindow();
		    //admin_mainstage.close();
		    stage.show();
		}catch (Exception e) {
			System.out.println("error in add user window");
		}
	}
	
	@FXML
	public void deleteUser() throws IOException{
		
		String str_selectedUser = list_users.getSelectionModel().getSelectedItem();
		User selectedUser = null;
		
		for(int i = 0; i < users.size(); i++){
			 if(users.isEmpty()) {
				 break;
			 }else {
				 if((users.get(i).getUsername()).equals(str_selectedUser)) {
					 selectedUser = users.get(i);
				 }
			 }
			 
		 }
		
		if (str_selectedUser.equals("admin")){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DELETE NEW USER");
			alert.setContentText("Cannot delete admin");
			alert.showAndWait();
			return;
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete User");
		alert.setHeaderText("Delete User");
		alert.setContentText("Are you sure you want to remove this user?");

		Optional<ButtonType> result = alert.showAndWait();
		
		try {
			if (result.get() == ButtonType.OK){
				userList.removeUser(selectedUser);
				UserList.write(userList);
				
				str_users = FXCollections.observableArrayList();
				for(int i = 0; i < users.size(); i++){
					 if(users.isEmpty()) {
						 break;
					 }else {
						 str_users.add(users.get(i).getUsername());
					 }
					 
				 }
				list_users.setItems(str_users);
				list_users.getSelectionModel().select(0);
				
			}
		} catch(Exception e) {
			System.out.println("error in delete user window");
		}

	}
}