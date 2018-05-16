package co.edu.udea.compumovil.gr05_20181.lab2.data.remote;
import co.edu.udea.compumovil.gr05_20181.lab2.data.Model;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("/posts")
    @FormUrlEncoded
    Call<Model> savePost(

            @Field("nombre") String nombre,
            @Field("contrasena") String contrasena,
            @Field("correo") String correo,
            @Field("apellido") String apellido,
            @Field("foto") String foto,
            @Field("id") int id


    );
}