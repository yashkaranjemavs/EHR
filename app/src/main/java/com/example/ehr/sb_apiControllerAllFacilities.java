package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiControllerAllFacilities {


    String url="https://ssb4235.uta.cloud/";
    private static sb_apiControllerAllFacilities clientobject;
    private static Retrofit retrofit;

    sb_apiControllerAllFacilities()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiControllerAllFacilities getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiControllerAllFacilities();
        }
        return clientobject;
    }
    sb_apisetAllFacilities getApi()
    {
        return retrofit.create(sb_apisetAllFacilities.class);
    }
}
