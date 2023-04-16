package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiControllerAllProviders {

    String url=BaseUrl.baseUrl;
    private static sb_apiControllerAllProviders clientobject;
    private static Retrofit retrofit;

    sb_apiControllerAllProviders()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiControllerAllProviders getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiControllerAllProviders();
        }
        return clientobject;
    }
    sb_apisetAdminViewProviders getApi()
    {
        return retrofit.create(sb_apisetAdminViewProviders.class);
    }
}
