/*Claudia Pan - cp728,
 *Yelin Shin - ys521*/
package controller;

import app.songInfo;

import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.*;


public class LoginController<T> {
	Stage primaryStage; 
	private songInfo currentSong;
	@FXML         
	 ListView<songInfo> listView_songs; 
	
	@FXML
    private	Label lbl_name , lbl_artist, lbl_album, lbl_year, lbl_details;
	
	@FXML
    private Button btn_Add, btn_Delete, btn_Edit, btn_Confirm, btn_Cancel, btn_EDITCONFIRM; 
	
	@FXML
	private TextField txt_Name  , txt_Artist, txt_Album, txt_Year;
	
	private ObservableList<songInfo> songs_ObsList; 
	
	
	public void start(Stage mainStage) throws IOException { 
		String currentDir = System.getProperty("user.dir");

		File songFile = new File (currentDir + "/songlist.txt");
		if(!songFile.exists()) {
			songFile.createNewFile();
		}
		
		songs_ObsList = FXCollections.observableArrayList(parseFile (currentDir +"songlist.txt")); 

		listView_songs.setItems(songs_ObsList); 
		listView_songs.setCellFactory(new Callback<ListView<songInfo>, ListCell<songInfo>>() {
			@Override
			public ListCell<songInfo> call(ListView<songInfo> param) {
		        ListCell<songInfo> tmpCell = new ListCell<songInfo>() {
		            @Override
		            public void updateItem(songInfo tmp, boolean empty) {
		            		
		                super.updateItem(tmp, empty);
		                if (tmp != null) {
		                	setText(tmp.getSong_Name() +" - "+ tmp.getSong_Artist());
		                }else {
		                	setText(null);
		                }
		            }
		        };
		        return tmpCell;
		    }
		});
		
		// select the first item
		if (songs_ObsList != null) {
			listView_songs.getSelectionModel().select(0);
			if(listView_songs.getSelectionModel().getSelectedIndex() == 0) {
				showAllDetails(true);
			}
			showAllDetails(true);
			showSongDetails();
		}else if(songs_ObsList.size() == '0') {
			showAllDetails(false);
		}
		// set listener for the items
		listView_songs
			    .getSelectionModel()
			    .selectedIndexProperty()
			    .addListener((obs, oldVal, newVal)-> showSongDetails ());
	}
	@FXML
	private void showSongDetails() {   
			showAllDetails(false);
	      if(listView_songs.getSelectionModel().getSelectedItem() ==null) {
	    	  return;
	      }else {
	    	  currentSong = listView_songs.getSelectionModel().getSelectedItem();
	    	  txt_Name.setText(currentSong.getSong_Name());
	    	  txt_Artist.setText(currentSong.getSong_Artist());
	    	  txt_Album.setText(currentSong.getSong_Album());
	    	  txt_Year.setText(currentSong.getSong_Year());
	    	  showAllDetails(true); 
	      }
	}
	

	private ArrayList <songInfo> parseFile (String file_path) throws FileNotFoundException{
		String currentDir = System.getProperty("user.dir");
		ArrayList <songInfo> a = new ArrayList<songInfo>();
		File songFile = new File (currentDir + "/songlist.txt");
		//open file path, parse with buffer
		//add songs to arrary list
		
		Scanner buff_list = null;
		String line = null;
		try {
			buff_list = new Scanner(songFile);
		}catch (FileNotFoundException error) {
			error.printStackTrace();
		}	
		
		while (buff_list.hasNextLine()) {
			line = buff_list.nextLine();
			String [] delim = line.split("/");
			songInfo tmp = new songInfo(delim[0], delim[1],delim[2],delim[3]);	
			a.add(tmp);
		}
		buff_list.close();
		sortSongList(a);
		listView_songs.refresh();
		return a;
	}

	private ArrayList<songInfo> sortSongList (ArrayList <songInfo> a){
		Collections.sort(a, new Comparator<songInfo>(){
			@Override
			public int compare (songInfo song1, songInfo song2) {
				int nameVal = song1.getSong_Name().compareTo(song2.getSong_Name());
				if(nameVal == 0) {
					int artistVal = song1.getSong_Artist().compareTo(song2.getSong_Artist());
					return artistVal; 
				}else {
					return nameVal;
				}
			}
		});
		
		return a;
	}
	
