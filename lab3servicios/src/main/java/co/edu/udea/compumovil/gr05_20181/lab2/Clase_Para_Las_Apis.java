package co.edu.udea.compumovil.gr05_20181.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.udea.compumovil.gr05_20181.lab2.data.Model;
import co.edu.udea.compumovil.gr05_20181.lab2.data.remote.APIService;
import co.edu.udea.compumovil.gr05_20181.lab2.data.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Clase_Para_Las_Apis extends AppCompatActivity {

    private APIService mAPIService;
    TextView texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase__para__las__apis);
        mAPIService = ApiUtils.getAPIService();
        Button boton=findViewById(R.id.boton);
      texto  =findViewById(R.id.label);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost("s","s");
            }
        });


    }

    public void sendPost(String title, String body) {
        mAPIService.savePost("Pedrito","Perez","a@hot.com","Perez","asdfasdfdf", 10).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                if(response.isSuccessful()) {
                    texto.setText("Funciono");
                   showResponse(response.body().toString());
                    texto.setText("Funciono");
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                texto.setText("Error");
            }
        });
    }
    public void showResponse(String response) {
               texto.setText(response);
    }


}
