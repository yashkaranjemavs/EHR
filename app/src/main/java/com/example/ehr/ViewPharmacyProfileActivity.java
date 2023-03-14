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
    static TextView nameTextView;
    static TextView emailIdTextView;
    static EditText contactEditText;
    static EditText address1EditText;
    static EditText address2EditText;
    static EditText cityEditText;
    static EditText stateEditText;
    static EditText zipEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pharmacy_profile);

        String emailId = (String) getIntent().getSerializableExtra("emailid");

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_pharmacy_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pharmacy Profile");

        ViewPharmacyProfileActivity.nameTextView = (TextView)findViewById(R.id.textViewName);
        ViewPharmacyProfileActivity.emailIdTextView = (TextView)findViewById(R.id.textViewEmail);
        ViewPharmacyProfileActivity.contactEditText = (EditText)findViewById(R.id.editTextContact);
        ViewPharmacyProfileActivity.address1EditText = (EditText)findViewById(R.id.editTextMultiLineAdd1);
        ViewPharmacyProfileActivity.address2EditText = (EditText)findViewById(R.id.editTextMultiLineAdd2);
        ViewPharmacyProfileActivity.cityEditText = (EditText)findViewById(R.id.editTextCity);
        ViewPharmacyProfileActivity.stateEditText = (EditText)findViewById(R.id.editTextState);
        ViewPharmacyProfileActivity.zipEditText = (EditText)findViewById(R.id.editTextZip);

        BackgroundViewPharmacyProfileWorker backgroundViewPharmacyProfileWorker = new BackgroundViewPharmacyProfileWorker(this);
        backgroundViewPharmacyProfileWorker.execute("viewPharmacyProfile", emailId);
    }

    public void newMethod(JSONObject jsonObject){
        if(jsonObject!=null) {
            try {
                String emailId3 = (String) jsonObject.get("emailid");
                String name = (String) jsonObject.get("name");
                String contact = (String) jsonObject.get("contact");
                String addressline1 = (String) jsonObject.get("addressline1");
                String addressline2 = (String) jsonObject.get("addressline2");
                String city = (String) jsonObject.get("city");
                String state = (String) jsonObject.get("state");
                String zip = (String) jsonObject.get("zip");

                ViewPharmacyProfileActivity.nameTextView.setText(name);
                ViewPharmacyProfileActivity.emailIdTextView.setText(emailId3);
                ViewPharmacyProfileActivity.contactEditText.setText(contact);
                ViewPharmacyProfileActivity.address1EditText.setText(addressline1);
                ViewPharmacyProfileActivity.address2EditText.setText(addressline2);
                ViewPharmacyProfileActivity.cityEditText.setText(city);
                ViewPharmacyProfileActivity.stateEditText.setText(state);
                ViewPharmacyProfileActivity.zipEditText.setText(zip);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(this, "JSON null", Toast.LENGTH_SHORT).show();
        }
    }
}