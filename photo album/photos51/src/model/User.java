package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	private String username;
	private List<Album> albumlist;
	
	public User(String username) {
		this.username = username;
		albumlist = new ArrayList<Album>();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void addAlbum(Album albumName) {
		albumlist.add(albumName);
	}
	
	public void deleteAlbum(Album currentAlbum) {
		albumlist.remove(currentAlbum);
	}
	
	public Album getAlbum(String albumName) {
		for(Album x : albumlist) {
			if(x.getAlbumName().equals(albumName)) {
				return x;
			}
		}
		return null;
	}
	
	public int getAlbumPos (Album currentAlbum) {
		for(int i=0; i<albumlist.size(); i++) {
			if(albumlist.get(i).getAlbumName().equals(currentAlbum.getAlbumName())) {
				return i;
			}
		}
		return -1;
	}
	
	public void addPhoto (Photo currentPhoto, int albumPos ) {
		albumlist.get(albumPos).addPhoto(currentPhoto);
	}
	
	public boolean checkDupAlbum (String albumName) {
		for (Album x: albumlist) {
			if (x.getAlbumName().toLowerCase().equals(albumName.trim().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
