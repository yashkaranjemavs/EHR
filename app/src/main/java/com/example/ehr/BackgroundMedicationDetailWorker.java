package com.example.ehr;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BackgroundMedicationDetailWorker extends AsyncTask<String, Void, ArrayList<JSONObject>> {
    PharmacyActivity pharmacyActivity;
    String actionType;

    public BackgroundMedicationDetailWorker(PharmacyActivity pharmacyActivity) {
        this.pharmacyActivity = pharmacyActivity;
    }

    @Override
    protected ArrayList<JSONObject> doInBackground(String... params) {
        actionType = params[0];
        String baseUrl = "https://yxk8995.uta.cloud";

        try {
            if (actionType.equalsIgnoreCase("showMedicationDetails")) {
                StringBuilder urlString = new StringBuilder("");
                String emailid = params[1];
                String onlyPending = params[2];
                urlString.append(baseUrl).append("/getMedicationDetails.php").append("?").append("emailid").append("=").append(emailid).append("&").append("onlyPending").append("=").append(onlyPending);
                return handleRequest(urlString.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<JSONObject> listOfRecords) {
        this.pharmacyActivity.setAdapter(listOfRecords);
    }

    private ArrayList<JSONObject> handleRequest(String urlString) {
        StringBuilder result = new StringBuilder();
        ArrayList<JSONObject> listOfRecords = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),
                        StandardCharsets.ISO_8859_1));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line+"\n");
                }
                bufferedReader.close();
            }else{
                Toast.makeText(pharmacyActivity, "ERROR with GET call", Toast.LENGTH_LONG).show();
            }

            String[] seperateJson = result.toString().split("\n");
            for(int i=0;i<seperateJson.length;i++){
                listOfRecords.add(new JSONObject(seperateJson[i]));
            }
            return listOfRecords;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
