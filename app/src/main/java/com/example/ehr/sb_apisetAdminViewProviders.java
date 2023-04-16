package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface sb_apisetAdminViewProviders {
    @GET("sb_getallproviders.php")
    Call<List<sb_ResponseModel_AllProviders>> getData();
}
