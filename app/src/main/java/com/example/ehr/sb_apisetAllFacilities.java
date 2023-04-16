package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface sb_apisetAllFacilities
{
    @FormUrlEncoded
    @POST("sb_getAllFacilities.php")
    Call<List<sb_ResponseModel_AllFacilities>> getData(
            @Field("type") String type
    );
}
