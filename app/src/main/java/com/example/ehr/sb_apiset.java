package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface sb_apiset
{
    @GET("sb_getschedule.php")
    Call<List<sb_ResponseModel_Schedule>> getData();
}
