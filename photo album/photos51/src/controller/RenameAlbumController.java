package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.User;
import model.UserList;


public class RenameAlbumController {
	
	private Album album;
	private ArrayList<Album> albums;
	private User user;
	private ObservableList<Album> albumList;
	private int index;
	private UserList userList; 
	private ArrayList<User> users;
	private ListView<Album> list_album;

	@FXML private TextField txt_newName;
	@FXML private Button btn_confirm;
	
	/**
	 * @author Yelin
	 * @param selectedAlbum
	 * @param selectedUser
	 */
	public void setAlbum(Album selectedAlbum, User selectedUser) {
		
		this.album = selectedAlbum;
		this.user = selectedUser;
		albums = selectedUser.getAlbumlist();
	}
	
	@FXML
	private void confirm(ActionEvent event) throws IOException, ClassNotFoundException{
		String newName = txt_newName.getText();
		
		if (newName==null ||newName.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID RENAME ALBUM");
			alert.setContentText("Album name is required!");
			alert.showAndWait();
			return;
		}
		
		for(int i = 0; i < albums.size(); i++){
			 if(albums.isEmpty()) {
				 break;
			 }else if(albums.get(i).getAlbumName().equals(newName)){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR MESSAGE");
				alert.setHeaderText("Album name is already exists!");
				alert.setContentText("Album name is already exists! please add different album name.");
				alert.showAndWait();
				return;	
			 }
			 
		 }
		
		Album edit = user.getAlbumlist().get(index);
		edit.setAlbumName(newName);

		albumList.set(index, edit);
		
		try {
			UserList.write(userList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Stage createStage = (Stage)btn_confirm.getScene().getWindow();
		createStage.close();
		
		
		list_album.refresh();
	}
	
	/**
	 * @author Yelin
	 * @param editIndex
	 * @param obsalbumList
	 * @param album_list
	 * @param userList
	 */
	public void setObs(int editIndex, ObservableList<Album> obsalbumList, ListView<Album> album_list, UserList userList) {
		this.albumList = obsalbumList;
		this.index = editIndex;
		this.list_album = album_list;
		this.userList = userList;
		
	}


}
