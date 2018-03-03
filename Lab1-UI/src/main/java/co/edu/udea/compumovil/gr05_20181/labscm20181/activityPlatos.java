package co.edu.udea.compumovil.gr05_20181.labscm20181;

import android.Manifest;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import gun0912.tedbottompicker.TedBottomPicker;

public class activityPlatos extends AppCompatActivity {
    private Menu menu;
    private Button botonGaleria, botonRegistrar;
    private NumberPicker pickerHorario;
    private EditText campoPrecio,campoNombre,campoIngredientes, tiempoCoccion;
    ArrayList<Uri> selectedUriList;
    private Uri selectedUri;
    private ViewGroup mSelectedImagesContainer;
    private ImageView iv_image;
    private TextView cuadroDatos;
    private RadioGroup grupoRadios;
    private RadioButton botonPlatoFuerte,botonEntrada;
    private String horario;
    private CheckBox rbm,rbt,rbn;
    public RequestManager mGlideRequestManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platos);
        botonGaleria= (Button) findViewById(R.id.botonGaleriaPlato);
        iv_image= (ImageView) findViewById(R.id.imageViewPlato);
        campoPrecio= (EditText) findViewById(R.id.editTextPrecioPlato);
        campoIngredientes= (EditText) findViewById(R.id.editTextIngredientesPlato);
        campoNombre= (EditText) findViewById(R.id.editTextNombrePlato);
        grupoRadios= (RadioGroup) findViewById(R.id.grupoRadios);
        tiempoCoccion = (EditText) findViewById(R.id.tiempoCoccion);
        //mGlideRequestManager = Glide.with(this);
        botonRegistrar= (Button) findViewById(R.id.botonRegistrar);
        pickerHorario= (NumberPicker) findViewById(R.id.numberPicker);
        rbt= (CheckBox) findViewById(R.id.tardeRb);
        rbm= (CheckBox) findViewById(R.id.mañanaRb);
        rbn= (CheckBox) findViewById(R.id.nocheRb);
        horario = getString(R.string.platos_horario);
        cuadroDatos= (TextView) findViewById(R.id.mostrarDatos);
        botonEntrada= (RadioButton) findViewById(R.id.radioButton);
        botonPlatoFuerte= (RadioButton) findViewById(R.id.radioButton2);
        pickerHorario.setWrapSelectorWheel(true);
        cuadroDatos.setMovementMethod(new ScrollingMovementMethod());
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
                cuadroDatos.setText(cuadroDatos.getText() + campoNombre.getHint().toString() + ": " + campoNombre.getText() + "\n");
                cuadroDatos.setText(cuadroDatos.getText() + campoPrecio.getHint().toString() + ": " + campoPrecio.getText() + "\n");
                cuadroDatos.setText(cuadroDatos.getText() + campoIngredientes.getHint().toString() + ": " + campoIngredientes.getText() + "\n");
                if(botonEntrada.isSelected())
                    cuadroDatos.setText(cuadroDatos.getText() + botonEntrada.getText().toString() + "\n");
                else if(botonPlatoFuerte.isSelected())
                    cuadroDatos.setText(cuadroDatos.getText() + botonPlatoFuerte.getText().toString() + "\n");
                if(rbm.isChecked())
                    cuadroDatos.setText(cuadroDatos.getText() + horario + ": " + rbm.getText().toString() + "\n");
                else if(rbn.isChecked())
                    cuadroDatos.setText(cuadroDatos.getText() + horario + rbn.getText().toString() + "\n");
                else if(rbt.isChecked())
                    cuadroDatos.setText(cuadroDatos.getText() + horario + rbt.getText().toString() + "\n");
                String tiempo = String.valueOf(pickerHorario.getValue());
                cuadroDatos.setText(cuadroDatos.getText() + tiempoCoccion.getText().toString() + ": " + tiempo + "\n");
            }
        });
    }







    private void setSingleShowButton() {


        TedPermission.with(activityPlatos.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Debe aceptar los permisos")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem opcionMenu){
        int id = opcionMenu.getItemId();
        if(id == R.id.limpiar){

        } else if(id == R.id.salir){
            System.exit(1);
        } else if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

    PermissionListener permissionlistener = new PermissionListener() {


        @Override
        public void onPermissionGranted() {


            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(activityPlatos.this)
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            selectedUri=uri;
                            Glide.with(activityPlatos.this)
                                    .load(uri)
                                    //.placeholder(R.drawable.img_error)
                                    .into(iv_image);
                        }
                    })
                    .create();

            tedBottomPicker.show(getSupportFragmentManager());


        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(activityPlatos.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };



}
