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

public class BackgroundViewPharmacyProfileWorker extends AsyncTask<String, Void, JSONObject> {
    ViewPharmacyProfileActivity viewPharmacyProfileActivity;
    Context context;
    String actionType;

    public BackgroundViewPharmacyProfileWorker(ViewPharmacyProfileActivity viewPharmacyProfileActivity) {
        this.viewPharmacyProfileActivity = viewPharmacyProfileActivity;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        actionType = params[0];
        String baseUrl = "https://yxk8995.uta.cloud";

        try {
            if (actionType.equalsIgnoreCase("viewPharmacyProfile")) {
                StringBuilder urlString = new StringBuilder("");
                String emailid = params[1];
                urlString.append(baseUrl).append("/getPharmacyProfile.php").append("?").append("emailid").append("=").append(emailid);
                return handleRequest(urlString.toString());
            }else if(actionType.equalsIgnoreCase("updatePharmacyProfile")){
                StringBuilder urlString = new StringBuilder("");
                urlString.append(baseUrl).append("/updatePharmacyProfile.php");
                return handleUpdateRequest(urlString.toString(), params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(jsonObject!=null){
            if (actionType.equalsIgnoreCase("viewPharmacyProfile")) {
                try {
                    String emailId = (String) jsonObject.get("emailid");
                    String name = (String) jsonObject.get("name");
                    String contact = (String) jsonObject.get("contact");
                    String addressline1 = (String) jsonObject.get("addressline1");
                    String addressline2 = (String) jsonObject.get("addressline2");
                    String city = (String) jsonObject.get("city");
                    String state = (String) jsonObject.get("state");
                    String zip = (String) jsonObject.get("zip");

                    PharmacyUserModel pharmacyUserModel = new PharmacyUserModel(emailId, name, contact, addressline1, addressline2, city, state, zip);
                    this.viewPharmacyProfileActivity.handleUI(pharmacyUserModel, actionType);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else if(actionType.equalsIgnoreCase("updatePharmacyProfile")){
                if(jsonObject.has("success")){
                    Toast.makeText(viewPharmacyProfileActivity, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                }else if(jsonObject.has("error")){
                    Toast.makeText(viewPharmacyProfileActivity, "ERROR: Pharmacy Profile Update", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(viewPharmacyProfileActivity, "JSON Not Recognized", Toast.LENGTH_SHORT).show();
                }
                this.viewPharmacyProfileActivity.handleUI(null, actionType);
            }
        }else{
            Toast.makeText(viewPharmacyProfileActivity, "jsonObject null in pharmacy background worker", Toast.LENGTH_SHORT).show();
        }
    }

    private JSONObject handleUpdateRequest(String urlString, String emailid, String addressline1, String addressline2, String contact, String city, String state, String zip) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);    //write output to response
            httpURLConnection.setDoInput(true); //read input from request
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_data = URLEncoder.encode("emailid", "UTF-8") + "=" + URLEncoder.encode(emailid, "UTF-8") + "&" +
                    URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +
                    URLEncoder.encode("addressline1", "UTF-8") + "=" + URLEncoder.encode(addressline1, "UTF-8") + "&" +
                    URLEncoder.encode("addressline2", "UTF-8") + "=" + URLEncoder.encode(addressline2, "UTF-8") + "&" +
                    URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") + "&" +
                    URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(state, "UTF-8") + "&" +
                    URLEncoder.encode("zip", "UTF-8") + "=" + URLEncoder.encode(zip, "UTF-8");

            bufferedWriter.write(post_data);    //send request
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();   //receive response
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder result = new StringBuilder("");
            String line = "";
            while((line = bufferedReader.readLine())!=null){
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

    private JSONObject handleRequest(String urlString) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),
                        StandardCharsets.ISO_8859_1));
                result = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
                bufferedReader.close();
            }else{
                Toast.makeText(viewPharmacyProfileActivity, "ERROR with GET call", Toast.LENGTH_SHORT).show();
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
