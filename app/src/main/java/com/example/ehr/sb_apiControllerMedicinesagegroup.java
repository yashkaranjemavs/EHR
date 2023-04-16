package com.example.ehr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sb_apiControllerMedicinesagegroup
{
    String url="https://ssb4235.uta.cloud/";
    private static sb_apiControllerMedicinesagegroup clientobject;
    private static Retrofit retrofit;

    sb_apiControllerMedicinesagegroup()
    {
        //Gson gson= new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized sb_apiControllerMedicinesagegroup getInstance()
    {
        if(clientobject==null)
        {
            clientobject=new sb_apiControllerMedicinesagegroup();
        }
        return clientobject;
    }
    sb_apiSetMedicinesAgeGroup getApi()
    {
        return retrofit.create(sb_apiSetMedicinesAgeGroup.class);
    }
}
