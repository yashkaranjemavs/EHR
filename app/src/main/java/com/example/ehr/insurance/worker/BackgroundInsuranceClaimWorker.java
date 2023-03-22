package com.example.ehr.insurance.worker;

import android.os.AsyncTask;

import com.example.ehr.insurance.InsuranceFragment;
import com.example.ehr.insurance.model.InsuranceClaimModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BackgroundInsuranceClaimWorker extends AsyncTask<Object, Void, String> {
    InsuranceFragment insuranceFragment;
    String actionType;
    InsuranceClaimModel insuranceClaim;

    public BackgroundInsuranceClaimWorker(InsuranceFragment fragment) {
        this.insuranceFragment = fragment;
    }


    @Override
    protected String doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = "https://kxp9181.uta.cloud";

        try {
            if (actionType.equals("get_claims")) {
                String id = (String) params[1];

                String urlString = baseUrl + "/getInsuranceClaims.php?id=" + id;
                URL url = new URL(urlString);

                return WorkerHelper.handleGetRequest(url);
            } else if (actionType.equals("update_claim")) {
                String urlString = baseUrl + "/updateInsuranceClaim.php";
                URL url = new URL(urlString);

                insuranceClaim = (InsuranceClaimModel) params[1];
                String insuranceCoverage = insuranceClaim.getInsuranceCoverage();
                String visitId = insuranceClaim.getVisitId();

                String postData = URLEncoder.encode("visitId", "UTF-8") + "=" + URLEncoder.encode(visitId, "UTF-8") + "&" +
                        URLEncoder.encode("insuranceCoverage", "UTF-8") + "=" + URLEncoder.encode(insuranceCoverage, "UTF-8");

                return WorkerHelper.handlePostRequest(url, postData);
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
    protected void onPostExecute(String resultString) {
        handleResponse(resultString);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private void handleResponse(String resultString) {
        try {
            if (actionType.equals("get_claims")) {
                JSONArray resultArr = new JSONArray(resultString);

                List<InsuranceClaimModel> claims = new ArrayList<>();

                for (int i=0; i<resultArr.length(); i++) {
                    JSONObject claimObj = (JSONObject) resultArr.get(i);
                    String patientId = claimObj.getString("patientId");
                    String firstName = claimObj.getString("firstName");
                    String lastName = claimObj.getString("lastName");
                    String charges = claimObj.getString("charges");
                    String patientPayment = claimObj.getString("patientPayment");
                    String visitId = claimObj.getString("visitId");

                    InsuranceClaimModel claim = new InsuranceClaimModel(patientId,firstName, lastName, charges, patientPayment, null, visitId);
                    claims.add(claim);
                }

                this.insuranceFragment.onLoadSuccess(claims);
            } else if (actionType.equals("update_claim")) {
                JSONObject resultObj = new JSONObject(resultString);

                if (resultObj.has("error")) {
                    this.insuranceFragment.onUpdate(resultObj.getString("error"));
                    return;
                }

                this.insuranceFragment.onUpdate("");
            }
        } catch (JSONException e) {
                this.insuranceFragment.onFailed("Something went wrong");
        }
    }
}
