package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiControllerAppointmentList {
    String url="https://ssb4235.uta.cloud/";
    private static sb_apiControllerAppointmentList clientobject;
    private static Retrofit retrofit;

    sb_apiControllerAppointmentList()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiControllerAppointmentList getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiControllerAppointmentList();
        }
        return clientobject;
    }
    sb_apisetAppointmentList getApi()
    {
        return retrofit.create(sb_apisetAppointmentList.class);
    }
}
