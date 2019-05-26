package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
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
import model.Photo;
import model.Tag;
import model.User;
import model.UserList;

public class EditCaptionController {
	
	private Stage mainstage;
	private User user;
	private Photo selectedPhoto;
	private UserList userList; 
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	private ArrayList<String> tagtypes = new ArrayList<String>();
	private String selectedType;
	private ObservableList<String> TypeList;
	private ListView<Tag> parent_listView_tags;
	private Album currentAlbum;
	private Stage parentStage;
	@FXML private Button btn_confirm;
	@FXML private TextField txt_caption;
	
	
	/**
	 * @author Yelin
	 * @param userList
	 * @param currentstage
	 */
	public void setObs(UserList userList, Stage currentstage) {
		this.userList = userList;  
		this.parentStage=currentstage;
		
	}
	/**
	 * @author Yelin
	 * @param stage
	 * @param currentUser
	 * @param selectedPhoto
	 * @param album
	 */

	public void setStage(Stage stage, User currentUser, Photo selectedPhoto, Album album) {
		this.mainstage=stage;
		this.user=currentUser;
		this.selectedPhoto=selectedPhoto;
		this.currentAlbum=album;
		
	}
	
	@FXML
	public void confirm() throws IOException, ClassNotFoundException{
		String newCaption = txt_caption.getText().trim();
		
		if(newCaption == null||newCaption.equals("")) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID CAPTION");
			alert.setContentText("Need to put proper caption.");
			alert.showAndWait();
			txt_caption.setText(null);
			return;
		}
		
		selectedPhoto.setCaption(newCaption);
		
		try {
			UserList.write(userList); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Stage stage = (Stage)btn_confirm.getScene().getWindow();
		stage.close();
		
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserAlbumContent.fxml"));
	    Parent root1 = (Parent) fxmlLoader.load();
	    
	    parentStage.setTitle("Album name: "+ currentAlbum.getAlbumName());
	    parentStage.setScene(new Scene(root1));
	    parentStage.setResizable(false);
	    AlbumContentController albumcontentcontroller = fxmlLoader.getController();
	    albumcontentcontroller.setAlbum(currentAlbum, user);
	    albumcontentcontroller.setObs(userList);
	    albumcontentcontroller.setStage(parentStage);
	    parentStage.show();
	}
}
