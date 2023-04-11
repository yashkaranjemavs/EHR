package com.example.ehr.insurance.worker;

import android.os.AsyncTask;

import com.example.ehr.BaseUrl;
import com.example.ehr.insurance.InsuranceSubscribersFragment;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BackgroundInsuranceSubscriberWorker extends AsyncTask<Object, Void, String> {

    InsuranceSubscribersFragment insuranceSubscribersFragment;

    String actionType;

    public BackgroundInsuranceSubscriberWorker(InsuranceSubscribersFragment fragment) {
        this.insuranceSubscribersFragment = fragment;
    }

    @Override
    protected String doInBackground(Object... params) {
        actionType = (String) params[0];
        String baseUrl = BaseUrl.baseUrl;

        try {
            if (actionType.equals("get_subscribers")) {
                String id = (String) params[1];

                String urlString = baseUrl + "/getInsuranceSubscribers.php?id=" + id;
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
            if (actionType.equals("get_subscribers")) {
                JSONArray resultArr = new JSONArray(resultString);

                List<InsuranceSubscriberModel> subscribers = new ArrayList<>();

                for (int i = 0; i < resultArr.length(); i++) {
                    JSONObject claimObj = (JSONObject) resultArr.get(i);
                    String patientId = claimObj.getString("patientId");
                    String firstName = claimObj.getString("firstName");
                    String lastName = claimObj.getString("lastName");
                    String gender = claimObj.getString("gender");
                    String dob = claimObj.getString("dob");
                    String emailId = claimObj.getString("emailId");
                    String contact = claimObj.getString("contact");
                    String address1 = claimObj.getString("address1");
                    String address2 = claimObj.getString("address2");
                    String city = claimObj.getString("city");
                    String state = claimObj.getString("state");
                    String zip = claimObj.getString("zip");
                    String expiry = claimObj.getString("expiry");

                    InsuranceSubscriberModel subscriber = new InsuranceSubscriberModel(
                            patientId, firstName, lastName, gender, dob, emailId, contact,
                            address1, address2, city, state, zip, expiry);
                    subscribers.add(subscriber);
                }

                this.insuranceSubscribersFragment.onLoadSuccess(subscribers);
            }
        } catch (JSONException e) {
            this.insuranceSubscribersFragment.onLoadFailed("Something went wrong");
        }
    }

}
