package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Album implements Serializable{
	
	
	private String albumName;
	private Photo lastestPhoto;
	private Photo headPhoto;
	private List<Photo> photoList;
	private int photocount=0;
	
	/**
	 * 
	 * @param albumName
	 */
	
	public Album (String albumName) {
		this.albumName = albumName;
		photoList = new ArrayList<Photo>();
		photocount = 0;
		lastestPhoto = null;
		headPhoto = null;
	}
	/**
	 * 
	 * @return
	 */
	public String getAlbumName() {
		return this.albumName;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCount() {
		return this.photocount;
	}
	/**
	 * 
	 * @param albumName
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	/**
	 * 
	 * @return
	 */
	
	public List<Photo> getPhotolist(){
		return this.photoList;
	}
	/**
	 * 
	 * @param Pos
	 * @return
	 */
	public Photo getPhoto(int Pos) {
		return photoList.get(Pos);
	}
	
	/**
	 * 
	 * @param photo
	 * @return
	 */
	public int getPhotoPos(Photo photo) {
		for(int i=0; i<photoList.size(); i++) {
			if(photoList.get(i).equals(photo)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		if(photocount == 0) {
			headPhoto = photo;
		}
		photocount++;
		photoList.add(photo);
		lastestPhoto = photo;
		setPhotoDate();
	}
	
	/**
	 * 
	 * @param deletePos
	 */
	public void deletePhoto(int deletePos) {
		photoList.remove(deletePos);
		photocount--;
		setPhotoDate();
		
	}
	
	/**
	 * 
	 * @param p
	 */
	public void deletePhoto2(Photo p){
		photoList.remove(p);
		photocount--;
		setPhotoDate();
	}
	
	//maybe need to identify 1st photo and last photo
	/**
	 * 
	 * @return
	 */
	public String getDateHeadPhoto() {
		setPhotoDate();
		if(headPhoto == null) {
			return "empty list";
		}else {
			return headPhoto.getDateString();
		}
		
	}
	
	/**
	 * @author Claudia
	 */
	public void setPhotoDate(){
		lastestPhoto = photoList.get(0);
		headPhoto = photoList.get(0);
		
		for(int i=0; i< photoList.size();i ++){
			if(photoList.get(i).getCalendar().before(headPhoto.getCalendar())){
				headPhoto = photoList.get(i);
			}
		}
		for(int j=0; j< photoList.size();j ++){
			if(photoList.get(j).getCalendar().after(lastestPhoto.getCalendar())){
				lastestPhoto = photoList.get(j);
			}
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDateLastestPhoto() {
		setPhotoDate();
		if(lastestPhoto == null) {
			return "empty list";
		}else {
			return lastestPhoto.getDateString();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDateRange()
	{
		return getDateHeadPhoto() + " - " + getDateLastestPhoto();
	}
}
