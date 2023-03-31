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

public class BackgroundLaboratoryPendingTestsWorker extends AsyncTask<Object, Void, String> {
    LaboratoryFragment testFragment;
    String actionType;
    LaboratoryPendingTestsModel testsModel;

    public BackgroundLaboratoryPendingTestsWorker(LaboratoryFragment testFragment) {
        this.testFragment = testFragment;
    }

    @Override
    protected String doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = "https://pxs9233.uta.cloud";

        try {
            //Viewing Laboratory Profile
            if (actionType.equals("get_test")) {
                String urlString = baseUrl + "/ps_getLaboratoryPendingTests.php";
                URL url = new URL(urlString);

                String id = (String) params[1];
                String postData = URLEncoder.encode("laboratoryid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                return handlePostRequest(url, postData);
            }

            else if (actionType.equals("add_test")) {
                String urlString = baseUrl + "/ps_updateLaboratoryTests.php";
                URL url = new URL(urlString);

                testsModel = (LaboratoryPendingTestsModel) params[1];
                String testreport = testsModel.getTestreport();
                String visitid = testsModel.getVisitid();
                String testid = testsModel.getTestid();
                String laboratoryid = testsModel.getLaboratoryid();

                String postData = URLEncoder.encode("testreport", "UTF-8") + "=" + URLEncoder.encode(testreport, "UTF-8") + "&" +
              URLEncoder.encode("visitid", "UTF-8") + "=" + URLEncoder.encode(visitid, "UTF-8") + "&" +
              URLEncoder.encode("testid", "UTF-8") + "=" + URLEncoder.encode(testid, "UTF-8") + "&" +
                        URLEncoder.encode("laboratoryid", "UTF-8") + "=" + URLEncoder.encode(laboratoryid, "UTF-8");

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
            if (actionType.equals("get_test")) {
                JSONArray resultArr = new JSONArray(resultString);

                List<LaboratoryPendingTestsModel> testList = new ArrayList<>();

                for (int i = 0; i < resultArr.length(); i++) {
                    JSONObject testfields = (JSONObject) resultArr.get(i);

                    String testname = testfields.getString("testname");
                    String firstname = testfields.getString("firstname");
                    String lastname = testfields.getString("lastname");
                    String testreport = testfields.getString("testreport");
                    String laboratoryid = testfields.getString("laboratoryid");
                    String visitid = testfields.getString("visitid");
                    String testid = testfields.getString("testid");

                    LaboratoryPendingTestsModel test = new LaboratoryPendingTestsModel(testname, firstname, lastname, testreport, laboratoryid, visitid, testid);
                    testList.add(test);
                }

                this.testFragment.onLoadSuccess(testList);
            }
                else if (actionType.equals("add_test")) {
                    JSONObject resultObj = new JSONObject(resultString);

                    if (resultObj.has("error")) {
                        this.testFragment.onUpdate(resultObj.getString("error"));
                        return;
                    }

                    this.testFragment.onUpdate("");
            }
        } catch (JSONException e) {

            this.testFragment.onFailed("Something went wrong");
        }

    }
}
