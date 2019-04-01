package model;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import javax.imageio.ImageIO;
import javax.swing.text.html.HTML.Tag;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class Photo implements Serializable {
	private Calendar calendar;
	private String caption;
	private List<Tag> tagList;
	private ArrayList<Album> albumList;
	private File file;
	
	public Photo(String caption, Album albumName, File file) {
		this.caption = caption;
		this.albumList = new ArrayList<Album>();
		albumList.add(albumName);
		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND,0);
		this.file = file;
		this.tagList = new ArrayList<Tag>();
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public void setCalendar (Calendar calendar) {
		this.calendar = calendar;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	public void addAlbum(Album albumname) {
		albumList.add(albumname);
	}
	
	public void removeAlbum(Album albumname) {
		albumList.remove(albumname);
	}
	
	public boolean isContained(Album albumname) {
		return albumList.contains(albumname);
	}
	
	public void setPhoto (File file) {
		this.file = file;
	}
	
	public File getPhoto() {
		return this.file;
	}
	
	public void addTag(Tag tag) {
		tagList.add(tag);
	}
	
	public void removeTag(Tag tag) {
		tagList.remove(tag);
	}
	
	public List<Tag> getTagList(){
		return this.tagList;
	}
	
	public String getDateString(){
		String[] dateString = calendar.getTime().toString().split("\\s+");
		return dateString[0] + " " + dateString[1] + " " + dateString[2] + "," + dateString[3];
	}
	
	public boolean dupTag (Tag tag) {
		for (Tag x: this.tagList) {
			if(x.equals(tag)) {
				return true;
			}
		}
		return false;
	}
}
