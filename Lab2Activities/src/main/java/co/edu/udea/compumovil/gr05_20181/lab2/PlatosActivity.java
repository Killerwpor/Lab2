package co.edu.udea.compumovil.gr05_20181.lab2;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr05_20181.lab2.data.plato;
import co.edu.udea.compumovil.gr05_20181.lab2.data.dbHelper;
import gun0912.tedbottompicker.TedBottomPicker;

public class PlatosActivity extends AppCompatActivity {
    private Menu menu;
    private Button botonGaleria, botonRegistrar;
    private NumberPicker pickerHorario;
    private EditText campoPrecio, campoNombre, campoIngredientes;
    ArrayList<Uri> selectedUriList;
    private Uri selectedUri = null;
    private ViewGroup mSelectedImagesContainer;
    private ImageView iv_image;
    private TextView etiqueta;
    private RadioGroup grupoRadios;
    private RadioButton botonPlatoFuerte, botonEntrada;
    private static final String RESUME_KEY = "resume";
    private static final String FOTO_KEY = "foto";
    private String datosrRecuperados;
    private String datosrRecuperados2;
    CheckBox rbm, rbt, rbn;
    private RecyclerView recyclerViewPlato;
    private RecyclerViewAdapterPlato adaptadorPlato;
    private plato plato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platos);
        botonGaleria = (Button) findViewById(R.id.botonGaleriaPlato);
        iv_image = (ImageView) findViewById(R.id.imageViewPlato);
        etiqueta = (TextView) findViewById(R.id.tiempoCoccion);
        campoPrecio = (EditText) findViewById(R.id.editTextPrecioPlato);
        campoIngredientes = (EditText) findViewById(R.id.editTextIngredientesPlato);
        campoNombre = (EditText) findViewById(R.id.editTextNombrePlato);
        grupoRadios = (RadioGroup) findViewById(R.id.grupoRadios);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrar);
        pickerHorario = (NumberPicker) findViewById(R.id.numberPicker);
        rbt = (CheckBox) findViewById(R.id.tardeRb);
        rbm = (CheckBox) findViewById(R.id.mañanaRb);
        rbn = (CheckBox) findViewById(R.id.nocheRb);
        botonEntrada = (RadioButton) findViewById(R.id.radioButton);
        botonPlatoFuerte = (RadioButton) findViewById(R.id.radioButton2);
        pickerHorario.setWrapSelectorWheel(true);
        etiqueta.setEnabled(false);
        if (savedInstanceState != null) {
            datosrRecuperados = savedInstanceState.getString(RESUME_KEY);
            datosrRecuperados2 = savedInstanceState.getString(FOTO_KEY);
            if (datosrRecuperados2 != null)
                selectedUri = Uri.parse(datosrRecuperados2);
            if (selectedUri != null) {
                Glide.with(PlatosActivity.this)
                        .load(selectedUri)
                        .into(iv_image);
            }
        }
        ////////////////////////////cuadroDatos.setMovementMethod(new ScrollingMovementMethod());
        rbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbn.setChecked(false);
                rbt.setChecked(false);
            }
        });

        rbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbm.setChecked(false);
                rbt.setChecked(false);
            }
        });

        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbm.setChecked(false);
                rbn.setChecked(false);
            }
        });

        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingleShowButton();
            }
        });

        pickerHorario.setMinValue(1);
        pickerHorario.setMaxValue(14);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foto = "1";
                String nombre, precio, ingredientes, horario = null, tipo = null, tiempo;
                nombre = String.valueOf(campoNombre.getText());
                precio = String.valueOf(campoPrecio.getText());
                ingredientes = String.valueOf(campoIngredientes.getText());
                if (botonEntrada.isChecked())
                    tipo = botonEntrada.getText().toString();
                else if (botonPlatoFuerte.isChecked())
                    tipo = botonPlatoFuerte.getText().toString();
                if (rbm.isChecked())
                    horario = rbm.getText().toString();
                else if (rbn.isChecked())
                    horario = rbn.getText().toString();
                else if (rbt.isChecked())
                    horario = rbt.getText().toString();
                tiempo = String.valueOf(pickerHorario.getValue());
                plato = new plato(nombre, horario, tipo, tiempo, foto, Float.valueOf(precio), ingredientes);
                dbHelper db = new dbHelper(getApplicationContext());
                db.guardarPlato(plato);
                recyclerViewPlato = (RecyclerView) findViewById(R.id.recycler_platos);
                recyclerViewPlato.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adaptadorPlato = new RecyclerViewAdapterPlato(obtenerPlatos());
                recyclerViewPlato.setAdapter(adaptadorPlato);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(RESUME_KEY, datosrRecuperados);
        savedInstanceState.putString(FOTO_KEY, datosrRecuperados2);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setSingleShowButton() {
        TedPermission.with(PlatosActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
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
            campoPrecio.setText("");
            campoIngredientes.setText("");
        } else if (id == R.id.salir) {
            System.exit(1);
        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(PlatosActivity.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            selectedUri = uri;
                            datosrRecuperados2 = String.valueOf(selectedUri);
                            Glide.with(PlatosActivity.this)
                                    .load(uri)
                                    .into(iv_image);
                        }
                    })
                    .create();
            tedBottomPicker.show(getSupportFragmentManager());
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(PlatosActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void guardarPreferencias(String campoDato) {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Platos", campoDato);
        editor.commit();
    }

    private void cargarPreferencias(TextView campoDato) {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dato = preferences.getString("Platos", "");
        campoDato.setText(dato);
    }

    private List<plato> obtenerPlatos(){
        List<plato> platos = new ArrayList<>();
        if(plato != null){
            platos.add(plato);
        }
        return platos;
    }
}
