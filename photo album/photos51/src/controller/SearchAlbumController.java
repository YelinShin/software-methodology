package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.UserList;

public class SearchAlbumController {
	
	private Album newAlbum;
	private User owner;
	private UserList userList; 
	private ListView<Album> list_album; 
	private ObservableList<Album> obsList; 
	private ObservableList<Photo> obsPhotoList;
	private List<Photo> photoList;
	
	@FXML
	private TextField txt_newAlbumName; 
	
	@FXML
	private Button btn_create;
	@FXML private ScrollPane scroll;
	@FXML private TilePane TilePane;
	
	/**
	 * @author Yelin
	 * @param searched_album
	 * @param selectedUser
	 */
	public void setAlbum(Album searched_album, User selectedUser) {
		this.newAlbum = searched_album;
		this.owner = selectedUser;
		
		//add preview of photos into tile here
		
	}
	
	/**
	 * @author Yelin
	 * @param obsAlbumList
	 * @param album_list
	 * @param userList
	 */
	
	public void setObs(ObservableList<Album> obsAlbumList, ListView<Album> album_list, UserList userList) {
		this.list_album = album_list;
		this.userList = userList; 
		this.obsList = obsAlbumList;
		try{
			photoList= newAlbum.getPhotolist();
			obsPhotoList = FXCollections.observableArrayList(photoList);
		}catch (Exception e){
			e.printStackTrace();
		}
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		scroll.setFitToWidth(false);
		TilePane.setPadding(new Insets(15, 15, 15, 15));
		TilePane.setHgap(15);
		TilePane.setVgap(15);
		TilePane.setTileAlignment(Pos.TOP_LEFT);
		TilePane.setPrefColumns(2);
		
		for(Photo x: photoList){
			ImageView imgView; 
			imgView = makeImgView(x.getPhoto());
			Label labelImg = makeCaption(imgView, x.getCaption(),x.getPhoto());
			TilePane.getChildren().addAll(labelImg);
		}
		
		
		
		scroll.setContent(TilePane);
	}
	
	/**
	 * @author Claudia
	 * @param imgFile
	 */
	
	private ImageView makeImgView (final File imgFile){
		ImageView imgView = null;
		try{
			final Image image = new Image (new FileInputStream(imgFile), 100, 0, true, true);
			imgView = new ImageView(image);
			imgView.setFitWidth(100);
		}catch(IOException e){
			e.printStackTrace();
		}
		return imgView; 
	}
	/**
	 * @author Claudia
	 * @param imgView
	 * @param capt
	 * @param file
	 */
	private Label makeCaption (ImageView imgView, String capt, File file){
		DropShadow shadow = new DropShadow(20,Color.DARKVIOLET);
		Label labelImg = new Label(capt); 
		labelImg.setPadding(new Insets(0, 0, 5, 0));
		labelImg.setGraphic(imgView);
		labelImg.setWrapText(true);
		labelImg.setContentDisplay(ContentDisplay.TOP);
		labelImg.setGraphicTextGap(15);
		
		return labelImg; 
	}
	
	@FXML
	public void createAlbum(ActionEvent event) throws IOException, ClassNotFoundException {
		String newalbumName = txt_newAlbumName.getText();
		
		if(newalbumName==null || newalbumName.isEmpty()) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID ADDING NEW ALBUM");
			alert.setContentText("Album name cannot be null");
			alert.showAndWait();
			return;
		}else if(owner.checkDupAlbum(newalbumName)) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID ADDING NEW ALBUM");
			alert.setContentText("Album name already exists, please use another name");
			alert.showAndWait();
			return;
		}
		
		newAlbum.setAlbumName(newalbumName);
		owner.addAlbum(newAlbum);
		obsList.add(newAlbum);
		
		try{
			UserList.write(userList);
		}catch (IOException e){
			e.printStackTrace();
		}
		
		txt_newAlbumName.setText("");
		
		Stage createStage = (Stage)btn_create.getScene().getWindow();
		createStage.close();
		
		
		list_album.refresh();
		
	}

}
