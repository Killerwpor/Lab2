package co.edu.udea.compumovil.gr05_20181.lab2;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.compumovil.gr05_20181.lab2.data.ResponseBebida;
import gun0912.tedbottompicker.TedBottomPicker;
import retrofit2.Call;
import retrofit2.Response;

import static android.util.Log.println;
import static co.edu.udea.compumovil.gr05_20181.lab2.MainActivity.mApiService;

public class BebidasActivity extends AppCompatActivity {

    private Menu menu;

    private static final String RESUME_KEY = "resume";
    private static final String FOTO_KEY = "foto";
    private static String datosrRecuperados, datosrRecuperados2;
    private static Uri selectedUri = null;
    private static ImageView iv_image;
    private static EditText campoNombre, campoPrecio, campoIngredientes;
    private static Button botonGaleria, botonRegistrar;
    private static RecyclerView recyclerViewBebida;
    private static RecyclerViewAdapterBebida adaptadorBebida;
    private static ResponseBebida bebida = null;
    private static List<ResponseBebida> bebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        campoNombre = (EditText) findViewById(R.id.editTextNombreBebida);
        campoPrecio = (EditText) findViewById(R.id.editTextPrecioBebida);
        campoIngredientes = (EditText) findViewById(R.id.editTextIngredientesBebida);
        botonGaleria = (Button) findViewById(R.id.botonGaleriaBebida);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrarBebidas);
        iv_image = (ImageView) findViewById(R.id.imageViewBebida);
        recyclerViewBebida = (RecyclerView) findViewById(R.id.recycler_bebidas);
        if (savedInstanceState != null) {
            datosrRecuperados = savedInstanceState.getString(RESUME_KEY);
            datosrRecuperados2 = savedInstanceState.getString(FOTO_KEY);
            if (datosrRecuperados2 != null)
                selectedUri = Uri.parse(datosrRecuperados2);
            if (selectedUri != null) {
                Glide.with(BebidasActivity.this)
                        .load(selectedUri)
                        .into(iv_image);
            }
        }
        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingleShowButton();
                new PeticionBebidaPost().execute();
            }
        });
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PeticionBebidaPost().execute();

                actualizarBebidas();
            }
        });
        actualizarBebidas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem opcionMenu) {
        int id = opcionMenu.getItemId();
        if (id == R.id.limpiar) {
            campoNombre.setText("");
            campoIngredientes.setText("");
        } else if (id == R.id.salir) {
            System.exit(1);
        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(RESUME_KEY, datosrRecuperados);
        savedInstanceState.putString(FOTO_KEY, datosrRecuperados2);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void setSingleShowButton() {
        TedPermission.with(BebidasActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(BebidasActivity.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            selectedUri = uri;
                            datosrRecuperados2 = String.valueOf(selectedUri);
                            Glide.with(BebidasActivity.this)
                                    .load(uri)
                                    .into(iv_image);
                        }
                    })
                    .create();
            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(BebidasActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void guardarPreferencias(String campoDato){
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Bebidas", campoDato);
        editor.commit();
    }

    private void cargarPreferencias(TextView campoDato){
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dato = preferences.getString("Bebidas", "");
        campoDato.setText(dato);
    }

    private void actualizarBebidas() {

        new PeticionBebidaGet().execute();

        recyclerViewBebida.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adaptadorBebida = new RecyclerViewAdapterBebida(bebidas);
        recyclerViewBebida.setAdapter(adaptadorBebida);

    }

    public static class PeticionBebidaGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            bebidas = new ArrayList<>();
            String nombre, foto, ingredientes;
            Integer precio;
            Uri fotoUri;
            try {
                Call<List<ResponseBebida>> response = mApiService.getBebidas();
                for (ResponseBebida bebida : response.execute().body()) {
                    nombre = bebida.getNombre();
                    precio= bebida.getPrecio();
                    foto = bebida.getFoto();
                    ingredientes = bebida.getIngredientes();
                    bebida = new ResponseBebida(nombre, ingredientes, precio, foto);
                    bebidas.add(bebida);
                }
            } catch(IOException e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

    public static class PeticionBebidaPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String nombre, foto, ingredientes;
            Integer precio;
            try{
                nombre = String.valueOf(campoNombre.getText());
                ingredientes = String.valueOf(campoIngredientes.getText());
                precio = Integer.parseInt(String.valueOf(campoPrecio.getText()));
                foto = datosrRecuperados2;
                Call<ResponseBebida> response = mApiService.postBebida(nombre.trim(), ingredientes.trim(), precio, foto);
                response.execute();
            } catch(Exception e){
                Log.e("ERROR", e.toString());
            }
            return null;
        }
    }

}
