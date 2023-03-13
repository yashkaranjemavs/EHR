package com.example.ehr;

import android.os.AsyncTask;

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

public class BackgroundInsuranceProfileWorker extends AsyncTask<String, Void, JSONObject> {
    InsuranceProfileFragment profileFragment;

    BackgroundInsuranceProfileWorker(InsuranceProfileFragment fragment) {
        this.profileFragment = fragment;
    }


    @Override
    protected JSONObject doInBackground(String... params) {
        String actionType = params[0];
        String baseUrl = "https://kxp9181.uta.cloud";

        try {
            if (actionType.equals("get_profile")) {
                String urlString = baseUrl + "/getInsuranceCompany.php";

                String id = params[1];

                URL url = new URL(urlString);

                return handleProfileRequest(url, id);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(JSONObject resultObj) {
        handleProfileResponse(resultObj);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private JSONObject handleProfileRequest(URL url, String id) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                    StandardCharsets.UTF_8));
            String post_data = URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");
            bufferedWriter.write(post_data);
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

            return new JSONObject(result.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void handleProfileResponse(JSONObject resultObj) {
        try {
            if (resultObj.has("error")) {
                this.profileFragment.onFailed(resultObj.getString("error"));
                return;
            }

            String id = resultObj.getString("id");
            String emailId = resultObj.getString("emailid");
            String name = resultObj.getString("name");
            String contact = resultObj.getString("contact");
            String address1 = resultObj.getString("address1");
            String address2 = resultObj.getString("address2");
            String city = resultObj.getString("city");
            String state = resultObj.getString("state");
            String zip = resultObj.getString("zip");
            InsuranceCompanyModel insuranceCompany = new InsuranceCompanyModel(id,emailId, name, contact, address1, address2, city, state, zip);

            this.profileFragment.onSuccess(insuranceCompany);
        } catch (JSONException e) {
            this.profileFragment.onFailed("Something went wrong");
        }
    }
}
