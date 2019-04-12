/*Claudia Pan - cp728,
 *Yelin Shin - ys521*/
package controller;

import model.UserList;
import model.User;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.fxml.Initializable;
import java.net.URL;

import java.io.*;
import java.util.*;


public class LoginController implements Initializable {
	
	@FXML         
	private TextField txt_userName; 
	
	@FXML
    private Button btn_Login;
	
	private String username;
	private Stage mainStage; 
	private List<User> users = new ArrayList<User>();
	private UserList userList;
	
	@FXML
	private void loginTo(ActionEvent event) throws IOException, ClassNotFoundException { 
		username = txt_userName.getText().trim();
		
		try{
			userList = UserList.read();
				
		}catch(IOException |ClassNotFoundException e){
			e.printStackTrace();
		}
		
		try{
			if(userList.dupUser(username)){
				if(username.equals("admin")){
					FXMLLoader adminLoad = new FXMLLoader(getClass().getResource("/view/admin.fxml"));
					Parent root = (Parent)adminLoad.load();
					Scene adminScene = new Scene(root);
					Stage adminStage = new Stage();
					adminStage.setScene(adminScene);
					adminStage.setResizable(false);
					adminStage.setTitle("Admin Window");
					AdminPageController adminController = adminLoad.getController();
					adminController.setStage(adminStage);
					mainStage = (Stage)txt_userName.getScene().getWindow();
					mainStage.close();
					adminStage.show();
				}else if(username.equals("stock")){
					FXMLLoader stockLoad = new FXMLLoader(getClass().getResource("/view/useralbum.fxml"));
					Parent root = (Parent)stockLoad.load();
					Scene stockScene = new Scene(root);
					Stage stockStage = new Stage();
					stockStage.setScene(stockScene);
					stockStage.setResizable(false);
					stockStage.setTitle("Admin Window");
					UserAlbumController useralbumcontroller = stockLoad.getController();
					useralbumcontroller.setStage(stockStage);
					mainStage = (Stage)txt_userName.getScene().getWindow();
					mainStage.close();
					stockStage.show();
				}
			}else{ 
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void initialize(URL location,ResourceBundle resources) {
		
	}
	
}