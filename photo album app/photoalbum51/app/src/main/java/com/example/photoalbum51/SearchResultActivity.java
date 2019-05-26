package com.example.photoalbum51;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class SearchResultActivity extends AppCompatActivity {
    Button btn_done;
    ListView resultListView;
    Toolbar mytoolbar;
    AlbumContentDisplayAdapter contentAdapter;
    ArrayList<Photo> resultAlbum;

    @Override
    protected  void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_album_content);

        btn_done = findViewById(R.id.btn_done);
        resultListView = findViewById(R.id.search_photo_list);






        contentAdapter = new AlbumContentDisplayAdapter(this, R.layout.photo_setup,getThumbnail());
        resultListView.setAdapter(contentAdapter);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar_result);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("Search Result Album");

        btn_done.setOnClickListener(closeListener);
    }

    private ArrayList<Photo> getThumbnail(){
        final int size = 250;
        int albumpos = -1;
        Album tmp = new Album("tmp");



        ArrayList<Photo> imgs = MainActivity.albumcontroller.getSearchPhotos();
        ArrayList<Photo> thumbs = new ArrayList<Photo>();

        for(int i = 0;i < imgs.size() ; i++){
            BitmapPhotos bitPhotos = imgs.get(i).getPhoto();
            bitPhotos.bitmap = Bitmap.createScaledBitmap(bitPhotos.bitmap,size,size,false);
            thumbs.add(new Photo(bitPhotos, imgs.get(i).getCaption(),tmp, imgs.get(i).getPos()));
        }

        return thumbs;
    }

    View.OnClickListener closeListener = new View.OnClickListener(){
        @Override
        public void onClick (View view){
            finish();
        }
    };


}
