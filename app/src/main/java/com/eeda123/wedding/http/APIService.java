package com.eeda123.wedding.http;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;


public interface APIService {

    @GET("/app/msg/getMemberMsg")
    Call<HashMap<String,Object>> getMemberMsg();
}
