package com.example.photoalbum51;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AlbumContentDisplayAdapter extends ArrayAdapter {
    private int id;
    private ArrayList<Photo> content = new ArrayList();
    private Context context;

    public AlbumContentDisplayAdapter (Context context, int id, ArrayList<Photo> content){
        super(context, id, content);
        this.id = id;
        this.content = content;
        this.context = context;
    }
    public ArrayList<Photo> getContent() {
        return content;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View tmp = view;
        DisplayHolder holder =null;

        if(tmp == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            tmp = inflater.inflate(id, parent,false);
            holder = new DisplayHolder();
            holder.image = (ImageView)tmp.findViewById(R.id.imgviewPhoto);
            holder.title = (TextView)tmp.findViewById(R.id.textviewPhoto);
            tmp.setTag(holder);
        }else {
            holder = (DisplayHolder) tmp.getTag();
        }
        Photo p = (Photo)content.get(position);
        holder.image.setImageBitmap(p.getPhoto().bitmap);
        holder.title.setText(p.getCaption());
        return tmp;
    }

    static class DisplayHolder{
        TextView title;
        ImageView image;
    }

}
