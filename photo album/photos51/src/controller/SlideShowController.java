package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class SlideShowController {
	private Stage mainstage;
	private User user;
	private Album currentAlbum;
	private List<Photo> photos;
	private int index;
	private Photo currPhoto;
	private ImageView currImage;
	
	@FXML private Button btn_prevPhoto,btn_nextPhoto, btn_done;
	@FXML private TilePane tile_photo;
	
	/**
	 * @author Yelin
	 * @param stage
	 * @param currentUser
	 * @param album
	 */
	public void setStage(Stage stage, User currentUser,Album album) {
		this.mainstage=stage;
		this.user = currentUser;
		this.currentAlbum = album;
		
		index = 0;
		photos = currentAlbum.getPhotolist();
		currPhoto = photos.get(index);
		currImage = getImageView(currPhoto.getPhoto());
		tile_photo.setAlignment(Pos.CENTER);
		tile_photo.getChildren().add(currImage);
	}
	
	/**
	 * @author Claudia
	 * @param file
	 */
	public ImageView getImageView(final File file) {
		ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(file), 250, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(300);
        }catch(IOException e) {
        		e.printStackTrace();
        }
            
        return imageView;
	}
	
	
	@FXML
	public void prevPhoto() throws IOException, ClassNotFoundException{
		if(index ==0) {
			index = photos.size()-1;
			currPhoto = photos.get(index);
			currImage = getImageView(currPhoto.getPhoto());
			tile_photo.setAlignment(Pos.CENTER);
			tile_photo.getChildren().clear();
			tile_photo.getChildren().add(currImage);
		} else {
			index--;
			currPhoto = photos.get(index);
			currImage = getImageView(currPhoto.getPhoto());
			tile_photo.setAlignment(Pos.CENTER);
			tile_photo.getChildren().clear();
			tile_photo.getChildren().add(currImage);
		}
	}
	
	
	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	public void nextPhoto() throws IOException, ClassNotFoundException{
		if(index ==photos.size()-1) {
			index = 0;
			currPhoto = photos.get(index);
			currImage = getImageView(currPhoto.getPhoto());
			tile_photo.setAlignment(Pos.CENTER);
			tile_photo.getChildren().clear();
			tile_photo.getChildren().add(currImage);
		} else {
			index++;
			currPhoto = photos.get(index);
			currImage = getImageView(currPhoto.getPhoto());
			tile_photo.setAlignment(Pos.CENTER);
			tile_photo.getChildren().clear();
			tile_photo.getChildren().add(currImage);
		}
	}
	
	@FXML
	public void done(){
		mainstage.close();
	}

}