	@FXML 
	private void clickAdd (ActionEvent e) {
		txt_Album.setText(null);
		txt_Artist.setText(null);
		txt_Name.setText(null);
		txt_Year.setText(null);
		txt_Album.setDisable(false);
		txt_Artist.setDisable(false);
		txt_Name.setDisable(false);
		txt_Year.setDisable(false);
		
		listView_songs.setDisable(true);
		btn_Delete.setDisable(true);
		btn_Edit.setDisable(true);
		btn_Cancel.setVisible(true);
		btn_Confirm.setVisible(true);
		
		
		showAllDetails(true);
		return;
	}
	@FXML
	private void clickDelete (ActionEvent e) {
		
		txt_Album.setDisable(true);
		txt_Artist.setDisable(true);
		txt_Name.setDisable(true);
		txt_Year.setDisable(true);
		listView_songs.setDisable(false);
		btn_Delete.setDisable(false);
		btn_Edit.setDisable(false);
		btn_Add.setDisable(false);
		btn_Cancel.setVisible(false);
		btn_Confirm.setVisible(false);
		
		if(listView_songs.getSelectionModel().getSelectedItem()== null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID DELETING SONG");
			alert.setContentText("No deleting Allowed- no songs in library!");
			alert.showAndWait();
			return;
		}
		
		int index = listView_songs.getSelectionModel().getSelectedIndex();
		songs_ObsList.remove(index);
		listView_songs.refresh();
		if((index+1) == songs_ObsList.size()) {
			listView_songs.getSelectionModel().select(index--);
			listView_songs.refresh();
			showSongDetails();
			writeOutputFile();
		}else if(songs_ObsList.size()=='0'){
			showAllDetails(false);
		}else  {
			listView_songs.getSelectionModel().select(index);
			listView_songs.refresh();
			showSongDetails();
			writeOutputFile();
		}
		return;
	}
	
	@FXML
	private void confirmAdd (ActionEvent e) {
		String song = txt_Name.getText();
		String artist = txt_Artist.getText();
		
		if (song==null ||song.isEmpty()|| artist==null || artist.isEmpty()){
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID ADDING NEW SONG");
			alert.setContentText("Song name and artist are required!");
			alert.showAndWait();
			
			if(song == null) {
				txt_Name.setPromptText("Please enter a song name");
			}else if(artist == null) {
				txt_Artist.setPromptText("Please enter an Artist");
			}
			return;
		}else {
			
			songInfo tmp = new songInfo (txt_Name.getText(),txt_Artist.getText(), txt_Album.getText(), txt_Year.getText());
			
			if (isDuplicate (tmp) == false) {
				int insertIndex = sortObs(tmp);
				
				songs_ObsList.add(insertIndex, tmp);
				listView_songs.refresh();
				listView_songs.getSelectionModel().select(tmp);
				txt_Album.setDisable(true);
				txt_Artist.setDisable(true);
				txt_Name.setDisable(true);
				txt_Year.setDisable(true);
				
				btn_Delete.setDisable(false);
				btn_Edit.setDisable(false);
				btn_Cancel.setVisible(false);
				btn_Confirm.setVisible(false);
				
				
				listView_songs.refresh(); 
				writeOutputFile();
				showAllDetails(true);
				listView_songs.setDisable(false);
				return;
			}else {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR MESSAGE");
				alert.setHeaderText("INVALID ADDING NEW SONG");
				alert.setContentText("No Duplicate Song Allowed- song already exists");
				alert.showAndWait();
				return;
			}
		}
	}
	@FXML
	private void confirmEdit (ActionEvent e) {
		songInfo tmp = listView_songs.getSelectionModel().getSelectedItem();
		int i = listView_songs.getSelectionModel().getSelectedIndex();
	
		if(listView_songs.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR MESSAGE");
			alert.setHeaderText("INVALID EDITING SONG");
			alert.setContentText("Empty library, no songs to edit!");
			alert.showAndWait();
			return;
		}else { //duplicates cannot be saved
			for(int x = 0; x < songs_ObsList.size(); x++) {
				if (x == i) {
					continue;
				}
				if(songs_ObsList.get(x).getSong_Name().equals(txt_Name.getText()) && songs_ObsList.get(x).getSong_Artist().equals(txt_Artist.getText())) {
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR MESSAGE");
					alert.setHeaderText("INVALID EDITING SONG");
					alert.setContentText("Edited song is a duplicate of an existing song!");
					alert.showAndWait();
					return;
				}
			}
			
			tmp.setSong_Name(txt_Name.getText());
			tmp.setSong_Artist(txt_Artist.getText());
			tmp.setSong_Album(txt_Album.getText());
			tmp.setSong_Year(txt_Year.getText());
			listView_songs.getSelectionModel().select(tmp);
			listView_songs.refresh();

			txt_Album.setDisable(true);
			txt_Artist.setDisable(true);
			txt_Name.setDisable(true);
			txt_Year.setDisable(true);
			
			btn_Delete.setDisable(false);
			btn_Edit.setDisable(false);
			btn_Add.setDisable(false);
			btn_Cancel.setVisible(false);
			btn_Confirm.setVisible(false);
			btn_EDITCONFIRM.setVisible(false);
				
			writeOutputFile();
			return;

		}

	}
	
