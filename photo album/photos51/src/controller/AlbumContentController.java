package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.UserList;


public class AlbumContentController {
	
	@FXML
	private Button btn_addPhoto,btn_slidShow, btn_addTag, btn_editCap, btn_back, btn_delete, btn_move, btn_copy, btn_deleteTag, btn_addCap, btn_search, btn_createSearch, btn_logout;

	@FXML 
	private ListView listView; 
	
	@FXML
	private TextField txt_Tag, txt_tagSearch;
	@FXML
	private Text txt_Caption, txt_Date;
	@FXML
	private DatePicker date_begin, date_end; 
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML 
	private TilePane tile_photoCollection; 
	@FXML
	private ListView listView_photo;
	
	@FXML private ListView<Tag> listView_tags;
	
	private Stage currentStage;
	private User currentUser; 
	private Album album;
	private Photo selectedPhoto; 
	private UserList userList; 
	private ObservableList<Photo> obsPhotoList;
	private List<Photo> photoList;
	private List<Tag> taglist;
	private ObservableList<Tag>obsTagList;
	
	
	/**
	 * @author Claudia
	 * @param selectedAlbum
	 * @param selectedUser
	 */
	public void setAlbum(Album selectedAlbum, User selectedUser) {
		
		this.album = selectedAlbum;
		this.currentUser = selectedUser;
	}
	/**
	 * @author Claudia
	 * @param userList
	 */
	public void setObs( UserList userList) {
		this.userList = userList; 
	}
	
