package com.example.auteres;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private EditText mInputLibro;
    private TextView mTextoTitulo,mTextoAutor,mTextoEditora,mTextoFechaPublicacion,mTextDescripcion;
    private ImageView mImagenLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputLibro = (EditText) findViewById(R.id.ingresoLibro);
        mTextoTitulo = (TextView) findViewById(R.id.titulo);
        mTextoAutor = (TextView) findViewById(R.id.autorLibro);
        mImagenLibro = (ImageView) findViewById(R.id.imageLibro);
        mTextoEditora = (TextView) findViewById(R.id.editora);
        mTextoFechaPublicacion = (TextView) findViewById(R.id.fechaPublicacion);
        mTextDescripcion = (TextView) findViewById(R.id.descripcion);





    }


    public void buscarLibro(View view){
        String cadenaBusqueda = mInputLibro.getText().toString();
        new ConseguirLibro(mTextoTitulo,mTextoAutor,mImagenLibro,mTextoEditora,mTextoFechaPublicacion,mTextDescripcion).execute(cadenaBusqueda);
    }
}
