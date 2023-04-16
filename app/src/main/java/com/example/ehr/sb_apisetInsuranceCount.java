package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface sb_apisetInsuranceCount {
    @GET("sb_getInsuranceCountList.php")
    Call<List<sb_ResponseModel_InsruanceCount>> getData();
}
