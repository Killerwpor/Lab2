package co.edu.udea.compumovil.gr05_20181.lab2.data.remote;

import co.edu.udea.compumovil.gr05_20181.lab2.data.remote.APIService;
import co.edu.udea.compumovil.gr05_20181.lab2.data.remote.RetrofitClient;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.194.3:3000/api/Usuarios/"; //ojo, esto puede dar problemas despues revisar bien

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}