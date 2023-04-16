package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface sb_apisetPatientSearch {
    @GET("sb_getPatientList.php")
    Call<List<sb_ResponseModel_PatientSearch>> getData();
}
