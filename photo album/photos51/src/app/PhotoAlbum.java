package app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import model.UserList;
import model.User;
import model.Album;
import model.Photo;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.EOFException;

import app.PhotoAlbum;
import controller.LoginController;

public class PhotoAlbum extends Application {
	
	private ArrayList<User> users = new ArrayList<User>();

	
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException{
		
		UserList userList = new UserList();
		try{
			userList = UserList.read();
		}catch(EOFException e) {
			
		}
		
		String userDir = System.getProperty("user.dir");
		String path = userDir + "/stock/";
		User admin = new User("admin");
		User stock = new User("stock");
		Album stockAlbum = new Album("stock"); 
		
		File stockFile = new File(path);
		File[] stockFiles = stockFile.listFiles();
		
		for (final File x: stockFiles){
			Long modDate = x.lastModified();
			String date = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(modDate);
			Photo photo = new Photo(x.getName(), stockAlbum, x, date);
			stockAlbum.addPhoto(photo);
		}
		
		stock.addAlbum(stockAlbum);
		
		
		if (userList.getUserList().size() == 0){
			try{
				userList.addUser(admin);
				userList.addUser(stock);
				UserList.write(userList);
				userList = UserList.read();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/login.fxml"));			
				Parent root= (Parent) loader.load();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Photo Album");
				primaryStage.setResizable(false);  
				primaryStage.show();
			}catch (IOException e){
				e.printStackTrace();
			}
		}else {
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/login.fxml"));			
				Parent root= (Parent) loader.load();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Photo Album");
				primaryStage.setResizable(false);  
				primaryStage.show();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
