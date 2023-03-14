package com.example.ehr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPharmacyProfileActivity extends AppCompatActivity {
    TextView nameTextView;
    TextView emailIdTextView;
    EditText contactEditText;
    EditText address1EditText;
    EditText address2EditText;
    EditText cityEditText;
    EditText stateEditText;
    EditText zipEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pharmacy_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_pharmacy_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pharmacy Profile");

        String emailId = (String) getIntent().getSerializableExtra("emailid");

        nameTextView = (TextView)findViewById(R.id.textViewName);
        emailIdTextView = (TextView)findViewById(R.id.textViewEmail);
        address1EditText = (EditText)findViewById(R.id.editTextMultiLineAdd1);
        contactEditText = (EditText)findViewById(R.id.editTextContact);
        address2EditText = (EditText)findViewById(R.id.editTextMultiLineAdd2);
        cityEditText = (EditText)findViewById(R.id.editTextCity);
        stateEditText = (EditText)findViewById(R.id.editTextState);
        zipEditText = (EditText)findViewById(R.id.editTextZip);

        BackgroundViewPharmacyProfileWorker backgroundViewPharmacyProfileWorker = new BackgroundViewPharmacyProfileWorker(ViewPharmacyProfileActivity.this);
        backgroundViewPharmacyProfileWorker.execute("viewPharmacyProfile", emailId);
    }

    public void handleUI(PharmacyUserModel pharmacyUserModel){
        if(pharmacyUserModel!=null) {
            try {
                nameTextView.setText(pharmacyUserModel.getName());
                emailIdTextView.setText(pharmacyUserModel.getEmailid());
                contactEditText.setText(pharmacyUserModel.getContact());
                address1EditText.setText(pharmacyUserModel.getAddressline1());
                address2EditText.setText(pharmacyUserModel.getAddressline2());
                cityEditText.setText(pharmacyUserModel.getCity());
                stateEditText.setText(pharmacyUserModel.getState());
                zipEditText.setText(pharmacyUserModel.getZip());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(this, "pharmacyUserModel null", Toast.LENGTH_SHORT).show();
        }
    }
}