package controller;

import java.io.*;
import java.util.*;

import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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


public class CreateUserController {
	private UserList userList; 
	private ArrayList<User> users;
	
	@FXML private TextField txt_Username;
	@FXML private Button btn_confirm;
	
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
	}
	
	@FXML
	private void confirm(ActionEvent event) throws IOException, ClassNotFoundException{
		String newUser = txt_Username.getText();
		
		if (newUser==null ||newUser.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID ADDING NEW USER");
			alert.setContentText("user name is required!");
			alert.showAndWait();
			return;
		}
		
		for(int i = 0; i < users.size(); i++){
			 if(users.isEmpty()) {
				 break;
			 }else if(users.get(i).getUsername().equals(newUser)){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR MESSAGE");
				alert.setHeaderText("Username already exists!");
				alert.setContentText("Username already exists! please add different username.");
				alert.showAndWait();
				return;	
			 }
			 
		 }
		
		User new_user = new User(newUser);
		userList.addUser(new_user);
		
		UserList.write(userList);
		
		Stage createStage = (Stage)btn_confirm.getScene().getWindow();
		createStage.close();
		
		ObservableList<String> str_users = FXCollections.observableArrayList();
		 for(int i = 0; i < users.size(); i++){
			 if(users.isEmpty()) {
				 break;
			 }else {
				 str_users.add(users.get(i).getUsername());
			 }
			 
		 }
		
		
		
		FXMLLoader adminLoad = new FXMLLoader(getClass().getResource("/view/admin.fxml"));
		Parent root = (Parent)adminLoad.load();
		Scene s = new Scene(root);
		AdminPageController.admin_mainstage.setScene(s);
		AdminPageController adminController = adminLoad.getController();
		adminController.setStage(AdminPageController.admin_mainstage);
		AdminPageController.admin_mainstage.show();

	}
}
