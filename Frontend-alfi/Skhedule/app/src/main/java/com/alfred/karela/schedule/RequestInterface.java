package com.alfred.karela.schedule;

/**
 * Created by Alfred on 06-03-2017.
 */

import retrofit2.Call;
import retrofit2.http.GET;


public interface RequestInterface {
    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}
