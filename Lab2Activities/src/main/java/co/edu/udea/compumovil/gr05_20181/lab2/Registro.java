package co.edu.udea.compumovil.gr05_20181.lab2;

import android.Manifest;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import co.edu.udea.compumovil.gr05_20181.lab2.data.usuario;
import co.edu.udea.compumovil.gr05_20181.lab2.data.usuarioContract;
import gun0912.tedbottompicker.TedBottomPicker;

import static android.util.Log.println;

public class Registro extends AppCompatActivity {

    private ImageButton botonImagen;
    private Button botonGuardar;
    private EditText campoNombre, campoApellido, campoContraseña,campoCorreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        botonGuardar=findViewById(R.id.botonRegistrarse);
        botonImagen = findViewById(R.id.botonImagen);
        campoNombre=findViewById(R.id.campoNombre);
        campoApellido=findViewById(R.id.campoApellido);
        campoContraseña=findViewById(R.id.campoNombre);
        campoCorreo=findViewById(R.id.campoEmail);



       botonGuardar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String foto="test";
               String nombre, correo, contraseña, apellido;
               nombre= String.valueOf(campoNombre.getText());
               apellido= String.valueOf(campoApellido.getText());
               correo= String.valueOf(campoCorreo.getText());
               contraseña= String.valueOf(campoContraseña.getText());
               usuario user=new usuario(nombre,contraseña,apellido,correo,foto);
               dbHelper db=new dbHelper(getApplicationContext());
               db.guardarUsuario(user);
           }
       });


    }


}

