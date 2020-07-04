package com.example.auteres;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

public class ConseguirLibro extends AsyncTask<String,Void,String> {

    private WeakReference<TextView> mTextoTitulo;
    private WeakReference<TextView> mTextoAutor;
    private WeakReference<ImageView> mImagenLibro;
    private WeakReference<TextView> mEditoraLibro;
    private WeakReference<TextView> mFechaMuplicacionLibro;
    private WeakReference<TextView> mDescripcionLibro;


    ConseguirLibro(TextView tituloTexto,TextView autorTexto,ImageView imagenLibro,TextView editoraTexto,TextView fechapublicacionTexto,TextView descripcionTexto){
        this.mTextoTitulo = new WeakReference<>(tituloTexto);
        this.mTextoAutor = new WeakReference<>(autorTexto);
        this.mImagenLibro = new WeakReference<>(imagenLibro);
        this.mEditoraLibro = new WeakReference<>(editoraTexto);
        this.mFechaMuplicacionLibro = new WeakReference<>(fechapublicacionTexto);
        this.mDescripcionLibro = new WeakReference<>(descripcionTexto);
    }
    @Override
    protected String doInBackground(String... strings) {
        return UtilidadesRed.obtenerInformacionLibro(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i= 0;
            String titulo = null;
            String autores = null;
            String smallThumbnail = null;
            String editora = null;
            String fechapublicacion = null;
            String descripcion = null;
            while(i < itemsArray.length() && (autores == null && titulo == null && descripcion == null)){
                JSONObject libro = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = libro.getJSONObject("volumeInfo");
                try{
                    titulo = volumeInfo.getString("title");
                    autores = volumeInfo.getString("authors");
                    editora = volumeInfo.getString("publisher");
                    fechapublicacion = volumeInfo.getString("publishedDate");
                    descripcion = volumeInfo.getString("description");


                }catch (Exception e){
                    mTextoTitulo.get().setText("No existen resultados");
                    mTextoAutor.get().setText("");
                    mEditoraLibro.get().setText("");
                    mFechaMuplicacionLibro.get().setText("");
                    mDescripcionLibro.get().setText("");

                    e.printStackTrace();
                }

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                try{
                    smallThumbnail = imageLinks.getString("smallThumbnail");
                }catch (Exception e){
                    smallThumbnail = "";
                    e.printStackTrace();
                }

                i++;
            }

            if(titulo != null && autores != null){
                mTextoTitulo.get().setText(titulo);
                autores = autores.replace("\"", "");
                autores = autores.replace("[", "");
                autores = autores.replace("]", "");
                mTextoAutor.get().setText(autores);
                mEditoraLibro.get().setText(editora);
                mFechaMuplicacionLibro.get().setText(fechapublicacion);
                mDescripcionLibro.get().setText(descripcion);
                if (descripcion == null || descripcion.equals("")){
                    descripcion = "Este libro no cuenta con una descripción";
                    mDescripcionLibro.get().setText(descripcion);
                }

                ConseguirImagen imagen = new ConseguirImagen(mImagenLibro);
                imagen.execute(smallThumbnail);


            }else{
                mTextoTitulo.get().setText("No existen resultados");
                mTextoAutor.get().setText("");
                mEditoraLibro.get().setText("");
                mFechaMuplicacionLibro.get().setText("");
                mDescripcionLibro.get().setText("");
            }

        }catch (JSONException  e){
            e.printStackTrace();
        }
    }

}
