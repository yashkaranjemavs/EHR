package com.example.ehr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class sr_PatientProfileActivity extends AppCompatActivity {
    UserModel user;
    TextView firstNameTextView;
    TextView lastNameTextView;
    TextView genderTextView;
    TextView dobTextView;
    TextView emailIdTextView;
    TextView contactEditText;
    TextView address1EditText;
    TextView address2EditText;
    TextView cityEditText;
    TextView stateEditText;
    TextView zipEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_patientprofile);

        Toolbar toolbar = findViewById(R.id.patient_toolbar);
        setSupportActionBar(toolbar);

        user = (UserModel) getIntent().getSerializableExtra("user");

        String id = user.getUserid();

        firstNameTextView = findViewById(R.id.textViewFirstName);
        lastNameTextView = findViewById(R.id.textViewLastName);
        genderTextView = findViewById(R.id.textViewGender);
        dobTextView = findViewById(R.id.textViewDob);
        emailIdTextView = findViewById(R.id.textViewEmail);
        address1EditText = findViewById(R.id.editTextMultiLineAdd1);
        contactEditText = findViewById(R.id.textViewContact);
        address2EditText = findViewById(R.id.editTextMultiLineAdd2);
        cityEditText = findViewById(R.id.editTextCity);
        stateEditText = findViewById(R.id.editTextState);
        zipEditText = findViewById(R.id.editTextZip);

        BackgroundViewPatientWorker backgroundViewPatientWorker = new BackgroundViewPatientWorker(sr_PatientProfileActivity.this);
        backgroundViewPatientWorker.execute("patientProfile", id);

    }

    public void handleUI(PatientUserModel patientUserModel){
        if(patientUserModel!=null) {
            try {
                firstNameTextView.setText(patientUserModel.getFirstname());
                lastNameTextView.setText(patientUserModel.getLastname());
                genderTextView.setText(patientUserModel.getGender());
                dobTextView.setText(patientUserModel.getDob());
                emailIdTextView.setText(patientUserModel.getEmailid());
                contactEditText.setText(patientUserModel.getContact());
                address1EditText.setText(patientUserModel.getAddressline1());
                address2EditText.setText(patientUserModel.getAddressline2());
                cityEditText.setText(patientUserModel.getCity());
                stateEditText.setText(patientUserModel.getState());
                zipEditText.setText(patientUserModel.getZip());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(this, "patientUserModel null", Toast.LENGTH_SHORT).show();
        }
    }


}