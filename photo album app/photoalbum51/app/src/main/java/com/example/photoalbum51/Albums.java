package com.example.photoalbum51;

import java.io.EOFException;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.content.Context;



public class Albums implements Serializable {

    private static final long serialVersionUID = 51L;
    private ArrayList<Album> albums;
    private ArrayList<String> albumNames;
    private ArrayList<Photo> allPhotos;
    private ArrayList<Photo> searchPhotos;
    //direct file path system.dir()



    public Albums(){
        albums = new ArrayList<Album>();
        allPhotos = new ArrayList<Photo>();

    }

    public void removeAlbum(int index){
        albums.remove(index);
    }

    public int addAlbum(Album album){
        if(!albums.contains(album)){
            albums.add(album);
            return 1;
        }else{
            return -1;
        }
    }

    public void setSearchPhotos(ArrayList<Photo> searchPhotos){
        this.searchPhotos = searchPhotos;
    }

    public ArrayList<Photo> getSearchPhotos(){
        return this.searchPhotos;
    }

    public ArrayList<Photo> getAllPhotos(){
        allPhotos = new ArrayList<Photo>();
        for(int i = 0; i< albums.size(); i++){
            for(int j = 0; j<albums.get(i).getPhotolist().size(); j++){
                allPhotos.add(albums.get(i).getPhotolist().get(j));

            }
        }
        return allPhotos;
    }



    public ArrayList<Album> getAlbums(){
        return this.albums;
    }

    public ArrayList<String> getAlbumNames(){
        albumNames = new ArrayList<String>();
        for(int i=0; i< albums.size(); i++){
            albumNames.add(albums.get(i).getAlbumName());
        }
        return albumNames;
    }

    public void write(Context c)  {

        FileOutputStream f = null;

        try {
            f = c.openFileOutput("albumlist.dat", Context.MODE_PRIVATE);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this);
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Albums read(Context c) {

        FileInputStream fi = null;
        String filepath = c.getFilesDir()+"/" + "albumlist.dat";


        File file = new File(filepath);
        if(!file.exists()){
            return null;
        }
        Albums u = null;
        FileOutputStream fo = null;

        ObjectInputStream o ;
        try {
            FileInputStream f = new FileInputStream(filepath);
            o = new ObjectInputStream(f);
            u = (Albums)o.readObject();
            o.close();
            f.close();
        }catch (IOException e ){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return u;
        /*try {
            fo = c.openFileOutput("albumlist.dat", Context.MODE_PRIVATE);

            fo.close();
            fi = c.openFileInput("albumlist.dat");
            int i = fi.read();
            if(i==-1){
                System.out.println("empty file");
                return null;
            }
            ObjectInputStream o = new ObjectInputStream(fi);

            u = (Albums) o.readObject();
            o.close();
            fi.close();
            return u;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return u;*/
    }


}
