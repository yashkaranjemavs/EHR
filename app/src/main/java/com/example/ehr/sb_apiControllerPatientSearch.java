package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiControllerPatientSearch
{
    String url=BaseUrl.baseUrl;
    private static sb_apiControllerPatientSearch clientobject;
    private static Retrofit retrofit;

    sb_apiControllerPatientSearch()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiControllerPatientSearch getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiControllerPatientSearch();
        }
        return clientobject;
    }
    sb_apisetPatientSearch getApi()
    {
        return retrofit.create(sb_apisetPatientSearch.class);
    }

}
