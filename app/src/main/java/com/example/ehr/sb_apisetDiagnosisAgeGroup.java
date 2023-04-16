package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface sb_apisetDiagnosisAgeGroup
{
    @FormUrlEncoded
    @POST("sb_getDiagnosisByAgeGroup.php")
    Call<List<sb_ResponseModel_Diagnosisagegroup>> getData(
            @Field("age1") String age1,
            @Field("age2") String age2
    );
}
