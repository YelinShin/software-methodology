package com.example.photoalbum51;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PhotoDisplayActivity extends AppCompatActivity {
    Button btn_next, btn_prev, btn_move, btn_addTag, btn_deleteTag, btn_back;
    TextView caption;
    int photopos, albumpos;
    ImageView displayphoto;
    ListView tagListView;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<Tag> tagArrayList;
    ArrayList<String> strTagList;
    int selectedPosition=-1;

    ArrayList<Photo> photolist;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_display);

        btn_move = findViewById(R.id.btn_move);
        btn_back = findViewById(R.id.btn_backAlbum);
        btn_addTag = findViewById(R.id.btn_addTag);
        btn_deleteTag = findViewById(R.id.btn_deleteTag);
        displayphoto = findViewById(R.id.img_soloPhoto);
        tagListView = findViewById(R.id.tag_list);
        btn_next = findViewById(R.id.btn_forward);
        btn_prev = findViewById(R.id.btn_back);
        caption = findViewById(R.id.txt_caption);
        photopos = getIntent().getIntExtra("photopos",0);
        albumpos = getIntent().getIntExtra("albumpos",0);
        caption.setText(MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos).getCaption());
        BitmapPhotos bitPhoto = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos).getPhoto();
        bitPhoto.bitmap = Bitmap.createScaledBitmap(bitPhoto.bitmap,250,250,false);

        displayphoto.setImageBitmap(bitPhoto.bitmap);

        strTagList = new ArrayList<String>();
        tagArrayList = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().get(photopos).getTagList();
        if(tagArrayList!=null){
            for(int i = 0;i <tagArrayList.size();i++){
                strTagList.add(tagArrayList.get(i).getType() + " : " + tagArrayList.get(i).getValue());
            }
        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strTagList);
        tagListView.setAdapter(arrayAdapter);



        final AdapterView.OnItemClickListener resetSelectListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int j=0;
                for (j = 0; j < parent.getChildCount(); j++)
                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                //selected item has a different background color
                view.setBackgroundColor(Color.LTGRAY);
                tagListView.setSelected(true);
                selectedPosition = position;

            };
        };

        tagListView.setOnItemClickListener(resetSelectListener);


        btn_prev.setOnClickListener(prevPhotoListener);
        btn_next.setOnClickListener(nextPhotoListener);
        btn_addTag.setOnClickListener(addTagListener);
        btn_back.setOnClickListener(backtoAlbum);
        btn_deleteTag.setOnClickListener(deleteTagListener);
        btn_move.setOnClickListener(moveAlbumListener);
    }
    View.OnClickListener backtoAlbum = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            finish();
            Intent movePIntent = new Intent(PhotoDisplayActivity.this,AlbumContentActivity.class );
            movePIntent.putExtra("albumPosition", albumpos);
            startActivity(movePIntent);

        }
    };
    View.OnClickListener moveAlbumListener = new View.OnClickListener(){
        @Override
        public void onClick (View view){
            finish();
            Intent movePIntent = new Intent(PhotoDisplayActivity.this,PhotoMoveActivity.class );
            movePIntent.putExtra("photopos", photopos);
            movePIntent.putExtra("albumpos", albumpos);
            startActivity(movePIntent);

        }
    };
    View.OnClickListener prevPhotoListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            photolist = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist();
            if(photolist!=null){
                if(photopos == 0){
                    photopos = photolist.size()-1;
                }else{
                    photopos --;
                }
                caption.setText(photolist.get(photopos).getCaption());
                displayphoto.setImageBitmap(photolist.get(photopos).getPhoto().bitmap);



                tagArrayList = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().get(photopos).getTagList();
                ArrayList<String> tmpStrTags = new ArrayList<String>();
                if(tagArrayList!=null){
                    for(int i = 0;i <tagArrayList.size();i++){
                        tmpStrTags.add(tagArrayList.get(i).getType() + " : " + tagArrayList.get(i).getValue());
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(PhotoDisplayActivity.this, android.R.layout.simple_list_item_multiple_choice, tmpStrTags);
                tagListView.setAdapter(arrayAdapter);

            }
        }
    };

    View.OnClickListener nextPhotoListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            photolist = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist();
            if(photolist !=null){
                if(photopos == photolist.size() -1){
                    photopos = 0;
                }else {
                    photopos ++;
                }
                caption.setText(photolist.get(photopos).getCaption());
                displayphoto.setImageBitmap(photolist.get(photopos).getPhoto().bitmap);
                tagArrayList = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().get(photopos).getTagList();
                ArrayList<String> tmpStrTags = new ArrayList<String>();
                if(tagArrayList!=null){
                    for(int i = 0;i <tagArrayList.size();i++){
                        tmpStrTags.add(tagArrayList.get(i).getType() + " : " + tagArrayList.get(i).getValue());
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(PhotoDisplayActivity.this, android.R.layout.simple_list_item_multiple_choice, tmpStrTags);
                tagListView.setAdapter(arrayAdapter);

            }
        }
    };

    View.OnClickListener addTagListener = new View.OnClickListener(){
        @Override
        public void onClick (View view){
            //make new intent to turn to new page
            finish();
            Intent addTagIntent = new Intent(PhotoDisplayActivity.this,PhotoAddTagActivity.class );
            addTagIntent.putExtra("photopos", photopos);
            addTagIntent.putExtra("albumpos", albumpos);
            startActivity(addTagIntent);


        }

    };

    View.OnClickListener deleteTagListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            if(tagListView.isSelected() == false){
                AlertDialog alert = new AlertDialog.Builder(PhotoDisplayActivity.this).create();
                alert.setTitle("Alert");
                alert.setMessage("Please select tag to delete");
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.show();
            }else {
                MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos).getTagList().remove(selectedPosition);
                //write
                MainActivity.albumcontroller.write(PhotoDisplayActivity.this);
                reset();

            }

        }
    };

    private void reset(){
        strTagList= new ArrayList<String>();
        tagArrayList = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().get(photopos).getTagList();
        if(tagArrayList!=null){
            for(int i = 0;i <tagArrayList.size();i++){
                strTagList.add(tagArrayList.get(i).getType() + " : " + tagArrayList.get(i).getValue());
            }
        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strTagList);
        tagListView.setAdapter(arrayAdapter);

        tagListView.setSelected(false);

    }


}
