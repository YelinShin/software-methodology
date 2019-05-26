package com.example.photoalbum51;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {
    Button btn_person, btn_location, btn_or, btn_and;
    EditText txt_person, txt_location, txt_both_location,txt_both_person;
    ArrayList<Album> albumlist;
    Album searchedResultAlbum;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_display);

        btn_person = findViewById(R.id.btn_person);
        btn_location = findViewById(R.id.btn_location);
        btn_and = findViewById(R.id.btn_and);
        btn_or = findViewById(R.id.btn_or);
        txt_person = findViewById(R.id.txt_person);
        txt_location = findViewById(R.id.txt_tagValue);
        txt_both_location = findViewById(R.id.txt_both_location);
        txt_both_person = findViewById(R.id.txt_both_person);

        albumlist = MainActivity.albumcontroller.getAlbums();
        //System.out.println("albumlist size is: "+albumlist.size());


        View.OnClickListener searchByPersonListner = new View.OnClickListener(){
            @Override
            public void onClick (View view){
                if(txt_person.getText().toString().equals("")){
                    txt_person.setText("");
                    AlertDialog alert = new AlertDialog.Builder(SearchActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Need Person tag value to search.");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }else {
                    // search by Person tag value
                    Album tmpAlbum = new Album("tmp".trim());
                    Photo tmpPhoto;
                    boolean add = true;

                    for(Album album: albumlist) {
                        for(Photo photo: album.getPhotolist()) {
                            for (int i =0; i<photo.getTagList().size();i++) {
                                if(photo.getTagList().get(i).getType().equals("Person")
                                && photo.getTagList().get(i).getValue().toLowerCase().contains(txt_person.getText().toString().toLowerCase())) {
                                    tmpPhoto = photo;
                                    for(Photo x : tmpAlbum.getPhotolist()) {
                                        if(x.getCaption().equals(tmpPhoto.getCaption()) ){
                                            add = false;
                                        }
                                    }
                                    if(add) {
                                        tmpAlbum.addPhoto(tmpPhoto);
                                    }
                                }
                            }
                        }
                    }
                    txt_person.setText("");
                    searchedResultAlbum = tmpAlbum;
                    System.out.println("after searching result: "+tmpAlbum.getPhotolist().size());
                    displayResult();
                }

            }
        };

        btn_person.setOnClickListener(searchByPersonListner);

        View.OnClickListener searchByLocationListner = new View.OnClickListener(){
            @Override
            public void onClick (View view){
                if(txt_location.getText().toString().equals("")){
                    txt_location.setText("");
                    AlertDialog alert = new AlertDialog.Builder(SearchActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Need Location tag value to search.");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }else {
                    // search by Location tag value
                    Album tmpAlbum = new Album("tmp".trim());
                    Photo tmpPhoto;
                    boolean add = true;

                    for(Album album: albumlist) {
                        for(Photo photo: album.getPhotolist()) {
                            for (int i =0; i<photo.getTagList().size();i++) {
                                if(photo.getTagList().get(i).getType().equals("Location")
                                        && photo.getTagList().get(i).getValue().toLowerCase().contains(txt_location.getText().toString().toLowerCase())) {
                                    tmpPhoto = photo;
                                    for(Photo x : tmpAlbum.getPhotolist()) {
                                        if(x.getCaption().equals(tmpPhoto.getCaption()) ){
                                            add = false;
                                        }
                                    }
                                    if(add) {
                                        tmpAlbum.addPhoto(tmpPhoto);
                                    }
                                }
                            }
                        }
                    }
                    txt_location.setText("");
                    searchedResultAlbum = tmpAlbum;
                    displayResult();
                    //System.out.println("after searching result: "+tmpAlbum.getPhotolist().size());
                }
            }
        };

        btn_location.setOnClickListener(searchByLocationListner);

        View.OnClickListener searchByBothAndListner = new View.OnClickListener(){
            @Override
            public void onClick (View view){
                if(txt_both_person.getText().toString().equals("") || txt_both_location.getText().toString().equals("")){
                    txt_both_person.setText("");
                    txt_both_location.setText("");
                    AlertDialog alert = new AlertDialog.Builder(SearchActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Need both Location and Person tag value to search.");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }else {
                    // search by Location and person tag value
                    Album tmpAlbum = new Album("tmp".trim());
                    Photo tmpPhoto;

                    System.out.println("loc match should be "+txt_both_location.getText().toString().toLowerCase());

                    for(Album album: albumlist) {
                        for(Photo photo: album.getPhotolist()) {
                            boolean add = true;
                            boolean match1 = false;//location match
                            boolean match2 = false;//person match
                            System.out.println("photo name: "+photo.getCaption());
                            for (int i =0; i<photo.getTagList().size();i++) {

                                if(photo.getTagList().get(i).getType().equals("Location")&&
                                        photo.getTagList().get(i).getValue().toLowerCase().contains(txt_both_location.getText().toString().toLowerCase())){
                                    System.out.println("loc match");
                                    match1 = true;
                                }

                                if(photo.getTagList().get(i).getType().equals("Person")&&
                                        photo.getTagList().get(i).getValue().toLowerCase().contains(txt_both_person.getText().toString().toLowerCase())){
                                    match2 = true;
                                    System.out.println("per match");
                                }

                                if(match1 == true && match2 == true){
                                    tmpPhoto = photo;
                                    for(Photo x : tmpAlbum.getPhotolist()) {
                                        if(x.getCaption().equals(tmpPhoto.getCaption()) ){
                                            add = false;
                                        }
                                    }
                                    if(add) {
                                        System.out.println("added");
                                        tmpAlbum.addPhoto(tmpPhoto);
                                    }
                                }
                            }
                        }
                    }

                    txt_both_person.setText("");
                    txt_both_location.setText("");
                    searchedResultAlbum = tmpAlbum;
                    displayResult();
                    //System.out.println("after searching result: "+tmpAlbum.getPhotolist().size());
                }
            }
        };

        btn_and.setOnClickListener(searchByBothAndListner);


        View.OnClickListener searchByBothORListner = new View.OnClickListener(){
            @Override
            public void onClick (View view){
                if(txt_both_person.getText().toString().equals("") || txt_both_location.getText().toString().equals("")){
                    txt_both_person.setText("");
                    txt_both_location.setText("");
                    AlertDialog alert = new AlertDialog.Builder(SearchActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Need both Location and Person tag value to search.");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }else {
                    // search by Location and person tag value
                    Album tmpAlbum = new Album("tmp".trim());
                    Photo tmpPhoto;

                    for(Album album: albumlist) {
                        for(Photo photo: album.getPhotolist()) {
                            boolean add = true;
                            boolean match1 = false;//location match
                            boolean match2 = false;//person match
                            System.out.println("photo name: "+photo.getCaption());
                            for (int i =0; i<photo.getTagList().size();i++) {

                                if(photo.getTagList().get(i).getType().equals("Location")&&
                                        photo.getTagList().get(i).getValue().toLowerCase().contains(txt_both_location.getText().toString().toLowerCase())){
                                   // System.out.println("location match");
                                    match1 = true;
                                }

                                if(photo.getTagList().get(i).getType().equals("Person")&&
                                        photo.getTagList().get(i).getValue().toLowerCase().contains(txt_both_person.getText().toString().toLowerCase())){
                                    //System.out.println("person match");
                                    match2 = true;
                                }

                                if(match1 == true || match2 == true){
                                    //System.out.println("one of them are matched");
                                    tmpPhoto = photo;
                                    for(Photo x : tmpAlbum.getPhotolist()) {
                                        if(x.getCaption().equals(tmpPhoto.getCaption())){
                                            //System.out.println("there is same photo");
                                            add = false;
                                        }
                                    }
                                    if(add) {
                                        //System.out.println("added");
                                        tmpAlbum.addPhoto(tmpPhoto);
                                    }
                                }
                            }
                        }
                    }
                    txt_both_person.setText("");
                    txt_both_location.setText("");
                    searchedResultAlbum = tmpAlbum;
                    displayResult();
                    //System.out.println("after searching result: "+tmpAlbum.getPhotolist().size());
                }
            }
        };

        btn_or.setOnClickListener(searchByBothORListner);

    }

    public void displayResult (){
        MainActivity.albumcontroller.setSearchPhotos(searchedResultAlbum.getPhotolist());
        Intent albumIntent = new Intent(SearchActivity.this, SearchResultActivity.class);
        startActivity(albumIntent);

    }
}