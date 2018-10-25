package com.example.daksh.testlogin;


import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Communicator communicator;
    private EditText usernameET, passwordET;
    private String username, password;
    private Button loginButtonPost, loginButtonGet;
    private final static String TAG = "MainActivity";
    public static Bus bus;
    String message, error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        communicator = new Communicator();

        usernameET = (EditText) findViewById(R.id.usernameInput);
        passwordET = (EditText) findViewById(R.id.passwordInput);
        //This is used to hide the password's EditText characters. So we can avoid the different hint font.
        passwordET.setTransformationMethod(new PasswordTransformationMethod());

        loginButtonPost = (Button) findViewById(R.id.loginButtonPost);
        loginButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();
                loginPost(username, password);
            }
        });


    }

    public static final String BASE_URL = "http://qualitypatrol.com/api/";

    public void loginPost(final String username, final String password) {

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

        Call<User> call = service.post(username, password, "1", "2");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                if (!response.body().getStatus())
                {
                    message = response.body().getMessage();
                    if (message.equals("Details Not Matched")&&username.isEmpty())
                    {
                        usernameET.setError(response.body().getError().getEmail());
                        error = response.body().getError().getEmail();
                        showMessage(error);
                    }
                    if (message.equals("Details Not Matched")&&password.isEmpty()) {
                        passwordET.setError(response.body().getError().getPassword());
                        error = response.body().getError().getPassword();
                        showMessage(error);
                    }
                    if(message.equals("Details Not Matched")&&username.isEmpty()&&password.isEmpty())
                    {
                            error=response.body().getError().getEmail()+"\n"+response.body().getError().getPassword();
                            usernameET.setError(response.body().getError().getEmail());
                            passwordET.setError(response.body().getError().getPassword());
                            showMessage(error);
                    }

                    if(message.equals("User Not Found"))
                    {
                        usernameET.setError("User Does Not Exist");
                        showMessage(message);
                    }
                    if(message.equals("Password Not Found"))
                    {
                        passwordET.setError("Wrong Password");
                        showMessage("Wrong Password");
                    }
                }
                else
                {
                    message="Welcome! "+response.body().getData().getName();
                    showMessage(message);
                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void showMessage(String message) {
        Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
    }
}




