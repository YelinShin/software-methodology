package com.example.photoalbum51;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Album implements Serializable {
    private static final long serialVersionUID = 51L;
    private String albumName;
    private int photocount=0;
    private Photo lastestPhoto;
	private Photo headPhoto;
	private ArrayList<Photo> photoList;
	private Album tmp;


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

    public ArrayList<Photo> getPhotolist(){
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

    }

    /**
     *
     * @param deletePos
     */
    public void deletePhoto(int deletePos) {
        photoList.remove(deletePos);
        photocount--;


    }

    /**
     *
     * @param p
     */
    public void deletePhoto2(Photo p){
        photoList.remove(p);
        photocount--;

    }




}
