package com.example.ehr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface sb_apisetAppointmentList {
    @GET("sb_getAppointmentList.php")
    Call<List<sb_ResponseModel_Appointments>> getData();
}
