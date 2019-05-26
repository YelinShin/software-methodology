package com.example.photoalbum51;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapPhotos implements Serializable {

    public Bitmap bitmap;
    private static final long serialVersionUID = 51L;

    public BitmapPhotos(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    // makes bitmap array for serialization of photos
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream stream = null;
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte bytes[] = stream.toByteArray();
        out.write(bytes, 0, bytes.length);
    }

    //array for bitmap is decoded for data
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            stream.write(b);
        byte bytes[] = stream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}