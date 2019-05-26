package controller;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;

import model.User;
import model.UserList;
import model.Album;
import model.Photo;
import model.Tag;


public class UserAlbumController {
	private UserList userList; 
	private ArrayList<User> users;
	private String username;
	
	public static Stage userAlbum_stage;
	private List<Album> albums;
	private User selectedUser;
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	
	private ObservableList<Album> albumList;
	
	@FXML private TextField txt_late,txt_tagValue,txt_tagType, txt_tagValue2, txt_tagType2;
	@FXML private TextField txt_early;
	@FXML private TextField txt_numPhoto;
	@FXML private Button btn_add, btn_OR, btn_AND, btn_Single;
	@FXML private Button btn_delete;
	@FXML private Button btn_rename;
	@FXML private Text txt_early2,txt_late2,txt_numPhoto2;
	@FXML private ListView<Album> list_album;
	@FXML private Button btn_content;
	@FXML private Button btn_logout,btn_search;
	@FXML private DatePicker date_begin,date_end;
	
	
	/**
	 * @author Claudia
	 * @param username
	 */
	public void set_user(String username) {
		this.username = username;
		
		try {
			userList = UserList.read();
			users = userList.getUserList();
		}catch(Exception err) {
			return;
		}
		
		for(int i = 0; i < users.size(); i++){
			 if(users.isEmpty()) {
				 break;
			 }else {
				 if((users.get(i).getUsername()).equals(username)) {
					 selectedUser = users.get(i);
				 }
			 } 
		}
	}
	/**
	 * @author Yelin
	 * @param stockStage
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void setStage(Stage stockStage) throws ClassNotFoundException, IOException {
		userAlbum_stage = stockStage;
		
		
		albums = selectedUser.getAlbumlist();
		
		albumList = FXCollections.observableArrayList(albums);
		list_album.setItems(albumList);
		list_album.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>() {
		    public ListCell<Album> call(ListView<Album> param) {
		        final ListCell<Album> cell = new ListCell<Album>() {
		            @Override
		            public void updateItem(Album album, boolean empty) {
		            		
		                super.updateItem(album, empty);
		                if (album == null) {
		                		setText(null);
		                }else {
		                	setText(album.getAlbumName());
		                }
		            }
		        };
		        return cell;
		    }
		});
		
		
		
		if(!albumList.isEmpty()) {
			list_album.getSelectionModel().select(0);
			selectItem();
		}
		
		list_album
		 .getSelectionModel()
		 .selectedItemProperty()
		 .addListener((obs, oldVal, newVal) -> selectItem());
		
		
	}
	

	private void selectItem() {
		if(list_album.getSelectionModel().getSelectedItem()!=null) {
			Album selected = list_album.getSelectionModel().getSelectedItem();
			if(selected.getPhotolist().size() == 0){
	    		txt_early2.setText("empty list");
	    		txt_late2.setText("empty list");
	    		txt_numPhoto2.setText(Integer.toString(selected.getCount()));
	    		return;
	    	}
			txt_late2.setText(selected.getDateLastestPhoto());
			txt_early2.setText(selected.getDateHeadPhoto());
			txt_numPhoto2.setText(Integer.toString(selected.getCount()));
		}else {
			return;
		}
	}
	
	@FXML
	public void addAlbum() throws IOException{
		int editIndex = list_album.getSelectionModel().getSelectedIndex();
		try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addNewAlbum.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Create album");
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    CreateAlbumController createAlbumcontroller = fxmlLoader.getController();
		    createAlbumcontroller.setStage(stage, selectedUser);
		    createAlbumcontroller.setObs(editIndex, albumList, list_album, userList);
		    userAlbum_stage = (Stage)btn_add.getScene().getWindow();
		    stage.show();
		}catch (Exception e) {
			System.out.println("error in add album window");
		}
	}
	
	@FXML 
	public void deleteAlbum() throws IOException {
		Album albumSelected = list_album.getSelectionModel().getSelectedItem();
		if(albumSelected == null){
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID  DELETE ALBUM");
			alert.setContentText("No album selected. Please pick one to delete");
			alert.showAndWait();
			return;
		}
		
		int deleteIndex = list_album.getSelectionModel().getSelectedIndex();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Album");
		alert.setHeaderText("Delete Album");
		alert.setContentText("Are you sure you want to remove this album?");
		Optional<ButtonType> result = alert.showAndWait();
		
		try {
			if (result.get() == ButtonType.OK){
				selectedUser.getAlbumlist().remove(deleteIndex);
				UserList.write(userList);
				//list_album.refresh();
				setStage(userAlbum_stage);
			}
		} catch(Exception e) {
			System.out.println("error in delete album window");
		}
		
	}
	
	@FXML
	public void renameAlbum() throws IOException, ClassNotFoundException{
		
		int editIndex = list_album.getSelectionModel().getSelectedIndex(); 
		if(editIndex <0){
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID  REALBUM");
			alert.setContentText("No album selected. Please pick one to rename");
			alert.showAndWait();
			return;
		}
		try {
			
			Album selectedAlbum = list_album.getSelectionModel().getSelectedItem();
			
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserRenameAlbum.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Rename album");
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    RenameAlbumController renameAlbumcontroller = fxmlLoader.getController();
		    renameAlbumcontroller.setAlbum(selectedAlbum,selectedUser);
		    renameAlbumcontroller.setObs(editIndex, albumList,list_album, userList);
		    userAlbum_stage = (Stage)btn_rename.getScene().getWindow();
		    stage.show();
		}catch (Exception e) {
			System.out.println("error in rename album window");
		}
	}
	
	@FXML
	public void logOut() throws IOException{
		Stage primaryStage = (Stage) btn_logout.getScene().getWindow();
		Parent root = FXMLLoader.load((getClass().getResource("/view/Login.fxml")));
		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	@FXML
	public void openAlbum() throws IOException, ClassNotFoundException{
		try {
			Album selected = list_album.getSelectionModel().getSelectedItem();
			if(selected == null){
				Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle("ERROR MESSAGE");
				alert.setHeaderText("INVALID OPEN ALBUM");
				alert.setContentText("No album selected. Please pick one to open");
				alert.showAndWait();
				return;
			}
			
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserAlbumContent.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Album name: "+selected.getAlbumName());
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    AlbumContentController albumcontentcontroller = fxmlLoader.getController();
		    albumcontentcontroller.setAlbum(selected, selectedUser);
		    
		    albumcontentcontroller.setObs(userList);
		    albumcontentcontroller.setStage(stage);
		    userAlbum_stage = (Stage)btn_content.getScene().getWindow();
		    userAlbum_stage.close();
		    stage.show();
		}catch (Exception e) {
			System.out.println("error in open album window");
		}
	}
	
	
	@FXML
	public void search() throws IOException, ClassNotFoundException{
		//only time search
		boolean properRange = false;
		boolean tagSet = false;
		boolean tagSet2 = false;
		String tagType = txt_tagType.getText();
		String tagValue = txt_tagValue.getText();
		String tagType2 = txt_tagType2.getText();
		String tagValue2 = txt_tagValue2.getText();
		
		if(!tagType.equals("")&&!tagValue.equals("")) {
			tagSet = true;
		}
		if(!tagType2.equals("")&&!tagValue2.equals("")) {
			tagSet2 = true;
		}
		if(date_end.getValue()!=null && date_begin.getValue()!= null) {
			properRange=true;
		}
		
		if((tagSet==true || tagSet2==true) && properRange == true) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE AND TAG");
			alert.setContentText("Need to put proper date range or tag.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		} else if (properRange == false) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE");
			alert.setContentText("This is search by Date range, please put vaild date range.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		} else if (properRange == true && tagSet==false && tagSet2==false) {
			//only date search 
		} /*else if (properRange == false && tagSet == true && tagSet2 == false) {
			//only tag1 search
		} else if (properRange == false && tagSet == false && tagSet2 == true) {
			//only tag 2 search
		} else if (properRange == false && tagSet == true && tagSet2 == true){
			//search with both tag pairs
		}else if (properRange == false && tagSet == false && tagSet2 == false) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE AND TAG");
			alert.setContentText("Need to put proper date range or tag.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		}*/ else {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE AND TAG");
			alert.setContentText("Need to put proper date range.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		}
		
		
		Album searched_album = null;
		
		searched_album = search_album(properRange, tagSet, tagSet2,false);
		
		
	
		txt_tagType.setText("");
		txt_tagValue.setText("");
		txt_tagType2.setText("");
		txt_tagValue2.setText("");
		date_begin.setValue(null);
		date_end.setValue(null);
		
		if(properRange == true || tagSet == true || tagSet2==true) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/searchAlbum.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Rename album");
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    SearchAlbumController searchalbumcontroller = fxmlLoader.getController();
		    searchalbumcontroller.setAlbum(searched_album, selectedUser);
		    searchalbumcontroller.setObs(albumList, list_album, userList);
		    userAlbum_stage = (Stage)btn_search.getScene().getWindow();
		    stage.show();
		}

	}
	
