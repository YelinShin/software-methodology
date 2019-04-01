package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable{
	private String albumName;
	private Photo lastestPhoto;
	private Photo headPhoto;
	private List<Photo> photoList;
	
	public Album (String albumName) {
		this.albumName = albumName;
		photoList = new ArrayList<Photo>();
		lastestPhoto = null;
		headPhoto = null;
	}
	
	public String getAlbumName() {
		return this.albumName;
	}
	
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public List<Photo> getPhotolist(){
		return this.photoList;
	}
	
	public Photo getPhoto(int Pos) {
		return photoList.get(Pos);
	}
	
	public int getPhotoPos(Photo photo) {
		for(int i=0; i<photoList.size(); i++) {
			if(photoList.get(i).equals(photo)) {
				return i;
			}
		}
		return -1;
	}
	
	public void addPhoto(Photo photo) {
		photoList.add(photo);
	}
	
	public void deletePhoto(int deletePos) {
		photoList.remove(deletePos);
	}
	
	//maybe need to identify 1st photo and last photo
	
	public String getDateHeadPhoto() {
		if(headPhoto == null) {
			return "empty list";
		}else {
			return headPhoto.getDateString();
		}
	}
	
	public String getDateLastestPhoto() {
		if(lastestPhoto == null) {
			return "empty list";
		}else {
			return lastestPhoto.getDateString();
		}
	}
	
	public String getDateRange()
	{
		return getDateHeadPhoto() + " - " + getDateLastestPhoto();
	}
}
