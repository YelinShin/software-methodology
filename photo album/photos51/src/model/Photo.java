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
import java.text.ParseException;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;


public class Photo implements Serializable {
	
	private Calendar calendar;
	private String caption;
	private List<Tag> tagList;
	private ArrayList<Album> albumList;
	private File file;
	
	
	
	/**
	 * 
	 * @param caption
	 * @param albumName
	 * @param file
	 * @param date
	 */
	public Photo(String caption, Album albumName, File file, String date) {
		this.caption = caption;
		this.albumList = new ArrayList<Album>();
		albumList.add(albumName);
		
		
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat ("MM-dd-yyyy HH:mm:ss");
			Date photoDate = formatDate.parse(date);
			calendar = Calendar.getInstance();
			calendar.setTime(photoDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.file = file;
		this.tagList = new ArrayList<Tag>();
	}
	
	/**
	 * 
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCaption() {
		return this.caption;
	}
	
	/**
	 * 
	 * @param calendar
	 */
	public void setCalendar (Calendar calendar) {
		this.calendar = calendar;
	}
	
	/**
	 * 
	 * @return
	 */
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	/**
	 * 
	 * @param albumname
	 */
	public void addAlbum(Album albumname) {
		albumList.add(albumname);
	}
	
	public void removeAlbum(Album albumname) {
		albumList.remove(albumname);
	}
	
	/**
	 * 
	 * @param albumname
	 * @return
	 */
	public boolean isContained(Album albumname) {
		return albumList.contains(albumname);
	}
	
	/**
	 * 
	 * @param file
	 */
	public void setPhoto (File file) {
		this.file = file;
	}
	
	/**
	 * 
	 * @return
	 */
	public File getPhoto() {
		return this.file;
	}
	
	/**
	 * 
	 * @param tag
	 */
	public void addTag(Tag tag) {
		tagList.add(tag);
	}
	
	/**
	 * 
	 * @param tag
	 */
	public void removeTag(Tag tag) {
		tagList.remove(tag);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Tag> getTagList(){
		return this.tagList;
	}
	/**
	 * 
	 * @return
	 */
	public String getDateString(){
		String[] dateString = calendar.getTime().toString().split("\\s+");
		return dateString[0] + " " + dateString[1] + " " + dateString[2] + "," + dateString[3];
	}
	
	/**
	 * 
	 * @param tag
	 * @return
	 */
	public boolean dupTag (Tag tag) {
		for (Tag x: this.tagList) {
			if(x.equals(tag)) {
				return true;
			}
		}
		return false;
	}
}
