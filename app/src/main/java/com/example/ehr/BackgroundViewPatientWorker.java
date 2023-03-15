package com.example.ehr;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;




public class BackgroundViewPatientWorker extends AsyncTask<Object, Void, JSONObject> {
     sr_PatientProfileActivity sr_patientProfileActivity;

     public BackgroundViewPatientWorker(sr_PatientProfileActivity sr_patientProfileActivity) {
          this.sr_patientProfileActivity = sr_patientProfileActivity;
     }

     @Override
     protected JSONObject doInBackground(Object... params) {
          String actionType = (String) params[0];
          String baseUrl = "https://sxr4177.uta.cloud";

          try {
               if (actionType.equalsIgnoreCase("patientProfile")) {
                    String urlString = baseUrl + "/getPatient.php";
                    URL url = new URL(urlString);

                    String id = (String) params[1];

                    String postData = URLEncoder.encode("patientid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    return handleRequest(url, postData);
               }
          } catch (Exception ex) {
               ex.printStackTrace();
          }
          return null;
     }

     @Override
     protected void onPostExecute(JSONObject jsonObject) {
          try {
               String patientid = jsonObject.getString("patientid");
               String emailId = jsonObject.getString("emailid");
               String firstname = jsonObject.getString("firstname");
               String lastname = jsonObject.getString("lastname");
               String gender = jsonObject.getString("gender");
               String dob = jsonObject.getString("dob");
               String contact = jsonObject.getString("contact");
               String addressline1 = jsonObject.getString("addressline1");
               String addressline2 = jsonObject.getString("addressline2");
               String city = jsonObject.getString("city");
               String state = jsonObject.getString("state");
               String zip = jsonObject.getString("zip");

               PatientUserModel patientUserModel = new PatientUserModel(emailId, patientid, firstname, lastname, gender, dob, contact, addressline1, addressline2, city, state, zip);
               this.sr_patientProfileActivity.handleUI(patientUserModel);
          } catch (JSONException e) {
               throw new RuntimeException(e);
          }
     }

     private JSONObject handleRequest(URL url, String postData) {
          try {
               HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

               httpURLConnection.setRequestMethod("POST");
               httpURLConnection.setDoOutput(true);
               httpURLConnection.setDoInput(true);

               OutputStream outputStream = httpURLConnection.getOutputStream();
               BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                       StandardCharsets.UTF_8));


               bufferedWriter.write(postData);
               bufferedWriter.close();
               outputStream.close();

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

               System.out.println(result.toString());

               return new JSONObject(result.toString());
          } catch (IOException ex) {
               throw new RuntimeException(ex);
          } catch (Exception ex) {
               ex.printStackTrace();
          }

          return null;
     }
}