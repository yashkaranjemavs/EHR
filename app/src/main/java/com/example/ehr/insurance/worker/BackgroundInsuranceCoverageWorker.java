package com.example.ehr.insurance.worker;

import android.os.AsyncTask;

import com.example.ehr.BaseUrl;
import com.example.ehr.insurance.InsuranceCoverageFragment;
import com.example.ehr.insurance.InsuranceSubscribersFragment;
import com.example.ehr.insurance.model.InsuranceCoverageModel;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;

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
                    JSONObject claimObj = (JSONObject) resultArr.get(i);
                    String patientId = claimObj.getString("patientId");
                    String visitId = claimObj.getString("visitId");
                    String firstName = claimObj.getString("firstName");
                    String lastName = claimObj.getString("lastName");
                    String charges = claimObj.getString("charges");
                    String patientPayment = claimObj.getString("patientPayment");
                    String insuranceCoverage = claimObj.getString("insuranceCoverage");

                    InsuranceCoverageModel coverage = new InsuranceCoverageModel(patientId,visitId,firstName, lastName, charges,patientPayment,insuranceCoverage);
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
