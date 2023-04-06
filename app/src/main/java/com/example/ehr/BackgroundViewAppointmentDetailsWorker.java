package com.example.ehr;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BackgroundViewAppointmentDetailsWorker extends AsyncTask<String, Void, String> {
    sr_PatientAppointmentsActivity patientAppointmentsActivity;

    String actionType;

    public BackgroundViewAppointmentDetailsWorker(sr_PatientAppointmentsActivity patientAppointmentsActivity) {
        this.patientAppointmentsActivity = patientAppointmentsActivity;
    }

    @Override
    protected String doInBackground(String...params) {
        actionType = params[0];
        String baseUrl = BaseUrl.baseUrl;
        try {
            if (actionType.equalsIgnoreCase("showAppointmentDetails")) {
                String patientid = params[1];
                String urlString = baseUrl + "/getAppointmentDetails.php?patientid=" + patientid;

                URL url = new URL(urlString);

                return handleRequest(url, "GET", null);
            } else if (actionType.equalsIgnoreCase("cancelAppointment")) {
                String visitid = params[1];
                String urlString = baseUrl + "/cancelAppointment.php";

                URL url = new URL(urlString);

                String post_data = URLEncoder.encode("visitid", "UTF-8") + "=" + URLEncoder.encode(visitid, "UTF-8");
                return handleRequest(url, "POST", post_data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String resultString) {
        try {
            if (actionType.equals("showAppointmentDetails")) {
                JSONArray resultArr = new JSONArray(resultString);


                List<PatientAppointmentModel> bookedAppointments = new ArrayList<>();
                List<PatientAppointmentModel> otherAppointments = new ArrayList<>();

                for (int i=0; i<resultArr.length(); i++) {
                    JSONObject appObj = (JSONObject) resultArr.get(i);
                    String patientId = appObj.getString("patientid");
                    String visitId = appObj.getString("visitid");
                    String vdate = appObj.getString("vdate");
                    String vtime = appObj.getString("vtime");
                    String patientNotes = appObj.getString("patientnotes");
                    String providerNotes = appObj.getString("providernotes");
                    String symptoms = appObj.getString("symptoms");
                    String diagnosis = appObj.getString("diagnosis");
                    String status = appObj.getString("status");

                    PatientAppointmentModel appointment = new PatientAppointmentModel(patientId,visitId,vdate, vtime, patientNotes,providerNotes, symptoms, diagnosis, status);
                    if(status.equalsIgnoreCase("booked")) {
                        bookedAppointments.add(appointment);
                    } else {
                        otherAppointments.add(appointment);
                    }
                }

                bookedAppointments.addAll(otherAppointments);
                List<PatientAppointmentModel> appointments = new ArrayList<>(bookedAppointments);
                this.patientAppointmentsActivity.setAdapter(appointments);
            } else if (actionType.equals("cancelAppointment")) {
                JSONObject resultObj = new JSONObject(resultString);

                if (resultObj.has("error")) {

                    Toast.makeText(patientAppointmentsActivity, resultObj.getString("error"), Toast.LENGTH_SHORT).show();
                    return;
                }

                patientAppointmentsActivity.appointmentCancelled(resultObj.getString("success"));
            }
        } catch (JSONException e) {
            Toast.makeText(patientAppointmentsActivity, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    private String handleRequest(URL url, String method, String postData) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoInput(true);


            if (method == "POST") {
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                        StandardCharsets.UTF_8));


                bufferedWriter.write(postData);
                bufferedWriter.close();
                outputStream.close();
            }

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                    StandardCharsets.ISO_8859_1));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
