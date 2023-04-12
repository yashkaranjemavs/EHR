package com.example.ehr;

import android.os.AsyncTask;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

public class BackgroundLaboratoryLandingTestsWorker extends AsyncTask<Object, Void, String> {
    LaboratoryLandingTestsFragment testsFragment;
    String actionType;

    public BackgroundLaboratoryLandingTestsWorker(LaboratoryLandingTestsFragment testsFragment) {
        this.testsFragment = testsFragment;
    }

    @Override
    protected String doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = BaseUrl.baseUrl;

        try {
            //Viewing Laboratory Tests
            if (actionType.equals("get_landing_tests")) {
                String urlString = baseUrl + "/ps_getLaboratorylandingTests.php";
                URL url = new URL(urlString);

                String id = (String) params[1];
                String postData = URLEncoder.encode("laboratoryid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                return handlePostRequest(url, postData);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String handlePostRequest(URL url, String postData) {
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

            return result.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String resultString) {
        handleResponse(resultString);
    }


    private void handleResponse(String resultString) {
        try {
            if (actionType.equals("get_landing_tests")) {
                JSONArray resultArr = new JSONArray(resultString);

                List<LaboratoryLandingTestsModel> testsList = new ArrayList<>();

                for (int i = 0; i < resultArr.length(); i++) {
                    JSONObject alltestfields = (JSONObject) resultArr.get(i);

                    String testname = alltestfields.getString("testname");
                    String testid = alltestfields.getString("testid");
                    String firstname = alltestfields.getString("firstname");
                    String lastname = alltestfields.getString("lastname");

                    LaboratoryLandingTestsModel test = new LaboratoryLandingTestsModel(testname, testid, firstname, lastname);
                    testsList.add(test);
                }

                this.testsFragment.onLoadSuccess(testsList);
            }
        } catch (JSONException e) {

            this.testsFragment.onLoadFailed("Something went wrong");
        }

    }
}