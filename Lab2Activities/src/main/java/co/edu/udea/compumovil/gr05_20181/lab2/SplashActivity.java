package co.edu.udea.compumovil.gr05_20181.lab2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    private final int splash_duration = 1500;
    //SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //prefs = getSharedPreferences("co.edu.udea.compumovil.gr05_20181.lab2", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = null;
                if(0 == 0) {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
                finish();
            };
        }, splash_duration);
    }
}