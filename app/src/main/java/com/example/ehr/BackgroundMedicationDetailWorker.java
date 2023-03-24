package com.example.ehr;

import android.os.AsyncTask;
import android.widget.Toast;

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
            }else if(actionType.equalsIgnoreCase("updateMedicationStatus")){
                StringBuilder urlString = new StringBuilder("");
                urlString.append(baseUrl).append("/updateMedicationStatus.php");
                return handleUpdateRequest(urlString.toString(), params[1], params[2]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private ArrayList<JSONObject> handleUpdateRequest(String urlString, String medicationid, String status) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);    //write output to response
            httpURLConnection.setDoInput(true); //read input from request
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_data = URLEncoder.encode("medicationid", "UTF-8") + "=" + URLEncoder.encode(medicationid, "UTF-8") + "&" +
                    URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");

            bufferedWriter.write(post_data);    //send request
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();   //receive response
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder result = new StringBuilder("");
            String line = "";
            while((line = bufferedReader.readLine())!=null){
                result.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
//            result.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<JSONObject> listOfRecords) {
        if(listOfRecords!=null){
            this.pharmacyActivity.setAdapter(listOfRecords);
        }
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
