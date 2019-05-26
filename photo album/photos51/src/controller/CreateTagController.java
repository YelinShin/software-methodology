package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.UserList;

public class CreateTagController {
	@FXML private Button btn_submit;
	@FXML private TextField tag_value,txt_newType;
	@FXML private ListView<String> list_tagType;
	
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
		
		//getting all the tag list in selectedUser's albums
		for (int i = 0; i<user.getAlbumlist().size(); i++) { // all user's album list
			for(int j=0; j<user.getAlbumlist().get(i).getPhotolist().size(); j++) { // all user's photo lists
				List<Tag> tmpTags = user.getAlbumlist().get(i).getPhotolist().get(j).getTagList();
				for (int k=0; k<tmpTags.size();k++) {
					Tag tmpTag = tmpTags.get(k);
					if(tagtypes.isEmpty()) {
						tagtypes.add(tmpTag.getType());
					}else {
						if(!tagtypes.contains(tmpTag.getType()) && !tmpTag.getType().equals(null)) {
							tagtypes.add(tmpTag.getType());
						}
					}
				}
			}
		}
		
		TypeList = FXCollections.observableArrayList(tagtypes);
		list_tagType.setItems(TypeList);
		
	}
	/**
	 * @author Yelin
	 * @param userList
	 * @param currentstage
	 */
	public void setObs(UserList userList, Stage currentstage) {
		this.userList = userList;  
		this.parentStage=currentstage;
	}
	
	@FXML
	public void submit() throws IOException, ClassNotFoundException{

			String selectedType = list_tagType.getSelectionModel().getSelectedItem();
			String TagValue = tag_value.getText();
			String newtagType = txt_newType.getText();
		
			
			if(selectedType==null) {
				if(newtagType.equals("")) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID TAG TYPE");
					alert.setContentText("Need to put proper tag.");
					alert.showAndWait();
					list_tagType.getSelectionModel().clearSelection();
					txt_newType.setText(null);
					tag_value.setText(null);
					return;
				}else {
					if(tagtypes.contains(selectedType)) {
						Alert alert = new Alert (AlertType.ERROR);
						alert.setTitle("ERROR MESSAGE");
						alert.setHeaderText("INVALID TAG TYPE");
						alert.setContentText("Need to put new tag type.");
						alert.showAndWait();
						list_tagType.getSelectionModel().clearSelection();
						txt_newType.setText(null);
						tag_value.setText(null);
						return;
					} else if (TagValue.equals("")){
						Alert alert = new Alert (AlertType.ERROR);
						alert.setTitle("ERROR MESSAGE");
						alert.setHeaderText("INVALID TAG TYPE");
						alert.setContentText("Need to put new tag value.");
						alert.showAndWait();
						list_tagType.getSelectionModel().clearSelection();
						txt_newType.setText(null);
						tag_value.setText(null);
						return;
					} else {
						Tag newtag = new Tag(newtagType, TagValue);
						boolean duptag = selectedPhoto.dupTag(newtag);
						if(duptag==false) {
							selectedPhoto.addTag(newtag);
						}else {
							Alert alert = new Alert (AlertType.ERROR);
							alert.setTitle("ERROR MESSAGE");
							alert.setHeaderText("INVALID TAG PAIR");
							alert.setContentText("This tag pair is already exist.");
							alert.showAndWait();
							list_tagType.getSelectionModel().clearSelection();
							txt_newType.setText(null);
							tag_value.setText(null);
							return;
						}
					}
				}
			}else {
				if(newtagType.equals("")||newtagType==null){
					if (TagValue.equals("")){
						Alert alert = new Alert (AlertType.ERROR);
						alert.setTitle("ERROR MESSAGE");
						alert.setHeaderText("INVALID TAG TYPE");
						alert.setContentText("Need to put new tag value.");
						alert.showAndWait();
						list_tagType.getSelectionModel().clearSelection();
						txt_newType.setText(null);
						tag_value.setText(null);
						return;
					} else {
						Tag newtag = new Tag(selectedType, TagValue);
						boolean duptag = selectedPhoto.dupTag(newtag);
						if(duptag==false) {
							selectedPhoto.addTag(newtag);
						}else {
							Alert alert = new Alert (AlertType.ERROR);
							alert.setTitle("ERROR MESSAGE");
							alert.setHeaderText("INVALID TAG PAIR");
							alert.setContentText("This tag pair is already exist.");
							alert.showAndWait();
							list_tagType.getSelectionModel().clearSelection();
							txt_newType.setText(null);
							tag_value.setText(null);
							return;
						}
					}
				} else {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID TAG TYPE");
					alert.setContentText("Need to select old tag type or put new tag type.");
					alert.showAndWait();
					list_tagType.getSelectionModel().clearSelection();
					txt_newType.setText(null);
					tag_value.setText(null);
					return;
				}
			}
			

			try {
				UserList.write(userList); 
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			Stage stage = (Stage)btn_submit.getScene().getWindow();
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
