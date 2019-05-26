package com.example.photoalbum51;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.photoalbum51.Albums;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    ListView albumNames;
    ArrayAdapter <String> arrayAdapter;
    ArrayList<Album> albums;
    ArrayList<String> stralbums;
    int selectedPosition=-1;

    public static Albums albumcontroller;
    Toolbar myToolbar;

    ListView albumlistView ;
    Button btn_add, btn_delete, btn_rename, btn_openAlbum, btn_clear;
    EditText txt_rename, txt_add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumlistView = (ListView) findViewById(R.id.album_list);
        btn_add = (Button)findViewById(R.id.btn_addAlbum);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_rename = (Button)findViewById(R.id.btn_rename);
        btn_openAlbum = (Button)findViewById(R.id.btn_openAlbum);
        btn_clear = (Button)findViewById(R.id.btn_clear);
        txt_add = (EditText)findViewById(R.id.txt_addAlbum);
        txt_rename = (EditText)findViewById(R.id.txt_renameAlbum);


        myToolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Photo Album 51");

        albumcontroller = Albums.read(this);

        if (albumcontroller == null) {
            albumcontroller = new Albums();
            Album stock = new Album("stock");
            final int thumbnailsize = 250;
            TypedArray array_imgs = getResources().obtainTypedArray(R.array.stock_imgs);
            for (int i = 0; i < array_imgs.length(); i++) {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), array_imgs.getResourceId(i, -1));
                BitmapPhotos sBitmap = new BitmapPhotos(bm);
                sBitmap.bitmap = Bitmap.createScaledBitmap(sBitmap.bitmap, thumbnailsize, thumbnailsize, false);
                stock.getPhotolist().add(new Photo(sBitmap, "stock_img" + i, stock, MainActivity.albumcontroller.getAllPhotos().size() + 1));
                //for testing purposes the caption for stock photos is stock_img from drawable
            }

            albumcontroller.addAlbum(stock);
            albumcontroller.write(this);
        }
        albums = albumcontroller.getAlbums();

        stralbums = albumcontroller.getAlbumNames();

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, stralbums);
        albumlistView.setAdapter(arrayAdapter);

        final AdapterView.OnItemClickListener resetSelectListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int j=0;
                for (j = 0; j < parent.getChildCount(); j++)
                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                //selected item has a different background color
                view.setBackgroundColor(Color.LTGRAY);
                albumlistView.setSelected(true);
                selectedPosition = position;

            };
        };

        View.OnClickListener addAlbumListener = new View.OnClickListener(){
            @Override
            public void onClick (View view){
                if(txt_add.getText().toString().equals("")){
                    txt_add.setText("");
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("New Album name cannot be blank");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }else if(stralbums.contains(txt_add.getText().toString())){
                    txt_add.setText("");
                    AlertDialog alertDup = new AlertDialog.Builder(MainActivity.this).create();
                    alertDup.setTitle("Alert");
                    alertDup.setMessage("Duplicate Album name is already exists");
                    alertDup.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDup.show();
                }else {
                    addAlbum(txt_add.getText().toString());

                }
            }
        };

        //clear value
        View.OnClickListener clearValueListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_add.setText("");
                txt_rename.setText("");
                albumlistView.setSelected(false);
                albumlistView.setAdapter(arrayAdapter);
            }
        };

        View.OnClickListener openAlbumListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("isSelected onclick: "+ albumlistView.isSelected());
                if (albumlistView.isSelected()==false) {

                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Please select album to open");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();

                }else {
                    //make new activity, new window
                    Intent albcontentIntent = new Intent(MainActivity.this,AlbumContentActivity.class);
                    albcontentIntent.putExtra("albumPosition", selectedPosition);
                    startActivity(albcontentIntent);
                    reset();
                }
            }

        };


        View.OnClickListener renameAlbumListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (albumlistView.isSelected() == false) {
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Please select album to rename");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();

                } else if (stralbums.contains(txt_rename.getText().toString())) {
                    txt_rename.setText("");
                    AlertDialog alertDup = new AlertDialog.Builder(MainActivity.this).create();
                    alertDup.setTitle("Alert");
                    alertDup.setMessage("Duplicate Album name is already exists");
                    alertDup.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDup.show();
                } else if (txt_rename.getText().toString().equals("")) {
                    txt_rename.setText("");
                    AlertDialog alertDup = new AlertDialog.Builder(MainActivity.this).create();
                    alertDup.setTitle("Alert");
                    alertDup.setMessage("Album name cannot be empty. Please re-enter the name.");
                    alertDup.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDup.show();
                } else {

                    Album selctedAlbum = albums.get(selectedPosition);
                    selctedAlbum.setAlbumName(txt_rename.getText().toString());
                    txt_rename.setText("");
                    Activity_write();

                }

            }

        };

        View.OnClickListener deleteAlbumListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (albumlistView.isSelected() == false) {
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    alert.setTitle("Alert");
                    alert.setMessage("Please select album to delete");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();

                } else {
                    albumcontroller.removeAlbum(selectedPosition);
                    Activity_write();
                }

            }

        };

        albumlistView.setOnItemClickListener(resetSelectListener);
        btn_openAlbum.setOnClickListener(openAlbumListener);
        btn_rename.setOnClickListener(renameAlbumListener);
        btn_clear.setOnClickListener(clearValueListener);
        btn_add.setOnClickListener(addAlbumListener);
        btn_delete.setOnClickListener(deleteAlbumListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_personsearch) {
            Intent searchIntent = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(searchIntent);
            reset();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private boolean addAlbum(String album){
        Album tmp = new Album(album);
        txt_add.setText("");
        albumcontroller.addAlbum(tmp);
        Activity_write();
        return true;
    }

    private boolean Activity_write(){
        albumcontroller.write(this);
        reset();
        return true;
    }

    private void reset(){
        stralbums=albumcontroller.getAlbumNames();
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, stralbums);
        albumlistView.setSelected(false);
        albumlistView.setAdapter(arrayAdapter);
    }


}
