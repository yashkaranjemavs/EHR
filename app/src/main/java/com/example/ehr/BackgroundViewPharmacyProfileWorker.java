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

    public BackgroundViewPharmacyProfileWorker(ViewPharmacyProfileActivity viewPharmacyProfileActivity) {
        this.viewPharmacyProfileActivity = viewPharmacyProfileActivity;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String actionType = params[0];
        String baseUrl = "https://yxk8995.uta.cloud";

        try {
            if (actionType.equalsIgnoreCase("viewPharmacyProfile")) {
                StringBuilder urlString = new StringBuilder("");
                String emailid = params[1];
                urlString.append(baseUrl).append("/getPharmacyProfile.php").append("?").append("emailid").append("=").append(emailid);
                return handleRequest(urlString.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
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
            this.viewPharmacyProfileActivity.handleUI(pharmacyUserModel);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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
