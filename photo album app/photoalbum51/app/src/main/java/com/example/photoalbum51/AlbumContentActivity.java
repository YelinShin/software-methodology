package com.example.photoalbum51;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AlbumContentActivity extends AppCompatActivity  {

    int albumpos ;
    int photopos;
    String albumname;
    ListView photolist;
    Button btn_addPhoto, btn_delete, btn_display;
    AlbumContentDisplayAdapter contentAdapter;
    Toolbar mytoolbar;
    Bitmap photo_add;


    @Override
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_content);


        photolist = (ListView) findViewById(R.id.photo_list);
        btn_addPhoto = (Button)findViewById(R.id.btn_add);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_display = (Button) findViewById(R.id.btn_display);

        mytoolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_result);
        setSupportActionBar(mytoolbar);
        //need albumname

        albumpos =  getIntent().getIntExtra("albumPosition",0);
        albumname = MainActivity.albumcontroller.getAlbumNames().get(albumpos);
        getSupportActionBar().setTitle(albumname);



        btn_display.setOnClickListener(displayPhotoListener);
        btn_addPhoto.setOnClickListener(addPhotoListener);
        btn_delete.setOnClickListener(deletePhotoListener);
        photolist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Photo p = (Photo) contentAdapter.getItem(position);
                int j=0;
                for (j = 0; j < parent.getChildCount(); j++)
                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                //selected item has a different background color
                view.setBackgroundColor(Color.LTGRAY);
                photolist.setSelected(true);
                photopos = position;

            }
        });
        contentAdapter = new AlbumContentDisplayAdapter(this, R.layout.photo_setup,getThumbnail());
        photolist.setAdapter(contentAdapter);



    }
    View.OnClickListener displayPhotoListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            if(photolist.isSelected() == false ){
                AlertDialog alert = new AlertDialog.Builder(AlbumContentActivity.this).create();
                alert.setTitle("Alert");
                alert.setMessage("Please select photo to display");
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.show();
            }else {
                System.out.println("displaying photo");
                ArrayList<Photo> photos = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist();
                BitmapPhotos bitPhoto = photos.get(photopos).getPhoto();
                Photo tmp = (Photo) contentAdapter.getItem(photopos);

                finish();
                Intent intent = new Intent(AlbumContentActivity.this, PhotoDisplayActivity.class);
                intent.putExtra("photopos", photopos);
                intent.putExtra("albumpos", albumpos);
                startActivity(intent);
                update();
            }
        }

    };
    View.OnClickListener deletePhotoListener = new View.OnClickListener(){
        @Override
        public void onClick (View view){
            if (photolist.isSelected()==false) {

                AlertDialog alert = new AlertDialog.Builder(AlbumContentActivity.this).create();
                alert.setTitle("Alert");
                alert.setMessage("Please select photo to delete");
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.show();

            }else {
                //allow to delete photo
                deletePhoto();
                update();

            }
        }
    };

    View.OnClickListener addPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
        }
    };
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent info){
        super.onActivityResult(requestcode,resultcode,info);

        if(requestcode == 1 && resultcode == Activity.RESULT_OK){
            Uri image = info.getData();


            try{
                photo_add = MediaStore.Images.Media.getBitmap(this.getContentResolver(),image);
                BitmapPhotos bmPhotos = new BitmapPhotos(photo_add);
                Photo tmp = null;
                boolean add = true;

                if(photo_add !=null){
                    System.out.println("photo is selected");

                    for(int i=0; i<MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().size();i++){
                        if (MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().get(i).getCaption().equals(getCaption(image))){
                            add = false;
                        }
                    }

                    if(add){
                        tmp = new Photo(bmPhotos, getCaption(image), MainActivity.albumcontroller.getAlbums().get(albumpos),MainActivity.albumcontroller.getAllPhotos().size() +1 );
                        MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().add(tmp);
                        MainActivity.albumcontroller.write(AlbumContentActivity.this);
                        update();
                    } else{
                        AlertDialog alert = new AlertDialog.Builder(AlbumContentActivity.this).create();
                        alert.setTitle("Alert");
                        alert.setMessage("The photo is already exist in current album.");
                        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alert.show();
                    }



                }else {
                    AlertDialog alert = new AlertDialog.Builder(AlbumContentActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Please select a photo to add");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



    private ArrayList<Photo> getThumbnail(){
        final int size = 250;
        albumpos =  getIntent().getIntExtra("albumPosition",0);
        ArrayList<Photo> imgs = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist();
        ArrayList<Photo> thumbs = new ArrayList<Photo>();

        for(int i = 0;i < imgs.size() ; i++){
            BitmapPhotos bitPhotos = imgs.get(i).getPhoto();
            bitPhotos.bitmap = Bitmap.createScaledBitmap(bitPhotos.bitmap,size,size,false);
            thumbs.add(new Photo(bitPhotos, imgs.get(i).getCaption(),MainActivity.albumcontroller.getAlbums().get(albumpos),imgs.get(i).getPos()));
        }

        return thumbs;
    }

    public String getCaption (Uri uri){
        //caption will be the file name
        String caption = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try{
                if(cursor != null && cursor.moveToFirst()){
                    caption = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(caption == null){
            caption = uri.getPath();
            int split = caption.lastIndexOf('/');
            if(split != -1){
                caption = caption.substring(split + 1);
            }
        }
        return caption;
    }

    private void update(){
        contentAdapter = new AlbumContentDisplayAdapter(this, R.layout.photo_setup,getThumbnail());
        photolist.setAdapter(contentAdapter);
        photolist.setSelected(false);


    }

    private void deletePhoto(){
        MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().remove(photopos);
        MainActivity.albumcontroller.write(this);

    }

}


