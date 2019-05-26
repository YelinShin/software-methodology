package com.example.photoalbum51;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class PhotoMoveActivity extends AppCompatActivity {
    Spinner dropdownAlbums;

    Button btn_confirm,btn_back;
    int photopos =-1 , albumpos =-1 ;
    int movePos = -1;
    ArrayList<String> albumList = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_photo_display);

        photopos = getIntent().getIntExtra("photopos",0);
        albumpos = getIntent().getIntExtra("albumpos",0);
        dropdownAlbums = findViewById(R.id.spinner_albums);
        btn_confirm = findViewById(R.id.btn_confirmMove);
        btn_back = findViewById(R.id.btn_back);



        albumList = MainActivity.albumcontroller.getAlbumNames();


        ArrayAdapter<String> dropAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, albumList);
        dropdownAlbums.setAdapter(dropAdapter);
        btn_confirm.setOnClickListener(confirmMoveListener);
        btn_back.setOnClickListener(cancelListener);
    }

    View.OnClickListener confirmMoveListener = new View.OnClickListener(){
      @Override
      public void onClick(View view){
          //move photo to new album
          albumList = MainActivity.albumcontroller.getAlbumNames();
          for(int i =0;i< albumList.size() ;i ++ ){
              if (albumList.get(i).equals(dropdownAlbums.getSelectedItem().toString())) {
                  movePos = i;
              }
          }

          MainActivity.albumcontroller.getAlbums().get(movePos).getPhotolist().add(MainActivity.albumcontroller.getAlbums().get(albumpos).getPhotolist().remove(photopos));
          MainActivity.albumcontroller.write(PhotoMoveActivity.this);

          finish();

          Intent movePIntent = new Intent(PhotoMoveActivity.this,AlbumContentActivity.class );
          movePIntent.putExtra("albumPosition", movePos);
          startActivity(movePIntent);


      }
    };

    View.OnClickListener cancelListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            finish();

            Intent movePIntent = new Intent(PhotoMoveActivity.this,AlbumContentActivity.class );
            movePIntent.putExtra("albumPosition", albumpos);
            startActivity(movePIntent);


        }
    };
}
