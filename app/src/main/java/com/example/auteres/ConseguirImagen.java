package com.example.auteres;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;

public class ConseguirImagen extends AsyncTask<String,Void,Bitmap> {

    WeakReference<ImageView> imgV;
    Bitmap bitmap;
    public ConseguirImagen(WeakReference<ImageView> imgV){
        this.imgV = imgV;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay = url[0];
        bitmap = null;
        try {
            InputStream srt = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(srt);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imgV.get().setImageBitmap(bitmap);
    }
}
