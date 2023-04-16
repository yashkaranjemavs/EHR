package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiControllerInusrancecount {

    String url="https://ssb4235.uta.cloud/";
    private static sb_apiControllerInusrancecount clientobject;
    private static Retrofit retrofit;

    sb_apiControllerInusrancecount()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiControllerInusrancecount getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiControllerInusrancecount();
        }
        return clientobject;
    }
    sb_apisetInsuranceCount getApi()
    {
        return retrofit.create(sb_apisetInsuranceCount.class);
    }
}
