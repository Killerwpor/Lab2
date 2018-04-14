package co.edu.udea.compumovil.gr05_20181.lab2;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.edu.udea.compumovil.gr05_20181.lab2.data.bebidaContract;
import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import co.edu.udea.compumovil.gr05_20181.lab2.data.usuarioContract;

import static android.util.Log.println;

public class PerfilActivity extends AppCompatActivity {

    ImageView imagen;
    TextView nombre, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.campoNombre2);
        correo = findViewById(R.id.campoCorreo2);
        dbHelper db = new dbHelper(getApplicationContext());
        String Usuario= getIntent().getStringExtra("Usuario");
        Cursor c = db.obtenerUsuarioPorCedula(Usuario);
        c.moveToNext();
        nombre.setText(c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.NOMBRE)) + c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.APELLIDO)));
        correo.setText(c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.CORREO)));
        Uri uri = Uri.parse(c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.FOTO)));
        println(Log.ERROR,"MYTAG",c.getString(c.getColumnIndex(usuarioContract.usuarioEntry.FOTO)));
        Glide.with(PerfilActivity.this)
                .load(uri)
                .into(imagen);
    }
}
