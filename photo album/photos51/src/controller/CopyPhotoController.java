package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.User;
import model.UserList;
import model.Photo;

public class CopyPhotoController {
	@FXML private Button btn_Submit;
	@FXML private TextField txt_copytoAlbum;
	
	
	private Stage stage;
	private UserList userList;
	private User user;
	private Photo photo;
	private ArrayList<Album> albums;
	private Album sourceAlbum;
	private Stage prev;

	/**
	 * @author Claudia
	 * @param stage
	 * @param prevStage
	 * @param userList
	 * @param currentUser
	 * @param copyPhoto
	 * @param srcAlbum
	 */
	public void setStage(Stage stage, Stage prevStage, UserList userList, User currentUser, Photo copyPhoto, Album srcAlbum) {
		
		this.stage = stage;
		this.userList = userList;
		this.user = currentUser;
		this.photo = copyPhoto;
		this.prev = prevStage;
		this.sourceAlbum = srcAlbum;
		albums = user.getAlbumlist();
	}
	
	
	@FXML
	public void submit() throws IOException, ClassNotFoundException{
		String copytoAlbum = txt_copytoAlbum.getText();
		
		boolean existing = false; 
		
		for(int i = 0; i<albums.size();i++){
			if(albums.get(i).getAlbumName().equals(copytoAlbum)){
				existing = true; 
				Album tmp = albums.get(i);
				for(int j = 0; j<tmp.getPhotolist().size();j++){
					if(tmp.getPhotolist().get(j).getPhoto().getAbsolutePath().equals(photo.getPhoto().getAbsolutePath())){
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR MESSAGE");
						alert.setHeaderText("Duplicate Photo Selected!");
						alert.setContentText("Photo selected already exists the target album");
						txt_copytoAlbum.setText(null);
						alert.showAndWait();
						return;
					}
				}
				//successfully copy
				albums.get(i).addPhoto(photo);
				txt_copytoAlbum.setText(null);
				
				try {
					UserList.write(userList); 
				}catch (Exception e) {
					e.printStackTrace();
				}
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserAlbumContent.fxml"));
			    Parent root1 = (Parent) fxmlLoader.load();
			    Stage albumContentStage = new Stage();
			    albumContentStage.setTitle("Album name: "+sourceAlbum.getAlbumName());
			    albumContentStage.setScene(new Scene(root1));
			    albumContentStage.setResizable(false);
			    AlbumContentController albumcontentcontroller = fxmlLoader.getController();
			    albumcontentcontroller.setAlbum(sourceAlbum, user);
			    
			    albumcontentcontroller.setObs(userList);
			    albumcontentcontroller.setStage(stage);
			    stage = (Stage)btn_Submit.getScene().getWindow();
			    stage.close();
			    prev.close();
			    albumContentStage.show();
			    return;
			}
		}
		if(!existing){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("Invalid Album Name!");
			alert.setContentText("Album does not exist, please input one of your album names correctly");
			alert.showAndWait();
				return;
		}
	}
		
	
}
