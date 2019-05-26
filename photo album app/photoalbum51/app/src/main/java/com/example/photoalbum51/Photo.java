package com.example.photoalbum51;


import java.io.Serializable;
import java.util.ArrayList;



public class Photo implements Serializable {
    private static final long serialVersionUID = 51L;
    private String caption;
    private ArrayList<Tag> tagList;
    private Album album;
    private int pos;
    private BitmapPhotos photo;


    /**
     *

     * @param albumName

     */

    public Photo(BitmapPhotos photo,String caption, Album albumName, int pos) {
        super();
        this.caption = caption;
        tagList = new ArrayList<Tag>();
        this.pos = pos;
        this.photo = photo;
        this.album = albumName;

    }
    public int getPos(){return this.pos;}

    public void setTagList (ArrayList<Tag> tagList){
        this.tagList = tagList;
    }



    public String getCaption() {
        return this.caption;
    }


    /**
     *
     * @return
     */
    public ArrayList<Tag> getTagList(){
        return this.tagList;
    }


    public BitmapPhotos getPhoto(){
        return photo;
    }

}