	/**
	 * @author Claudia
	 * @param stage
	 */
	public void setStage(Stage stage) {
		
		try {
			
			this.currentStage= stage;
			photoList = album.getPhotolist();
			obsPhotoList = FXCollections.observableArrayList(photoList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		listView_photo.setItems(null);
		listView_photo.setItems(obsPhotoList);
		
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		scrollPane.setFitToWidth(false);
		tile_photoCollection.setPadding(new Insets(15, 15, 15, 15));
		tile_photoCollection.setHgap(15);
		tile_photoCollection.setVgap(15);
		tile_photoCollection.setTileAlignment(Pos.TOP_LEFT);
		tile_photoCollection.setPrefColumns(2);
		
		
		for(Photo x: photoList){
			ImageView imgView; 
			imgView = makeImgView(x.getPhoto());
			Label labelImg = makeCaption(imgView, x.getCaption(),x.getPhoto());
			tile_photoCollection.getChildren().addAll(labelImg);
		}
		scrollPane.setContent(tile_photoCollection);
		
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
		
		labelImg.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent event){
				ImageView imgView; 
				if(event.getButton().equals(MouseButton.PRIMARY)){
					if(event.getClickCount() == 2){
						try{
							imgView = new ImageView();
							Image image = new Image(new FileInputStream(file), 500, 0, true, true);
							
							imgView.setImage(image);
							imgView.setFitWidth(500);
							imgView.setSmooth(true);
							imgView.setCache(true);
							imgView.setPreserveRatio(true);
							FXMLLoader viewLoader = new FXMLLoader (getClass().getResource("/view/ShowPhoto.fxml"));
							Parent root = (Parent)viewLoader.load();
						
							Stage stage = new Stage();
							stage.setTitle("Display Photo");
							stage.setScene(new Scene(root));
							stage.setResizable(false);						
							
							DisplayPhotoController dispControl = viewLoader.getController();
							
							dispControl.setStage(stage, imgView, file,album);
							currentStage = (Stage)tile_photoCollection.getScene().getWindow();
							//currentStage.close();
							stage.show();
						}catch(IOException e){
							e.printStackTrace();
						}
					}else if(event.getClickCount() == 1){
						int pos = -1;
						Label tmp = null;
		
						for(int i = 0;i<album.getPhotolist().size();i++){
							tmp = (Label)tile_photoCollection.getChildren().get(i);
							tmp.setEffect(null);
						}
						
						pos = -1;
						for (int i = 0; i<album.getPhotolist().size(); i++){
							if(file.getAbsolutePath().equals(album.getPhotolist().get(i).getPhoto().getAbsolutePath())){
								pos = i;
							}
						}
						selectedPhoto = album.getPhotolist().get(pos);
						txt_Caption.setText(selectedPhoto.getCaption());
						txt_Date.setText(selectedPhoto.getDateString());
						
						
						taglist = selectedPhoto.getTagList();
						obsTagList = FXCollections.observableArrayList(taglist);
						listView_tags.setItems(obsTagList);
						listView_tags.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {
						    public ListCell<Tag> call(ListView<Tag> param) {
						        final ListCell<Tag> cell = new ListCell<Tag>() {
						            @Override
						            public void updateItem(Tag tag, boolean empty) {
						            		
						                super.updateItem(tag, empty);
						                if (tag == null) {
						                		setText(null);
						                }else {
						                		setText(tag.toString());
						                }
						            }
						        };
						        return cell;
						    }
						});
						
						
						
						
						
						tmp = (Label)tile_photoCollection.getChildren().get(pos);
						if(tmp.getEffect()==null){
							tmp.setEffect(shadow);
						}else {
							tmp.setEffect(null);
						}
						tile_photoCollection.getChildren().set(pos, tmp);

					}
				}
			}
		});
		
		
		return labelImg; 
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
	public void addPhoto() throws IOException, ClassNotFoundException{
	
		FileChooser fc = new FileChooser();
		fc.setTitle("Select a Photo");
		FileChooser.ExtensionFilter filter1 = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter filter2 = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		
		fc.getExtensionFilters().addAll(filter1,filter2);
		
		File file = fc.showOpenDialog(currentStage);
		if(file == null) {
			return;
		}else {
			for(Photo x: album.getPhotolist()) {
				if(x.getPhoto().equals(file)) {
					//duplicate photo
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("Photo already exists in album!");
					alert.setContentText("Photo already exists! Please add another photo.");
					alert.showAndWait();
					return;
				}
			}
		}
		
		Long modDate = file.lastModified();
		String date = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(modDate);
		
		Photo x = new Photo(file.getName(), album,file, date); 
	
		
		album.addPhoto(x);
		obsPhotoList.add(x);
		
		
		listView_photo.setItems(null);
		listView_photo.setItems(FXCollections.observableArrayList(album.getPhotolist()));
		
		ImageView imgView;
		imgView = makeImgView(file);
		Label lbledImg = makeCaption(imgView,file.getName(), file);
		
		try {
			UserList.write(userList); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		tile_photoCollection.getChildren().addAll(lbledImg);
		
		
	}
	
	@FXML 
	private void deletePhoto() throws IOException, ClassNotFoundException{
		Label tmp; 
		int pos = -1; 
		Photo tmpPhoto;
		
		for(int i=0; i< tile_photoCollection.getChildren().size();i++){
			tmp = (Label)tile_photoCollection.getChildren().get(i);
			if(tmp.getEffect()!= null){
				pos = i;
			}
		}
		if(pos == -1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("No Photo Selected!");
			alert.setContentText("Please select a photo to delete");
			alert.showAndWait();
			return;
		}else {
			tmpPhoto = album.getPhotolist().get(pos);
			//album.deletePhoto(pos);
			album.deletePhoto2(tmpPhoto);
			obsPhotoList.remove(tmpPhoto);
			tile_photoCollection.getChildren().remove(pos);
			
			listView_photo.setItems(null);
			listView_photo.setItems(FXCollections.observableArrayList(album.getPhotolist()));
			
			try {
				UserList.write(userList); 
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			if(tile_photoCollection.getChildren().size() == 0){
				txt_Caption.setText(null);
				txt_Date.setText(null);
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserAlbumContent.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = (Stage)btn_delete.getScene().getWindow();
		    stage.setTitle("Album name: "+album.getAlbumName());
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    AlbumContentController albumcontentcontroller = fxmlLoader.getController();
		    albumcontentcontroller.setAlbum(album, currentUser);
		    
		    albumcontentcontroller.setObs(userList);
		    albumcontentcontroller.setStage(stage);
		    
		    stage.show();
			
		}
		
	}
	@FXML
	public void Back() throws IOException, ClassNotFoundException{
		FXMLLoader stockLoad = new FXMLLoader(getClass().getResource("/view/useralbum.fxml"));
		Parent root = (Parent)stockLoad.load();
		Scene stockScene = new Scene(root);
		Stage stockStage = (Stage) btn_back.getScene().getWindow();
		stockStage.setScene(stockScene);
		stockStage.setResizable(false);
		stockStage.setTitle(currentUser.getUsername() + "'s Album");
		UserAlbumController useralbumcontroller = stockLoad.getController();
		useralbumcontroller.set_user(currentUser.getUsername());
		useralbumcontroller.setStage(stockStage);
		stockStage.show();
	}
	
	
	@FXML
	public void movePhoto() throws IOException, ClassNotFoundException{
		int pos = -1;
		Photo movePhoto;
		Label tmp; 
		
		
		for(int i=0; i< tile_photoCollection.getChildren().size();i++){
			tmp = (Label)tile_photoCollection.getChildren().get(i);
			if(tmp.getEffect()!= null){
				pos = i;
			}
		}
		if(pos == -1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("No Photo Selected!");
			alert.setContentText("Please select a photo to move");
			alert.showAndWait();
			return;
		}else {
			//movePhoto = album.getPhotolist().get(pos);
			movePhoto = selectedPhoto;
			try{
			    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserMovePhoto.fxml"));
			    Parent root1 = (Parent) fxmlLoader.load();
			    Stage stage = new Stage();
			    stage.setTitle("Move Photo");
			    stage.setScene(new Scene(root1));
			    stage.setResizable(false);
			    MovePhotoController movephotocntrl = fxmlLoader.getController();
			    currentStage = (Stage)btn_move.getScene().getWindow();
			    movephotocntrl.setStage(stage,currentStage, userList,currentUser, movePhoto, album);
				stage.show();
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("error trying to move photo");
			}
		}
	}
	
	@FXML
	public void copyPhoto() throws IOException, ClassNotFoundException{
		//copy a photo from one ablum to another 
		int pos = -1;
		Photo copyPhoto;
		Label tmp; 
		
		for(int i=0; i< tile_photoCollection.getChildren().size();i++){
			tmp = (Label)tile_photoCollection.getChildren().get(i);
			if(tmp.getEffect()!= null){
				pos = i;
			}
		}
		if(pos == -1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("No Photo Selected!");
			alert.setContentText("Please select a photo to copy");
			alert.showAndWait();
			return;
		}else {
			//copyPhoto = album.getPhotolist().get(pos);
			copyPhoto = selectedPhoto;
			try{
			    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserCopyPhoto.fxml"));
			    Parent root1 = (Parent) fxmlLoader.load();
			    Stage stage = new Stage();
			    stage.setTitle("Copy Photo");
			    stage.setScene(new Scene(root1));
			    stage.setResizable(false);
			    CopyPhotoController copyphotocntrl = fxmlLoader.getController();
			    currentStage = (Stage)btn_copy.getScene().getWindow();
			    copyphotocntrl.setStage(stage,currentStage, userList,currentUser, copyPhoto, album);
				stage.show();
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("error trying to copy photo");
			}
		}
		
	}
	
	@FXML
	public void addTag() throws IOException, ClassNotFoundException{
		
				
		if (selectedPhoto == null) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("Invaild Adding Tag");
			alert.setContentText("Need to select photo before add tag.");
			alert.showAndWait();
			return;
		}
		try {
			Stage currentstage = (Stage)btn_addTag.getScene().getWindow();
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserAddTag.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Add Tag");
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    CreateTagController createtagcontroller = fxmlLoader.getController();
		    createtagcontroller.setObs(userList,currentstage);
		    createtagcontroller.setStage(stage, currentUser, selectedPhoto,album);
		    stage.show();
		}catch (Exception e) {
			System.out.println("error in add tag window");
		}
	}
	
	@FXML
	public void deleteTag() throws IOException, ClassNotFoundException{
		Tag selectedTag = listView_tags.getSelectionModel().getSelectedItem();
		if(selectedPhoto==null) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("Invaild Delete Tag");
			alert.setContentText("Need to select photo before delete tag.");
			alert.showAndWait();
			return;
		}else {
			if(selectedTag==null) {
				Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle("ERROR MESSAGE");
				alert.setHeaderText("Invaild Delete Tag");
				alert.setContentText("Need to select tag before delete tag.");
				alert.showAndWait();
				return;
			}
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Tag");
		alert.setHeaderText("Delete UsTager");
		alert.setContentText("Are you sure you want to remove this tag pair?");
		selectedPhoto.removeTag(selectedTag);
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserAlbumContent.fxml"));
	    Parent root1 = (Parent) fxmlLoader.load();
	    
	    currentStage.setTitle("Album name: "+ album.getAlbumName());
	    currentStage.setScene(new Scene(root1));
	    currentStage.setResizable(false);
	    AlbumContentController albumcontentcontroller = fxmlLoader.getController();
	    albumcontentcontroller.setAlbum(album, currentUser);
	    albumcontentcontroller.setObs(userList);
	    albumcontentcontroller.setStage(currentStage);
	    currentStage.show();
	}
	
	@FXML
	public void editCaption() throws IOException, ClassNotFoundException{
		if(tile_photoCollection.getChildren().size() == 0){
			selectedPhoto =null;
		}
	
		if (selectedPhoto == null) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("Invaild Adding Tag");
			alert.setContentText("Need to select photo before edit caption.");
			alert.showAndWait();
			return;
		}
		
		try {
			Stage currentstage = (Stage)btn_editCap.getScene().getWindow();
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserEditCaption.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage = new Stage();
		    stage.setTitle("Edit tag");
		    stage.setScene(new Scene(root1));
		    stage.setResizable(false);
		    EditCaptionController editCaptionController = fxmlLoader.getController();
		    editCaptionController.setObs(userList,currentstage);
		    editCaptionController.setStage(stage, currentUser, selectedPhoto,album);
		    stage.show();
		}catch (Exception e) {
			System.out.println("error in edit caption window");
		}
	}
	
	@FXML
	public void slidShow() throws IOException, ClassNotFoundException{
		if(album.getPhotolist().size()==0) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("Invaild Slide Show");
			alert.setContentText("Need to have at least one photo to do slide show.");
			alert.showAndWait();
			return;
		}else {
			try {
				Stage currentstage = (Stage)btn_slidShow.getScene().getWindow();
			    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserSlideShow.fxml"));
			    Parent root1 = (Parent) fxmlLoader.load();
			    Stage stage = new Stage();
			    stage.setTitle(album.getAlbumName() +" Slide show");
			    stage.setScene(new Scene(root1));
			    stage.setResizable(false);
			    SlideShowController slideshowcontroller = fxmlLoader.getController();
			    slideshowcontroller.setStage(stage, currentUser,album);
			    stage.show();
			}catch (Exception e) {
				System.out.println("error in add tag window");
			}
		}
	}
	
	
	

}
