package com.example.photoalbum51;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoAddTagActivity extends AppCompatActivity {

    Spinner dropdownTypes;
    EditText txt_value;
    Button btn_addTag,btn_cancel;
    int photopos =-1 , albumpos =-1 ;
    ArrayList<Tag> tagList;
    Photo p;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tag_display);


        dropdownTypes = findViewById(R.id.spinner_tags);
        txt_value = findViewById(R.id.txt_tagValue);
        btn_addTag = findViewById(R.id.btn_confirmTag);
        btn_cancel = findViewById(R.id.btn_cancel);
        String[] tagTypes = new String [] {"Person","Location"};
        ArrayAdapter<String> dropAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tagTypes);
        dropdownTypes.setAdapter(dropAdapter);



        photopos = getIntent().getIntExtra("photopos",0);
        albumpos = getIntent().getIntExtra("albumpos",0);



        p = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos);

        btn_addTag.setOnClickListener(saveTag);
        btn_cancel.setOnClickListener(cancel);
        tagList = new ArrayList<Tag>();
        tagList = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos).getTagList();

    }

    View.OnClickListener cancel = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            finish();

            Intent movePIntent = new Intent(PhotoAddTagActivity.this,AlbumContentActivity.class );
            movePIntent.putExtra("albumPosition", albumpos);
            startActivity(movePIntent);


        }
    };

    View.OnClickListener saveTag = new View.OnClickListener(){
        @Override
        public void onClick (View view){
            if(txt_value.getText().toString().equals("") ){
                AlertDialog alert = new AlertDialog.Builder(PhotoAddTagActivity.this).create();
                alert.setTitle("Alert");
                alert.setMessage("Tag Value cannot be blank");
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.show();
                clear();
            }else {
                //add tag
                tagList = new ArrayList<Tag>();

                Tag tmp = new Tag(dropdownTypes.getSelectedItem().toString(), txt_value.getText().toString().trim());
                tagList = MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos).getTagList();
                if(tagList != null){
                    if(isDup(tmp) == true){
                        AlertDialog alert = new AlertDialog.Builder(PhotoAddTagActivity.this).create();
                        alert.setTitle("Alert");
                        alert.setMessage("Tag Value cannot be Duplicate");
                        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alert.show();
                        clear();
                    }else {
                        p.getTagList().add(tmp);
                        MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos).setTagList(tagList);
                        MainActivity.albumcontroller.write(PhotoAddTagActivity.this);
                        finish();
                        Intent photoIntent = new Intent(PhotoAddTagActivity.this,PhotoDisplayActivity.class );
                        photoIntent.putExtra("albumpos", albumpos);
                        photoIntent.putExtra("photopos",photopos);
                        startActivity(photoIntent);

                    }
                }else {
                    tagList = new ArrayList<Tag>();
                    tagList.add(tmp);
                    p.setTagList(tagList);
                    MainActivity.albumcontroller.getAlbums().get(albumpos).getPhoto(photopos).setTagList(tagList);
                    MainActivity.albumcontroller.write(PhotoAddTagActivity.this);
                    finish();
                    Intent photoIntent = new Intent(PhotoAddTagActivity.this,PhotoDisplayActivity.class );
                    photoIntent.putExtra("albumpos", albumpos);
                    photoIntent.putExtra("photopos",photopos);
                    startActivity(photoIntent);
                }
            }
        }
    };

    public void clear(){
        txt_value.setText("");
    }

    public boolean isDup (Tag tag){


        if(p.getTagList()!=null){
            for(int i = 0;i <p.getTagList().size();i++){

                if(p.getTagList().get(i).getType().equals(tag.getType()) && p.getTagList().get(i).getValue().equals(tag.getValue())){
                    return true;
                }
            }
        }

        return false;
    }


}
