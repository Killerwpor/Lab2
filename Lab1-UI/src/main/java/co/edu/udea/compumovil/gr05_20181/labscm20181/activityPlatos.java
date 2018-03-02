package co.edu.udea.compumovil.gr05_20181.labscm20181;

import android.Manifest;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;

public class activityPlatos extends AppCompatActivity {
    private Button botonGaleria, botonRegistrar;
    private NumberPicker pickerHorario;
    private EditText campoPrecio,campoNombre,campoIngredientes;
    ArrayList<Uri> selectedUriList;
    Uri selectedUri;
    private ViewGroup mSelectedImagesContainer;
   private ImageView iv_image;
    private TextView cuadroDatos;
    private RadioGroup grupoRadios;
    private RadioButton botonPlatoFuerte,botonEntrada;

    CheckBox rbm,rbt,rbn;
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
        //mGlideRequestManager = Glide.with(this);
        botonRegistrar= (Button) findViewById(R.id.botonRegistrar);
        pickerHorario= (NumberPicker) findViewById(R.id.numberPicker);

        rbm= (CheckBox) findViewById(R.id.mañanaRb);
        rbn= (CheckBox) findViewById(R.id.nocheRb);
        cuadroDatos= (TextView) findViewById(R.id.mostrarDatos);
        botonEntrada= (RadioButton) findViewById(R.id.radioButton);
        botonPlatoFuerte= (RadioButton) findViewById(R.id.radioButton2);
       // pickerHorario.setMinValue(0);
        //pickerHorario.setMaxValue(24);
        pickerHorario.setWrapSelectorWheel(true);


        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSingleShowButton();
               /* Glide.with(activityPlatos.this)
                        .load("https://pre00.deviantart.net/0352/th/pre/f/2013/120/7/b/png_monsters_inc_by_upinflames12-d63nx7i.png")
                        .into(iv_image);
                        */

            }
        });
 botonRegistrar.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
             cuadroDatos.setText(cuadroDatos.getText()+"Nombre: "+campoNombre.getText()+"\n");
         cuadroDatos.setText(cuadroDatos.getText()+"Precio: "+campoPrecio.getText()+"\n");
         cuadroDatos.setText(cuadroDatos.getText()+"Ingredientes: "+campoIngredientes.getText()+"\n");
        if(botonEntrada.isSelected())
         cuadroDatos.setText(cuadroDatos.getText()+"Entrada\n");
         else
            cuadroDatos.setText(cuadroDatos.getText()+"Plato Fuerte\n");


     }
 });

    }
    private void setSingleShowButton() {


        Button btn_single_show = (Button) findViewById(R.id.botonGaleria);
        btn_single_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {


                        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(activityPlatos.this)
                                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                                    @Override
                                    public void onImageSelected(Uri uri) {
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

                 TedPermission.with(activityPlatos.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET)
                        .check();

            }
        });
    }





}
