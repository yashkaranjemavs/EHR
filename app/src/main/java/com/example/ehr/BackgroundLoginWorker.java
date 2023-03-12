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

public class BackgroundLoginWorker extends AsyncTask<String, Void, JSONObject> {
    LoginFragment loginFragment;
    String actionType;

    BackgroundLoginWorker(LoginFragment fragment) {
        this.loginFragment = fragment;
    }


    @Override
    protected JSONObject doInBackground(String... params) {
        actionType = params[0];
        String baseUrl = "https://pxs9233.uta.cloud";

        try {
            if (actionType.equals("login")) {
                String urlString = baseUrl + "/login.php";

                String email = params[1];
                String password = params[2];
                String role = params[3];

                URL url = new URL(urlString);

                return handleLoginRequest(url, email, password, role);
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
        if (actionType.equals("login")) {
            handleLoginResponse(resultObj);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private JSONObject handleLoginRequest(URL url, String email, String password, String role) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                    StandardCharsets.UTF_8));
            String post_data = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8") + "&" +
                    URLEncoder.encode("role", "UTF-8") + "=" +
                    URLEncoder.encode(role, "UTF-8");
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

    private void handleLoginResponse(JSONObject resultObj) {
        try {
            if (resultObj.has("error")) {
                this.loginFragment.onLoginFailed(resultObj.getString("error"));
                return;
            }
            String emailid = resultObj.getString("emailid");
            String userid = resultObj.getString("id");
            String role = resultObj.getString("role");
            UserModel user = new UserModel(emailid,userid, role);

            this.loginFragment.onLoginSuccess(user);
        } catch (JSONException e) {
            this.loginFragment.onLoginFailed("Something went wrong");
        }
    }
}
