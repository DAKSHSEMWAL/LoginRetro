package com.example.daksh.testlogin;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface loginapi {

        @FormUrlEncoded
        @POST("login")
        Call<User> post(
                @Field("email") String name,
                @Field("password") String password,
                @Field("device") String device,
                @Field("login_type")String login_type
        );

}
