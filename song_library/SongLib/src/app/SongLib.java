/*Claudia Pan - cp728,
 *Yelin Shin - ys521*/
package app;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import view.SongLibraryController;

import java.io.IOException;


public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SongLibrary.fxml"));			
			Parent root= (Parent) loader.load();
			SongLibraryController listController = loader.getController();
			listController.start(primaryStage);		
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Song Library");
			primaryStage.setResizable(false);  
			primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
