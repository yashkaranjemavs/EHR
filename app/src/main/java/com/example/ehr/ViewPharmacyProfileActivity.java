package com.example.ehr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPharmacyProfileActivity extends AppCompatActivity {
    EditText nameEditText;
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
        getSupportActionBar().setTitle("Pharmacy");

        String emailid = (String) getIntent().getSerializableExtra("emailid");

        nameEditText = (EditText)findViewById(R.id.editTextName);
        emailIdTextView = (TextView)findViewById(R.id.textViewEmail);
        address1EditText = (EditText)findViewById(R.id.editTextMultiLineAdd1);
        contactEditText = (EditText)findViewById(R.id.editTextContact);
        address2EditText = (EditText)findViewById(R.id.editTextMultiLineAdd2);
        cityEditText = (EditText)findViewById(R.id.editTextCity);
        stateEditText = (EditText)findViewById(R.id.editTextState);
        zipEditText = (EditText)findViewById(R.id.editTextZip);

        BackgroundViewPharmacyProfileWorker backgroundViewPharmacyProfileWorker = new BackgroundViewPharmacyProfileWorker(ViewPharmacyProfileActivity.this);
        backgroundViewPharmacyProfileWorker.execute("viewPharmacyProfile", emailid);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundViewPharmacyProfileWorker backgroundViewPharmacyProfileWorker2 = new BackgroundViewPharmacyProfileWorker(ViewPharmacyProfileActivity.this);
                backgroundViewPharmacyProfileWorker2.execute("updatePharmacyProfile", emailid, address1EditText.getText().toString(), address2EditText.getText().toString(), contactEditText.getText().toString(), cityEditText.getText().toString(), stateEditText.getText().toString(), zipEditText.getText().toString(), nameEditText.getText().toString());
            }
        });
    }

    public void handleUI(PharmacyUserModel pharmacyUserModel, String actionType){
        if (actionType.equalsIgnoreCase("viewPharmacyProfile")) {
            if(pharmacyUserModel!=null) {
                try {
                    nameEditText.setText(pharmacyUserModel.getName());
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
        }else if(actionType.equalsIgnoreCase("updatePharmacyProfile")){
            finish();
        }
    }
}