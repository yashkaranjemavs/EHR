package com.example.ehr.insurance;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundInsuranceProfileWorker extends AsyncTask<Object, Void, JSONObject> {
    InsuranceProfileFragment profileFragment;
    String actionType;
    InsuranceCompanyModel insuranceCompany;

    BackgroundInsuranceProfileWorker(InsuranceProfileFragment fragment) {
        this.profileFragment = fragment;
    }


    @Override
    protected JSONObject doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = "https://kxp9181.uta.cloud";

        try {
            if (actionType.equals("get_profile")) {
                String urlString = baseUrl + "/getInsuranceCompany.php";
                URL url = new URL(urlString);

                String id = (String) params[1];
                String postData = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                return handleRequest(url, postData);
            } else if (actionType.equals("update_profile")) {
                String urlString = baseUrl + "/updateInsuranceCompany.php";
                URL url = new URL(urlString);

                insuranceCompany = (InsuranceCompanyModel) params[1];
                String id = insuranceCompany.getId().trim();
                String name = insuranceCompany.getName().trim();
                String email = insuranceCompany.getEmailId().trim();
                String password = insuranceCompany.getPassword().trim();
                String contact = insuranceCompany.getContact().trim();
                String address1 = insuranceCompany.getAddress1().trim();
                String address2 = insuranceCompany.getAddress2().trim();
                String city = insuranceCompany.getCity().trim();
                String state = insuranceCompany.getState().trim();
                String zip = insuranceCompany.getZip().trim();

                String postData =
                        URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                                URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                                URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                                URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +
                                URLEncoder.encode("address1", "UTF-8") + "=" + URLEncoder.encode(address1, "UTF-8") + "&" +
                                URLEncoder.encode("address2", "UTF-8") + "=" + URLEncoder.encode(address2, "UTF-8") + "&" +
                                URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") + "&" +
                                URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(state, "UTF-8") + "&" +
                                URLEncoder.encode("zip", "UTF-8") + "=" + URLEncoder.encode(zip, "UTF-8");

                return handleRequest(url, postData);
            }
        } catch (MalformedURLException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(JSONObject resultObj) {
        handleResponse(resultObj);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
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

            return new JSONObject(result.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void handleResponse(JSONObject resultObj) {
        try {
            if (actionType.equals("get_profile")) {
                if (resultObj.has("error")) {
                    this.profileFragment.onLoadFailed(resultObj.getString("error"));
                    return;
                }
                String id = resultObj.getString("id");
                String emailId = resultObj.getString("emailid");
                String password = resultObj.getString("password");
                String name = resultObj.getString("name");
                String contact = resultObj.getString("contact");
                String address1 = resultObj.getString("address1");
                String address2 = resultObj.getString("address2");
                String city = resultObj.getString("city");
                String state = resultObj.getString("state");
                String zip = resultObj.getString("zip");
                InsuranceCompanyModel insuranceCompany = new InsuranceCompanyModel(id, emailId, password, name, contact, address1, address2, city, state, zip);

                this.profileFragment.onLoadSuccess(insuranceCompany);
            } else if (actionType.equals("update_profile")) {
                if (resultObj.has("error")) {
                    this.profileFragment.onUpdate(resultObj.getString("error"), insuranceCompany);
                    return;
                }

                this.profileFragment.onUpdate("", insuranceCompany);
            }
        } catch (JSONException e) {
            if (actionType.equals("get_profile")) {
                this.profileFragment.onLoadFailed("Something went wrong");
            } else if (actionType.equals("update_profile")) {
                this.profileFragment.onUpdate("Something went wrong", insuranceCompany);
            }
        }
    }
}
