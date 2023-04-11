package com.example.ehr.insurance;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.ehr.BaseUrl;
import com.example.ehr.R;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;
import com.example.ehr.sr_PatientAppointmentsActivity;

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

public class InsuranceSubscriberDialog extends Dialog {
    private InsuranceSubscriberModel insuranceSubscriberModel;
    private TextView nameTextView, genderTextView, dobTextView, emailIdTextView, contactTextView,
            address1TextView, address2TextView, cityTextView, stateTextView, zipTextView, expiryTextView;

    public InsuranceSubscriberDialog(Context ctx, InsuranceSubscriberModel insuranceSubscriberModel) {
        super(ctx);

        this.insuranceSubscriberModel = insuranceSubscriberModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.insurance_subscriber_dialog);

        nameTextView = findViewById(R.id.insurance_subscriber_dialog_name);
        genderTextView = findViewById(R.id.insurance_subscriber_dialog_gender);
        dobTextView = findViewById(R.id.insurance_subscriber_dialog_dob);
        emailIdTextView = findViewById(R.id.insurance_subscriber_dialog_email);
        contactTextView = findViewById(R.id.insurance_subscriber_dialog_contact);
        address1TextView = findViewById(R.id.insurance_subscriber_dialog_address1);
        address2TextView = findViewById(R.id.insurance_subscriber_dialog_address2);
        cityTextView = findViewById(R.id.insurance_subscriber_dialog_city);
        stateTextView = findViewById(R.id.insurance_subscriber_dialog_state);
        zipTextView = findViewById(R.id.insurance_subscriber_dialog_zip);
        expiryTextView = findViewById(R.id.insurance_subscriber_dialog_expiry);

        AppCompatButton closeButton = findViewById(R.id.insurance_subscriber_dialog_close_button);

        closeButton.setOnClickListener(view -> {
            dismiss();
        });

        setViews();
    }

    private void setViews() {
        nameTextView.setText(insuranceSubscriberModel.getFirstName() + " " + insuranceSubscriberModel.getLastName());
        genderTextView.setText(insuranceSubscriberModel.getGender());
        dobTextView.setText(insuranceSubscriberModel.getDob());
        emailIdTextView.setText(insuranceSubscriberModel.getEmailId());
        contactTextView.setText(insuranceSubscriberModel.getContact());
        address1TextView.setText(insuranceSubscriberModel.getAddress1());
        address2TextView.setText(insuranceSubscriberModel.getAddress2());
        cityTextView.setText(insuranceSubscriberModel.getCity());
        stateTextView.setText(insuranceSubscriberModel.getState());
        zipTextView.setText(insuranceSubscriberModel.getZip());
        expiryTextView.setText(insuranceSubscriberModel.getExpiry());
    }
}
