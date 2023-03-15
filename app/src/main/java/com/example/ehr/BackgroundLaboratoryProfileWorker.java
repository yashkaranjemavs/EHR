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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundLaboratoryProfileWorker extends AsyncTask<Object, Void, JSONObject> {
    LaboratoryProfileFragment profileFragment;
    String actionType;
    LaboratoryUserModel lab;

    BackgroundLaboratoryProfileWorker(LaboratoryProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }


    @Override
    protected JSONObject doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = "https://pxs9233.uta.cloud";

        try {
            if (actionType.equals("get_profile")) {
                String urlString = baseUrl + "/ps_getLaboratoryProfile.php";
                URL url = new URL(urlString);

                String id = (String) params[1];
                String postData = URLEncoder.encode("laboratoryid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                return handleRequest(url, postData);
            }
/*            else if (actionType.equals("update_profile"))
            {
                String urlString = baseUrl + "/updateLaboratory.php";
                URL url = new URL(urlString);

                lab = (LaboratoryUserModel) params[1];
                String id = lab.getId().trim();
                String name = lab.getName().trim();
                String email = lab.getEmailId().trim();
                String password = lab.getPassword().trim();
                String contact = lab.getContact().trim();
                String address1 = lab.getAddress1().trim();
                String address2 = lab.getAddress2().trim();
                String city = lab.getCity().trim();
                String state = lab.getState().trim();
                String zip = lab.getZip().trim();

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
            }*/
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
                String laoratoryid = resultObj.getString("id");
                String emailId = resultObj.getString("email");
                String password = resultObj.getString("password");
                String name = resultObj.getString("name");
                String contact = resultObj.getString("contact");
                String address1 = resultObj.getString("address1");
                String address2 = resultObj.getString("address2");
                String city = resultObj.getString("city");
                String state = resultObj.getString("state");
                String zip = resultObj.getString("zip");
                LaboratoryUserModel lab = new LaboratoryUserModel(laoratoryid, emailId, password, name, contact, address1, address2, city, state, zip);

                this.profileFragment.handleUI(lab);
            }
            /*else if (actionType.equals("update_profile")) {
                if (resultObj.has("error")) {
                    this.profileFragment.onUpdate(resultObj.getString("error"), lab);
                    return;
                }

                this.profileFragment.onUpdate("", lab);
            }*/
        } catch (JSONException e) {
            if (actionType.equals("get_profile")) {
                this.profileFragment.onLoadFailed("Something went wrong");
            }
            /*else if (actionType.equals("update_profile"))
             {
                this.profileFragment.onUpdate("Something went wrong", lab);*/
        }
    }
}

