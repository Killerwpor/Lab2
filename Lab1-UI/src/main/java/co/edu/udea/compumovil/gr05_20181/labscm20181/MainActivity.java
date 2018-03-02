package co.edu.udea.compumovil.gr05_20181.labscm20181;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Menu menu;
    //private Intent activityPlatos = new Intent(this, activityPlatos.class);
    //private Intent activityBebidas = new Intent(getApplicationContext(), activityBebidas.class);
    private Button botonPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonPrueba= (Button) findViewById(R.id.botonPrueba);
        botonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad2= new Intent(getApplicationContext(), activityPlatos.class);
                startActivity(actividad2);
            }
        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem opcionMenu) {
        int id = opcionMenu.getItemId();
        if (id == R.id.platos) {

        }
        if (id == R.id.bebidas) {
           //startActivity(activityBebidas);
        }
        if (id == R.id.salir) {
            System.exit(1);
        }
        return true;
    }
    */

}
