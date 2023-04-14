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
    LaboratoryPendingTestsFragment testsFragment;
    String actionType;
    LaboratoryPendingTestsModel testsModel;
    public BackgroundLaboratoryPendingTestsWorker(LaboratoryPendingTestsFragment testsFragment) {
        this.testsFragment = testsFragment;
    }

    @Override
    protected String doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = BaseUrl.baseUrl;

        try {
            //Viewing Laboratory Tests
            if (actionType.equals("get_tests")) {
                String urlString = baseUrl + "/ps_getLaboratoryTests.php";
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
            if (actionType.equals("get_tests"))
            {
                JSONArray resultArr = new JSONArray(resultString);

                List<LaboratoryPendingTestsModel> testsList = new ArrayList<>();

                for (int i = 0; i < resultArr.length(); i++) {
                    JSONObject alltestfields = (JSONObject) resultArr.get(i);

                    String testname = alltestfields.getString("testname");
                    String testid = alltestfields.getString("testid");
                    String firstname = alltestfields.getString("firstname");
                    String lastname = alltestfields.getString("lastname");
                    String gender = alltestfields.getString("gender");
                    String dob = alltestfields.getString("dob");
                    String city = alltestfields.getString("city");
                    String state = alltestfields.getString("state");
                    String zip = alltestfields.getString("zip");
                    String contact = alltestfields.getString("contact");
                    String emailid = alltestfields.getString("emailid");
                    String address1 = alltestfields.getString("address1");
                    String address2 = alltestfields.getString("address2");
                    String patientid = alltestfields.getString("patientid");
                    String laboratoryid = alltestfields.getString("laboratoryid");
                    String visitid = alltestfields.getString("visitid");
                    String tdate = alltestfields.getString("tdate");
                    String testreport = alltestfields.getString("testreport");

                    LaboratoryPendingTestsModel test = new LaboratoryPendingTestsModel(testname, testid, firstname, lastname, gender, dob, city, state, zip, contact, emailid, address1, address2, patientid, laboratoryid, visitid, tdate, testreport);
                    testsList.add(test);
                }

                this.testsFragment.onLoadSuccess(testsList);
            }
            else if (actionType.equals("add_test"))
            {
                JSONObject resultObj = new JSONObject(resultString);
                if (resultObj.has("error")) {
                    this.testsFragment.onUpdate(resultObj.getString("error"));
                    return;
                }
                this.testsFragment.onUpdate("");
            }
    }
    catch (JSONException e) {

            this.testsFragment.onLoadFailed("Something went wrong");
        }

    }
}
