package com.example.daksh.testlogin;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Communicator {
    private static  final String TAG = "Communicator";
    public static final String BASE_URL = "http://qualitypatrol.com/api/";

    public void loginPost(String username, String password){

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        loginapi service = retrofit.create(loginapi.class);

        Call<User> call = service.post(username,password,"1","2");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e(TAG,"Success");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
            }
        });
    }
}
