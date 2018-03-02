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
    private Button botonBebidas;
    private Button botonPlatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonPlatos = (Button) findViewById(R.id.botonPlatos);
        botonPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividadPlatos = new Intent(getApplicationContext(), activityPlatos.class);
                startActivity(actividadPlatos);
            }
        });
        botonBebidas = (Button) findViewById(R.id.botonBebidas);
        botonBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividadBebidas = new Intent(getApplicationContext(), activityBebidas.class);
                startActivity(actividadBebidas);
            }
        });
    }

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
        if (id == R.id.salir) {
            System.exit(1);
        }
        return true;
    }

}