	@FXML
	private void editSong (ActionEvent e) {
		
		songInfo tmp = listView_songs.getSelectionModel().getSelectedItem();
		if (tmp!= null) {
			txt_Album.setDisable(false);
			txt_Artist.setDisable(false);
			txt_Name.setDisable(false);
			txt_Year.setDisable(false);
			btn_Delete.setDisable(true);
			btn_Add.setDisable(true);
			btn_Cancel.setVisible(true);
			btn_Confirm.setVisible(false);
			btn_EDITCONFIRM.setVisible (true);
			listView_songs.setDisable(false);
			txt_Name.setText(tmp.getSong_Name());
			txt_Artist.setText(tmp.getSong_Artist());
			txt_Album.setText(tmp.getSong_Album());
			txt_Year.setText(tmp.getSong_Year());
		}
		
	}
	@FXML
	private void cancelAction (ActionEvent e) {
		
		txt_Album.setDisable(true);
		txt_Artist.setDisable(true);
		txt_Name.setDisable(true);
		txt_Year.setDisable(true);
		
		btn_Delete.setDisable(false);
		btn_Add.setDisable(false);
		btn_Edit.setDisable(false);
		
		btn_Cancel.setVisible(false);
		btn_Confirm.setVisible(false);
		btn_EDITCONFIRM.setVisible (false);
		listView_songs.setDisable(false);
		
		if (listView_songs.getSelectionModel().getSelectedItem() ==null) {
			showAllDetails(false);
			return;
		}
		showSongDetails();
		
	}
	
	public boolean isDuplicate (songInfo tmp) {
		for(int i = 0; i < songs_ObsList.size(); i++) {
			
			if(songs_ObsList.get(i).getSong_Name().equals(tmp.getSong_Name()) && songs_ObsList.get(i).getSong_Artist().equals(tmp.getSong_Artist())) {
				return true;
			}
		}
		return false; 
	}
	public boolean isDuplicateEdit (songInfo tmp, int index) {
		for(int i = 0; i < songs_ObsList.size(); i++) {
			if (index == i) {
				continue;
			}
			if((songs_ObsList.get(i).getSong_Name().equals(tmp.getSong_Name()) )&& songs_ObsList.get(i).getSong_Artist().equals(tmp.getSong_Artist())) {
				return true;
			}
		}
		return false; 
	}
	
	private void writeOutputFile() {
		String currentDir = System.getProperty("user.dir");

		FileWriter writer = null;
		BufferedWriter buffer = null; 
		try {
			writer = new FileWriter(currentDir + "/songlist.txt");
			buffer = new BufferedWriter(writer);
			for(int i = 0; i< songs_ObsList.size();i ++) {
				buffer.write(songs_ObsList.get(i).getSong_Name() + "/" + songs_ObsList.get(i).getSong_Artist() + "/" + songs_ObsList.get(i).getSong_Album() + "/" + songs_ObsList.get(i).getSong_Year() + "/");
				buffer.newLine();
			}
		}catch (IOException error) {
			error.printStackTrace();
		}finally {
			try {
				if(buffer != null) {
					buffer.close();
				}
			}catch (IOException error) {
				error.printStackTrace();
			}
		}
	}
	
	private int sortObs (songInfo tmp) {
		int i;
		for(i = 0; i < songs_ObsList.size(); i++) {
			if(tmp.getSong_Name().toLowerCase().compareTo(songs_ObsList.get(i).getSong_Name().toLowerCase())< 0) {
				return i;
			}else if(tmp.getSong_Name().toLowerCase().compareTo(songs_ObsList.get(i).getSong_Name().toLowerCase())>0) {
				continue;
			}else {
				if(tmp.getSong_Artist().toLowerCase().compareTo(songs_ObsList.get(i).getSong_Artist().toLowerCase())< 0) {
					return i;
				}else if(tmp.getSong_Artist().toLowerCase().compareTo(songs_ObsList.get(i).getSong_Artist().toLowerCase())>0) {
					continue;
				}
			}
			
		}
		return i;
	}
	
	private void showAllDetails(boolean b) {
		lbl_name.setVisible(b);
		lbl_artist.setVisible(b);
		lbl_album.setVisible(b);
		lbl_year.setVisible(b);
		lbl_details.setVisible(b);
		
		txt_Name.setVisible(b);
		txt_Album.setVisible(b);
		txt_Artist.setVisible(b);
		txt_Year.setVisible(b);
	
	}
	
}