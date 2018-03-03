package co.edu.udea.compumovil.gr05_20181.labscm20181;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class activityBebidas extends AppCompatActivity {

    private Menu menu;
    private String current_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String lang = intent.getStringExtra("lang");
        setLanguage(lang);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
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

    public void setLanguage(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
