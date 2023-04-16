package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiController
{
    String url="https://ssb4235.uta.cloud/";
    private static sb_apiController clientobject;
    private static Retrofit retrofit;

    sb_apiController()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiController getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiController();
        }
        return clientobject;
    }
    sb_apiset getApi()
    {
        return retrofit.create(sb_apiset.class);
    }
}
