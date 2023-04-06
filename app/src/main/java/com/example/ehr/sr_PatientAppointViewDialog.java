package com.example.ehr;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

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
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class sr_PatientAppointViewDialog extends Dialog implements
        android.view.View.OnClickListener {

    private sr_PatientAppointmentsActivity activity;
    private Dialog dialog;
    private String visitId;
    private ScrollView visitScrollView;
    private TextView visitDateView, visitTimeView, patientNameView, appointmentStatusView, patientNotesView, providerNotesView, symptomsView, diagnosisView;
    private TextView providerNameView, insuranceNameView, chargesView, patientPaymentView, insuranceCoverageView, balanceView, paymentStatusView;
    private AppCompatButton payBtn;

    public sr_PatientAppointViewDialog(sr_PatientAppointmentsActivity activity, String visitId) {
        super(activity);
        this.activity = activity;
        this.visitId = visitId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.patient_appointment_view);

        visitScrollView = findViewById(R.id.patient_visit_container);

        visitDateView = findViewById(R.id.patient_view_date);
        visitTimeView = findViewById(R.id.patient_view_time);
        patientNameView = findViewById(R.id.patient_view_patient_name);
        appointmentStatusView = findViewById(R.id.patient_view_appointment_status);
        patientNotesView = findViewById(R.id.patient_view_patient_notes);
        providerNotesView = findViewById(R.id.patient_view_provider_notes);
        symptomsView = findViewById(R.id.patient_view_symptoms);
        diagnosisView = findViewById(R.id.patient_view_diagnosis);
        providerNameView = findViewById(R.id.patient_view_provider_name);
        insuranceCoverageView = findViewById(R.id.patient_view_insurance_coverage);
        insuranceNameView = findViewById(R.id.patient_view_insurance_name);
        chargesView = findViewById(R.id.patient_view_charges);
        patientPaymentView = findViewById(R.id.patient_view_patient_payment);
        balanceView = findViewById(R.id.patient_view_balance);
        paymentStatusView = findViewById(R.id.patient_view_payment_status);

        payBtn = findViewById(R.id.patient_pay_charges_btn);

        payBtn.setOnClickListener(this);

        visitScrollView.setVisibility(View.GONE);
        dbprocess dbProcessObj = new dbprocess();
        dbProcessObj.execute("getAppointmentDetails", this.visitId);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    class dbprocess extends AsyncTask<String, Void, String>
    {
        String actionType;

        @Override
        protected String doInBackground(String... params) {
            actionType = params[0];
            String baseUrl = BaseUrl.baseUrl;
            try {
                if (actionType.equalsIgnoreCase("getAppointmentDetails")) {
                    String visitId = params[1];
                    String urlString = baseUrl + "/sr_getAppointmentDetailsByVisitId.php?visitid=" + visitId;

                    URL url = new URL(urlString);

                    return handleRequest(url, "GET", null);
                } else if (actionType.equalsIgnoreCase("pay")) {
                    String visitId = params[1];
                    String urlString = baseUrl + "/sr_payPatientCharges.php";

                    URL url = new URL(urlString);

                    String post_data = URLEncoder.encode("visitid", "UTF-8") + "=" + URLEncoder.encode(visitId, "UTF-8");
                    return handleRequest(url, "POST", post_data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String resultString) {
            try {
                if (actionType.equals("getAppointmentDetails")) {
                    JSONObject resultObj = new JSONObject(resultString);

                    if(resultObj.has("error"))
                    {
                        Toast.makeText(activity,resultObj.getString("error"),Toast.LENGTH_LONG).show();
                    } else
                    {
                        visitScrollView.setVisibility(View.VISIBLE);

                        visitDateView.setText(resultObj.getString("vdate"));
                        visitTimeView.setText(resultObj.getString("vtime"));
                        patientNameView.setText(resultObj.getString("patientfirstname") + " " + resultObj.getString("patientlastname"));
                        appointmentStatusView.setText(resultObj.getString("appointmentstatus"));
                        patientNotesView.setText(resultObj.getString("patientnotes"));
                        providerNotesView.setText(resultObj.getString("providernotes"));
                        symptomsView.setText(resultObj.getString("symptoms"));
                        diagnosisView.setText(resultObj.getString("diagnosis"));
                        providerNameView.setText(resultObj.getString("providerfirstname")+ " " + resultObj.getString("providerlastname"));
                        insuranceCoverageView.setText(resultObj.getString("insurancecoverage"));
                        insuranceNameView.setText(resultObj.getString("insurancename"));
                        chargesView.setText(resultObj.getString("charges"));
                        patientPaymentView.setText(resultObj.getString("patientpayment"));
                        balanceView.setText(resultObj.getString("balance"));
                        paymentStatusView.setText(resultObj.getString("paymentstatus"));
                    }
                } else if (actionType.equals("pay")) {
                    JSONObject resultObj = new JSONObject(resultString);

                    if(resultObj.has("error"))
                    {
                        Toast.makeText(activity,resultObj.getString("error"),Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity,resultObj.getString("success"),Toast.LENGTH_SHORT).show();
//                        getInsuranceNames();
                    }
//                    getActiveInsurance();
                }
            } catch (JSONException e) {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

        private String handleRequest(URL url, String method, String postData) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod(method);
                httpURLConnection.setDoInput(true);


                if (method == "POST") {
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));


                    bufferedWriter.write(postData);
                    bufferedWriter.close();
                    outputStream.close();
                }

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
    }

}
