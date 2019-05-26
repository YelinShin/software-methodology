package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	private String username;
	private ArrayList<Album> albumlist;
	
	/**
	 * 
	 * @param username
	 */
	public User(String username) {
		this.username = username;
		albumlist = new ArrayList<Album>();
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Album> getAlbumlist(){
		return (ArrayList<Album>) this.albumlist;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * 
	 * @param albumName
	 */
	public void addAlbum(Album albumName) {
		albumlist.add(albumName);
	}
	
	/**
	 * 
	 * @param currentAlbum
	 */
	public void deleteAlbum(Album currentAlbum) {
		albumlist.remove(currentAlbum);
	}
	
	/**
	 * 
	 * @param albumName
	 * @return
	 */
	public Album getAlbum(String albumName) {
		for(Album x : albumlist) {
			if(x.getAlbumName().equals(albumName)) {
				return x;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param currentAlbum
	 * @return
	 */
	public int getAlbumPos (Album currentAlbum) {
		for(int i=0; i<albumlist.size(); i++) {
			if(albumlist.get(i).getAlbumName().equals(currentAlbum.getAlbumName())) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 * @param currentPhoto
	 * @param albumPos
	 */
	public void addPhoto (Photo currentPhoto, int albumPos ) {
		albumlist.get(albumPos).addPhoto(currentPhoto);
	}
	
	/**
	 * 
	 * @param albumName
	 * @return
	 */
	public boolean checkDupAlbum (String albumName) {
		for (Album x: albumlist) {
			if (x.getAlbumName().toLowerCase().equals(albumName.trim().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
}