	@FXML
	public void OrTag() throws IOException, ClassNotFoundException{
		boolean properRange = false;
		boolean tagSet = false;
		boolean tagSet2 = false;
		boolean isOr = false;
		String tagType = txt_tagType.getText();
		String tagValue = txt_tagValue.getText();
		String tagType2 = txt_tagType2.getText();
		String tagValue2 = txt_tagValue2.getText();
		
		if(!tagType.equals("")&&!tagValue.equals("")) {
			tagSet = true;
		}
		if(!tagType2.equals("")&&!tagValue2.equals("")) {
			tagSet2 = true;
		}
		if(date_end.getValue()!=null && date_begin.getValue()!= null) {
			properRange=true;
		}
		
		if((tagSet==true || tagSet2==true) && properRange == true) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE AND TAG");
			alert.setContentText("Need to put proper date range or tag.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		} else if (properRange == true) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE");
			alert.setContentText("This is search by OR tag search.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		} else if (properRange == false && tagSet == true && tagSet2 == true){
			//search with both tag pairs
			isOr = true;
		}/*else if (properRange == false && tagSet == false && tagSet2 == false) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE AND TAG");
			alert.setContentText("Need to put proper date range or tag.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		}*/ else {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DATE RANGE AND TAG");
			alert.setContentText("Need to put proper tag pair.");
			alert.showAndWait();
			date_begin.setValue(null);
			date_end.setValue(null);
			txt_tagType.setText("");
			txt_tagValue.setText("");
			txt_tagType2.setText("");
			txt_tagValue2.setText("");
			return;
		}
		
		
		Album searched_album = null;
		
		searched_album = search_album(properRange, tagSet, tagSet2, isOr);
		
		

		txt_tagType.setText("");
		txt_tagValue.setText("");
		txt_tagType2.setText("");
		txt_tagValue2.setText("");
		date_begin.setValue(null);
		date_end.setValue(null);
		
		if(properRange == true || tagSet == true || tagSet2==true) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/searchAlbum.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Rename album");
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    SearchAlbumController searchalbumcontroller = fxmlLoader.getController();
		    searchalbumcontroller.setAlbum(searched_album, selectedUser);
		    searchalbumcontroller.setObs(albumList, list_album, userList);
		    
		    userAlbum_stage = (Stage)btn_search.getScene().getWindow();
		    stage.show();
		}
	}
	
	/**
	 * @author Yelin
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void AndTag() throws IOException, ClassNotFoundException{
		//only time search
				boolean properRange = false;
				boolean tagSet = false;
				boolean tagSet2 = false;
				String tagType = txt_tagType.getText();
				String tagValue = txt_tagValue.getText();
				String tagType2 = txt_tagType2.getText();
				String tagValue2 = txt_tagValue2.getText();
				
				if(!tagType.equals("")&&!tagValue.equals("")) {
					tagSet = true;
				}
				if(!tagType2.equals("")&&!tagValue2.equals("")) {
					tagSet2 = true;
				}
				if(date_end.getValue()!=null && date_begin.getValue()!= null) {
					properRange=true;
				}
				
				if((tagSet==true || tagSet2==true) && properRange == true) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID DATE RANGE AND TAG");
					alert.setContentText("Need to put proper date range or tag.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				} else if (properRange == true) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID DATE RANGE");
					alert.setContentText("This is search by Date range, please put vaild date range.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				} else if (properRange == false && tagSet==true && tagSet2==true) {
					//only date search 
				} /*else if (properRange == false && tagSet == true && tagSet2 == false) {
					//only tag1 search
				} else if (properRange == false && tagSet == false && tagSet2 == true) {
					//only tag 2 search
				} else if (properRange == false && tagSet == true && tagSet2 == true){
					//search with both tag pairs
				}else if (properRange == false && tagSet == false && tagSet2 == false) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID DATE RANGE AND TAG");
					alert.setContentText("Need to put proper date range or tag.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				}*/ else {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID TAG");
					alert.setContentText("Need to put proper 2 tag Pairs.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				}
				
				
				Album searched_album = null;
				
				searched_album = search_album(properRange, tagSet, tagSet2,false);
				
				

				txt_tagType.setText("");
				txt_tagValue.setText("");
				txt_tagType2.setText("");
				txt_tagValue2.setText("");
				date_begin.setValue(null);
				date_end.setValue(null);
				
				if(properRange == true || tagSet == true || tagSet2==true) {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/searchAlbum.fxml"));
				    Parent root1 = (Parent) fxmlLoader.load();
				    Stage stage = new Stage();
				    stage.setTitle("Rename album");
				    stage.setScene(new Scene(root1));
				    stage.setResizable(false);
				    SearchAlbumController searchalbumcontroller = fxmlLoader.getController();
				    searchalbumcontroller.setAlbum(searched_album, selectedUser);
				    searchalbumcontroller.setObs(albumList, list_album, userList);
				    userAlbum_stage = (Stage)btn_search.getScene().getWindow();
				    stage.show();
				}

	}
	
	/**
	 * @author Yelin
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public void singleTag() throws IOException, ClassNotFoundException{
		//only time search
				boolean properRange = false;
				boolean tagSet = false;
				boolean tagSet2 = false;
				String tagType = txt_tagType.getText();
				String tagValue = txt_tagValue.getText();
				String tagType2 = txt_tagType2.getText();
				String tagValue2 = txt_tagValue2.getText();
				
				if(!tagType.equals("")&&!tagValue.equals("")) {
					tagSet = true;
				}
				if(!tagType2.equals("")&&!tagValue2.equals("")) {
					tagSet2 = true;
				}
				if(date_end.getValue()!=null && date_begin.getValue()!= null) {
					properRange=true;
				}
				
				
				if((tagType.equals("")&& !tagValue.equals(""))||(tagType2.equals("")&& !tagValue2.equals(""))
						||(tagValue.equals("")&& !tagType.equals(""))|| (tagValue2.equals("")&& !tagType2.equals(""))) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID TAG");
					alert.setContentText("Need to put one proper tag pair.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				}
				
				if(tagSet==false && tagSet == false) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID TAG");
					alert.setContentText("Need to put one proper tag pair.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				}else if((tagSet==true || tagSet2==true) && properRange == true) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID DATE RANGE AND TAG");
					alert.setContentText("Need to put proper date range or tag.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				} else if (properRange == true) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID DATE RANGE");
					alert.setContentText("This is search by Date range, please put vaild date range.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				} else if(properRange == false && tagSet==true && tagSet2 == true) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID TAG PAIR");
					alert.setContentText("This is search by single tag. ");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				}else if (properRange == false && (tagSet==true || tagSet2==true)) {
					//only date search 
				} /*else if (properRange == false && tagSet == true && tagSet2 == false) {
					//only tag1 search
				} else if (properRange == false && tagSet == false && tagSet2 == true) {
					//only tag 2 search
				} else if (properRange == false && tagSet == true && tagSet2 == true){
					//search with both tag pairs
				}else if (properRange == false && tagSet == false && tagSet2 == false) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID DATE RANGE AND TAG");
					alert.setContentText("Need to put proper date range or tag.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				}*/ else {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID TAG");
					alert.setContentText("Need to put proper tag pair.");
					alert.showAndWait();
					date_begin.setValue(null);
					date_end.setValue(null);
					txt_tagType.setText("");
					txt_tagValue.setText("");
					txt_tagType2.setText("");
					txt_tagValue2.setText("");
					return;
				}
				
				
				Album searched_album = null;
				
				searched_album = search_album(properRange, tagSet, tagSet2,false);
				
				
			
				txt_tagType.setText("");
				txt_tagValue.setText("");
				txt_tagType2.setText("");
				txt_tagValue2.setText("");
				date_begin.setValue(null);
				date_end.setValue(null);
				
				if(properRange == true || tagSet == true || tagSet2==true) {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/searchAlbum.fxml"));
				    Parent root1 = (Parent) fxmlLoader.load();
				    Stage stage = new Stage();
				    stage.setTitle("Rename album");
				    stage.setScene(new Scene(root1));
				    stage.setResizable(false);
				    SearchAlbumController searchalbumcontroller = fxmlLoader.getController();
				    searchalbumcontroller.setAlbum(searched_album, selectedUser);
				    searchalbumcontroller.setObs(albumList, list_album, userList);
				    userAlbum_stage = (Stage)btn_search.getScene().getWindow();
				    stage.show();
				}

	}
	
	/**
	 * @author Yelin
	 * @param properRange
	 * @param tagSet
	 * @param tagSet2
	 * @param isOr
	 * @return
	 */
	
	private Album search_album(boolean properRange, boolean tagSet, boolean tagSet2, boolean isOr) {
		Album tmpAlbum = new Album("temp".trim());
		Photo tmpPhoto;
		boolean add = true;
		boolean tag_add=true;
		
		if(isOr) {
			
			 if (properRange == false && tagSet == true && tagSet2 == true){
				//search with both tag pairs
				
				for(Album album: albums) {
					for(Photo photo: album.getPhotolist()) {
						boolean match1 = false;
						boolean match2 = false;
						for (int i =0; i<photo.getTagList().size();i++) {
							if(photo.getTagList().get(i).getType().equals(txt_tagType.getText().trim())
									&& photo.getTagList().get(i).getValue().equals(txt_tagValue.getText().trim()) ) {
								match1 = true;
							}
							
							if(photo.getTagList().get(i).getType().equals(txt_tagType2.getText().trim())
								&& photo.getTagList().get(i).getValue().equals(txt_tagValue2.getText().trim())) {
									match2 =true;
									
							}
							
						}
						if (match1 == true || match2 == true) {
							tmpPhoto = photo;
							for(Photo x : tmpAlbum.getPhotolist()) {
								if(x.getPhoto().equals(tmpPhoto.getPhoto()) ){
									add = false;
								}	
							}if(add) {
								tmpAlbum.addPhoto(tmpPhoto);
							}
						}
						
					}
						
				}
			}
		}else if(properRange == true && tagSet==false && tagSet2==false) {
			//only date search
			for(Album album : albums) {
				for(Photo photo : album.getPhotolist()) {
					add = true;
					
					LocalDate ldate = photo.getCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
					if((ldate.isAfter(date_begin.getValue()) && ldate.isBefore(date_end.getValue()))|| ldate.equals(date_begin.getValue())|| ldate.equals(date_end.getValue())) {
						tmpPhoto = photo;
						for(Photo x : tmpAlbum.getPhotolist()) {
							if(x.getPhoto().equals(tmpPhoto.getPhoto()) ){
								add = false;
							}	
						}if(add) {
							tmpAlbum.addPhoto(tmpPhoto);
						}
					
					}else {
						continue;
					}
				}
			}
		} else if (properRange == false && tagSet == true && tagSet2 == false) {
			//only tag1 search
	
			for(Album album: albums) {
				for(Photo photo: album.getPhotolist()) {
					
					for (int i =0; i<photo.getTagList().size();i++) {
						if(photo.getTagList().get(i).getType().equals(txt_tagType.getText().trim())
								&& photo.getTagList().get(i).getValue().equals(txt_tagValue.getText().trim()) ) {
							tmpPhoto = photo;
							for(Photo x : tmpAlbum.getPhotolist()) {
								if(x.getPhoto().equals(tmpPhoto.getPhoto()) ){
									add = false;
								}	
							}if(add) {
								tmpAlbum.addPhoto(tmpPhoto);
							}
						}
					}
					
				}
			}
		} else if (properRange == false && tagSet == false && tagSet2 == true) {
			//only tag 2 search
			
			for(Album album: albums) {
				for(Photo photo: album.getPhotolist()) {
					
					for (int i =0; i<photo.getTagList().size();i++) {
						if(photo.getTagList().get(i).getType().equals(txt_tagType2.getText().trim())
								&& photo.getTagList().get(i).getValue().equals(txt_tagValue2.getText().trim()) ) {
							tmpPhoto = photo;
							for(Photo x : tmpAlbum.getPhotolist()) {
								if(x.getPhoto().equals(tmpPhoto.getPhoto()) ){
									add = false;
								}	
							}if(add) {
								tmpAlbum.addPhoto(tmpPhoto);
							}
						}
					}
					
				}
			}
		} else if (properRange == false && tagSet == true && tagSet2 == true){
			//search with both tag pairs
		
			
	
			
			for(Album album: albums) {
				for(Photo photo: album.getPhotolist()) {
					boolean match1 = false;
					boolean match2 = false;
					for (int i =0; i<photo.getTagList().size();i++) {
						if(photo.getTagList().get(i).getType().equals(txt_tagType.getText().trim())
								&& photo.getTagList().get(i).getValue().equals(txt_tagValue.getText().trim()) ) {
							match1 = true;
						}
						
						if(photo.getTagList().get(i).getType().equals(txt_tagType2.getText().trim())
							&& photo.getTagList().get(i).getValue().equals(txt_tagValue2.getText().trim())) {
								match2 =true;
								
						}
						
						if (match1 == true && match2 == true) {
							tmpPhoto = photo;
							for(Photo x : tmpAlbum.getPhotolist()) {
								if(x.getPhoto().equals(tmpPhoto.getPhoto()) ){
									add = false;
								}	
							}if(add) {
								tmpAlbum.addPhoto(tmpPhoto);
							}
						}
					}
					
				}
					
			}
		}
			
		return tmpAlbum;
	}

	
	

}
