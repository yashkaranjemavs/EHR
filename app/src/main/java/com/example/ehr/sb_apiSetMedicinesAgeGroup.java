package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface sb_apiSetMedicinesAgeGroup {
    /*
    @GET("sb_getMedicinesByAgeGroup.php")
    Call<List<sb_responsemodel_medicinesagegroup>> getData(@Path("agegroup") String agegroup); */

    @FormUrlEncoded
    @POST("sb_getMedicinesByAgeGroup.php")
    Call<List<sb_responsemodel_medicinesagegroup>> getData(
            @Field("age1") String age1,
            @Field("age2") String age2
    );
}
