package com.example.ehr.insurance.worker;

import android.os.AsyncTask;

import com.example.ehr.BaseUrl;
import com.example.ehr.insurance.InsuranceCoverageFragment;
import com.example.ehr.insurance.model.InsuranceCoverageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BackgroundInsuranceCoverageWorker extends AsyncTask<Object, Void, String> {

    InsuranceCoverageFragment insuranceCoverageFragment;

    String actionType;
    public BackgroundInsuranceCoverageWorker(InsuranceCoverageFragment fragment) {
        this.insuranceCoverageFragment = fragment;
    }

    @Override
    protected String doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = BaseUrl.baseUrl;

        try {
            if (actionType.equals("get_coverages")) {
                String id = (String) params[1];

                String urlString = baseUrl + "/getInsuranceCoverage.php?id=" + id;
                URL url = new URL(urlString);

                return WorkerHelper.handleGetRequest(url);
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
    protected void onPostExecute(String resultString) {
        handleResponse(resultString);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private void handleResponse(String resultString) {
        try {
            if (actionType.equals("get_coverages")) {
                JSONArray resultArr = new JSONArray(resultString);

                List<InsuranceCoverageModel> coverages = new ArrayList<>();

                for (int i=0; i<resultArr.length(); i++) {
                    JSONObject coverageObj = (JSONObject) resultArr.get(i);
                    String patientId = coverageObj.getString("patientId");
                    String visitId = coverageObj.getString("visitId");
                    String firstName = coverageObj.getString("firstName");
                    String lastName = coverageObj.getString("lastName");
                    String charges = coverageObj.getString("charges");
                    String patientPayment = coverageObj.getString("patientPayment");
                    String insuranceCoverage = coverageObj.getString("insuranceCoverage");
                    String status = coverageObj.getString("status");

                    InsuranceCoverageModel coverage = new InsuranceCoverageModel(patientId,visitId,firstName, lastName, charges,patientPayment,insuranceCoverage, status);
                    coverages.add(coverage);
                }

                this.insuranceCoverageFragment.onLoadSuccess(coverages);
            }
        } catch (JSONException e) {
            if (actionType.equals("update_claim")) {
                this.insuranceCoverageFragment.onLoadFailed("Something went wrong");
            }
        }
    }

}
