package controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import model.Album;
import model.Photo;
import model.User;
import model.UserList;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button; 
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class CreateAlbumController {
	
	private Stage userAlbum_stage;
	private ArrayList<Album> albums;
	private User selectedUser;
	private ObservableList<Album> obsList; 
	private int index;
	private UserList userList; 
	private ListView<Album> list_album; 


	@FXML
	private TextField txt_newAlbumName; 
	
	@FXML
	private Button btn_Confirm, btn_OpenFile;
	

	/**
	 * @author Yelin
	 * @param stage
	 * @param username
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void setStage(Stage stage, User username) throws ClassNotFoundException, IOException {
		try{
			this.userAlbum_stage = stage;
			this.selectedUser = username;
			albums = selectedUser.getAlbumlist();
			obsList = FXCollections.observableArrayList(albums);
			
		}catch (Exception err) {
			System.out.println("Create New Album Error for setStage");
			return;
		}
		
		
	}
	
	@FXML
	public void confirmNewAlbum(ActionEvent event) throws IOException, ClassNotFoundException {
		String newalbumName = txt_newAlbumName.getText();
		
		if(newalbumName==null || newalbumName.isEmpty()) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID ADDING NEW ALBUM");
			alert.setContentText("Album name cannot be null");
			alert.showAndWait();
			return;
		}else if(selectedUser.checkDupAlbum(newalbumName)) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID ADDING NEW ALBUM");
			alert.setContentText("Album name already exists, please use another name");
			alert.showAndWait();
			return;
		}
		
		
		Album addAlb = new Album(newalbumName);
		selectedUser.addAlbum(addAlb);
		//obsList.add(obsList.size(), addAlb);
		obsList.add(addAlb);
		try{
			UserList.write(userList);
		}catch (IOException e){
			e.printStackTrace();
		}
	
		txt_newAlbumName.setText("");
		
		Stage createStage = (Stage)btn_Confirm.getScene().getWindow();
		createStage.close();
		
		
		list_album.refresh();
		
	}
	/**
	 * @author Yelin
	 * @param editIndex
	 * @param obsAlbumList
	 * @param album_list
	 * @param userList
	 */
	
	public void setObs(int editIndex, ObservableList<Album> obsAlbumList, ListView<Album> album_list, UserList userList){
		this.obsList = obsAlbumList; 
		this.index = editIndex;
		this.list_album = album_list;
		this.userList = userList; 
	}
	
}
