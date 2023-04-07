package com.example.ehr;

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
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;




public class BackgroundViewPatientWorker extends AsyncTask<Object, Void, JSONObject> {

     sr_PatientProfileActivity sr_patientProfileActivity;
     PatientUserModel patient;
     String actionType;

     public BackgroundViewPatientWorker(sr_PatientProfileActivity sr_patientProfileActivity) {
          this.sr_patientProfileActivity = sr_patientProfileActivity;
     }

     @Override
     protected JSONObject doInBackground(Object... params) {
          actionType = (String) params[0];
          String baseUrl = BaseUrl.baseUrl;

          try {
               if (actionType.equalsIgnoreCase("patientProfile")) {
                    String patientid = (String) params[1];
                    String urlString = baseUrl+ "/getPatient.php?patientid=" + patientid;

                    return handleGetRequest(urlString);
               } else if (actionType.equalsIgnoreCase("updatePatientProfile")) {
                    String urlString = baseUrl + "/updatePatientProfile.php";

                    patient = (PatientUserModel) params[1];
                    String postData = URLEncoder.encode("patientid", "UTF-8") + "=" + URLEncoder.encode(patient.getPatientId(), "UTF-8") + "&" +
                            URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(patient.getFirstname(), "UTF-8") + "&" +
                            URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(patient.getLastname(), "UTF-8") + "&" +
                            URLEncoder.encode("emailid", "UTF-8") + "=" + URLEncoder.encode(patient.getEmailId(), "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(patient.getPassword(), "UTF-8") + "&" +
                            URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(patient.getGender(), "UTF-8") + "&" +
                            URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(patient.getDob(), "UTF-8") + "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(patient.getContact(), "UTF-8") + "&" +
                            URLEncoder.encode("addressline1", "UTF-8") + "=" + URLEncoder.encode(patient.getAddress1(), "UTF-8") + "&" +
                            URLEncoder.encode("addressline2", "UTF-8") + "=" + URLEncoder.encode(patient.getAddress2(), "UTF-8") + "&" +
                            URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(patient.getCity(), "UTF-8") + "&" +
                            URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(patient.getState(), "UTF-8") + "&" +
                            URLEncoder.encode("zip", "UTF-8") + "=" + URLEncoder.encode(patient.getZip(), "UTF-8");

                    return handlePostRequest(urlString, postData);
               }
          } catch (Exception ex) {
               ex.printStackTrace();
          }
          return null;
     }

     @Override
     protected void onPostExecute(JSONObject jsonObject) {
          if (jsonObject != null) {
               if (actionType.equalsIgnoreCase("patientProfile")) {
                    try {
                         String patientId = (String) jsonObject.get("patientid");
                         String emailId = (String) jsonObject.get("emailid");
                         String password = (String) jsonObject.get("password");
                         String firstname = (String) jsonObject.get("firstname");
                         String lastname = (String) jsonObject.get("lastname");
                         String gender = (String) jsonObject.get("gender");
                         String dob = (String) jsonObject.get("dob");
                         String contact = (String) jsonObject.get("contact");
                         String addressline1 = (String) jsonObject.get("addressline1");
                         String addressline2 = (String) jsonObject.get("addressline2");
                         String city = (String) jsonObject.get("city");
                         String state = (String) jsonObject.get("state");
                         String zip = (String) jsonObject.get("zip");

                         PatientUserModel patientUserModel = new PatientUserModel(patientId, emailId, password, null, firstname, lastname, gender, dob, contact, addressline1, addressline2, city, state, zip);
                         this.sr_patientProfileActivity.handleUI(patientUserModel, actionType);
                    } catch (JSONException e) {
                         throw new RuntimeException(e);
                    }
               } else if (actionType.equalsIgnoreCase("updatePatientProfile")) {
                    if (jsonObject.has("success")) {
                         Toast.makeText(sr_patientProfileActivity, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else if (jsonObject.has("error")) {
                         Toast.makeText(sr_patientProfileActivity, "ERROR: Patient Profile Update", Toast.LENGTH_SHORT).show();
                    } else {
                         Toast.makeText(sr_patientProfileActivity, "JSON Not Recognized", Toast.LENGTH_SHORT).show();
                    }
                    this.sr_patientProfileActivity.handleUI(patient, actionType);
               }
          } else {
               Toast.makeText(sr_patientProfileActivity, "jsonObject null in patient background worker", Toast.LENGTH_SHORT).show();
          }
     }

     private JSONObject handlePostRequest(String urlString, String postData) {
          try {
               URL url = new URL(urlString);
               HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
               httpURLConnection.setRequestMethod("POST");
               httpURLConnection.setDoOutput(true);
               httpURLConnection.setDoInput(true);
               OutputStream outputStream = httpURLConnection.getOutputStream();
               BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

               bufferedWriter.write(postData);
               bufferedWriter.flush();
               bufferedWriter.close();
               outputStream.close();

               InputStream inputStream = httpURLConnection.getInputStream();
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
               StringBuilder result = new StringBuilder("");
               String line = "";
               while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
               }
               bufferedReader.close();
               inputStream.close();
               httpURLConnection.disconnect();
               return new JSONObject(result.toString());
          } catch (IOException ex) {
               throw new RuntimeException(ex);
          } catch (Exception ex) {
               ex.printStackTrace();
          }
          return null;
     }

     private JSONObject handleGetRequest(String urlstring) {
          StringBuilder result = new StringBuilder();
          try {
               URL url = new URL(urlstring);
               HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

               httpURLConnection.setRequestMethod("GET");
               int responseCode = httpURLConnection.getResponseCode();
               if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),
                            StandardCharsets.ISO_8859_1));
                    result = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                         result.append(line);
                    }
                    bufferedReader.close();
               } else {
                    Toast.makeText(sr_patientProfileActivity, "ERROR with GET call", Toast.LENGTH_SHORT).show();
               }

               return new JSONObject(result.toString());
          } catch (IOException ex) {
               throw new RuntimeException(ex);
          } catch (Exception ex) {
               ex.printStackTrace();
          }
          return null;
     }
}








