package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiControllerDiagnosisagegroup
{
    String url="https://ssb4235.uta.cloud/";
    private static sb_apiControllerDiagnosisagegroup clientobject;
    private static Retrofit retrofit;

    sb_apiControllerDiagnosisagegroup()
    {
        //Gson gson= new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiControllerDiagnosisagegroup getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiControllerDiagnosisagegroup();
        }
        return clientobject;
    }
    sb_apisetDiagnosisAgeGroup getApi()
    {
        return retrofit.create(sb_apisetDiagnosisAgeGroup.class);
    }
}

