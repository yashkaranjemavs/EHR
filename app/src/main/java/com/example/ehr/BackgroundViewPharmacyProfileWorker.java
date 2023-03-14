package com.example.ehr;

import android.content.Context;
import android.os.AsyncTask;

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
    Context context;

    public BackgroundViewPharmacyProfileWorker(Context context) {
        context = context;
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
        System.out.println("onPost: " + jsonObject.toString());
        new ViewPharmacyProfileActivity().newMethod(jsonObject);
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
//                System.out.println(result.toString());
            }else{
                System.out.println("ERROR with GET call.");
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
