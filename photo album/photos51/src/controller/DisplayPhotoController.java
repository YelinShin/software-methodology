package controller;

import javafx.stage.*;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;

import java.lang.Object;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.image.*;


public class DisplayPhotoController {
	@FXML
	private TilePane tile_singlePhoto; 
	
	@FXML
	private Button btn_Done; 
	
	@FXML 
	private Text txt_Caption, txt_Date; 
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private ListView<Tag> listView_tags;
	
	private Stage currStage;
	private File file;
	private Photo selectedPhoto;
	private ImageView imgView;
	private Album album;
	private ObservableList<Tag>obsTagList;
	
	/**
	 * @author Claudia
	 * @param stage
	 * @param imgView
	 * @param file
	 * @param album
	 * @throws IOException
	 */
	public void setStage(Stage stage, ImageView imgView, File file, Album album) throws IOException{
		this.currStage= stage;
		this.imgView = imgView;
		this.file = file; 
		this.album = album;
		
		for(int i = 0; i <album.getPhotolist().size();i++){
			if(album.getPhotolist().get(i).getPhoto().equals(file)){
				selectedPhoto = album.getPhotolist().get(i);
				break;
			}
		}
		txt_Caption.setText(selectedPhoto.getCaption());
		txt_Date.setText(selectedPhoto.getDateString());
		
		
		obsTagList = FXCollections.observableArrayList(selectedPhoto.getTagList());
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
		
		tile_singlePhoto.setPadding(new Insets(15, 15, 15, 15));
		tile_singlePhoto.setHgap(15);
		tile_singlePhoto.setVgap(15);
		tile_singlePhoto.setTileAlignment(Pos.TOP_LEFT);
		
		
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		scrollPane.setFitToWidth(false);
		
		
		try{
			final Image image = new Image (new FileInputStream(file), 200, 0, true, true);
			imgView = new ImageView(image);
			imgView.setFitWidth(200);
			imgView.setFitHeight(200);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		//txt_Caption = 
		tile_singlePhoto.getChildren().add(imgView);
		
		
		
	}
	
	@FXML
	public void close(){
		currStage.close();
	}
	
	

}
