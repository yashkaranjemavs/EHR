package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class sr_PatientProfileActivity extends AppCompatActivity {
    UserModel user;
    EditText firstNameTextView;
    EditText lastNameTextView;
    EditText genderTextView;
    EditText dobTextView;
    EditText emailIdTextView;
    EditText contactEditText;
    EditText address1EditText;
    EditText address2EditText;
    EditText cityEditText;
    EditText stateEditText;
    EditText zipEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_patientprofile);

        Toolbar toolbar = findViewById(R.id.patient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Profile");

        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        user = (UserModel) getIntent().getSerializableExtra("user");

        String patientid = user.getUserid();

        firstNameTextView = (EditText) findViewById(R.id.patientProfile_textViewFirstName);
        lastNameTextView = (EditText) findViewById(R.id.patientProfile_textViewLastName);
        genderTextView = (EditText) findViewById(R.id.patientProfile_textViewGender);
        dobTextView = (EditText) findViewById(R.id.patientProfile_textViewDob);
        emailIdTextView = (EditText) findViewById(R.id.patientProfile_textViewEmail);
        address1EditText = (EditText) findViewById(R.id.patientProfile_editTextMultiLineAdd1);
        contactEditText = (EditText) findViewById(R.id.patientProfile_textViewContact);
        address2EditText = (EditText) findViewById(R.id.patientProfile_editTextMultiLineAdd2);
        cityEditText = (EditText) findViewById(R.id.patientProfile_editTextCity);
        stateEditText = (EditText) findViewById(R.id.patientProfile_editTextState);
        zipEditText = (EditText) findViewById(R.id.patientProfile_editTextZip);
        passwordEditText = findViewById(R.id.patientProfile_editTextPassword);

        BackgroundViewPatientWorker backgroundViewPatientWorker = new BackgroundViewPatientWorker(sr_PatientProfileActivity.this);
        backgroundViewPatientWorker.execute("patientProfile", patientid);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientUserModel patient = new PatientUserModel(
                        patientid,
                        emailIdTextView.getText().toString(),
                        passwordEditText.getText().toString(),
                        null,
                        firstNameTextView.getText().toString(),
                        lastNameTextView.getText().toString(),
                        genderTextView.getText().toString(),
                        dobTextView.getText().toString(),
                        contactEditText.getText().toString(),
                        address1EditText.getText().toString(),
                        address2EditText.getText().toString(),
                        cityEditText.getText().toString(),
                        stateEditText.getText().toString(),
                        zipEditText.getText().toString()
                    );

                BackgroundViewPatientWorker backgroundViewPatientWorker1 = new BackgroundViewPatientWorker(sr_PatientProfileActivity.this);
                backgroundViewPatientWorker1.execute("updatePatientProfile", patient);
            }
        });

    }

    private void showMenu(View v) {
        PopupMenu popupmenu = new PopupMenu(sr_PatientProfileActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.patient_menu, popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemprofile) {
                    sr_PatientProfileActivity.this.finish();
                    Intent intent = new Intent(sr_PatientProfileActivity.this, sr_PatientProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemhome) {
                    sr_PatientProfileActivity.this.finish();
                    Intent intent = new Intent(sr_PatientProfileActivity.this, PatientActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemappointments) {
                    sr_PatientProfileActivity.this.finish();
                    Intent intent = new Intent(sr_PatientProfileActivity.this, sr_PatientAppointmentsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.iteminsurance) {
                    sr_PatientProfileActivity.this.finish();
                    Intent intent = new Intent(sr_PatientProfileActivity.this, sr_PatientInsuranceActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemlogout) {
                    Intent intent = new Intent(sr_PatientProfileActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }

    public void handleUI(PatientUserModel patientUserModel, String actionType){
        if (actionType.equalsIgnoreCase("patientProfile")) {
            if (patientUserModel != null) {
                try {
                    firstNameTextView.setText(patientUserModel.getFirstname());
                    lastNameTextView.setText(patientUserModel.getLastname());
                    genderTextView.setText(patientUserModel.getGender());
                    dobTextView.setText(patientUserModel.getDob());
                    emailIdTextView.setText(patientUserModel.getEmailId());
                    passwordEditText.setText(patientUserModel.getPassword());
                    contactEditText.setText(patientUserModel.getContact());
                    address1EditText.setText(patientUserModel.getAddress1());
                    address2EditText.setText(patientUserModel.getAddress2());
                    cityEditText.setText(patientUserModel.getCity());
                    stateEditText.setText(patientUserModel.getState());
                    zipEditText.setText(patientUserModel.getZip());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "patientUserModel null", Toast.LENGTH_SHORT).show();
            }

        } else if (actionType.equalsIgnoreCase("updatePatientProfile")) {
            try {
                firstNameTextView.setText(patientUserModel.getFirstname());
                lastNameTextView.setText(patientUserModel.getLastname());
                genderTextView.setText(patientUserModel.getGender());
                dobTextView.setText(patientUserModel.getDob());
                emailIdTextView.setText(patientUserModel.getEmailId());
                passwordEditText.setText(patientUserModel.getPassword());
                contactEditText.setText(patientUserModel.getContact());
                address1EditText.setText(patientUserModel.getAddress1());
                address2EditText.setText(patientUserModel.getAddress2());
                cityEditText.setText(patientUserModel.getCity());
                stateEditText.setText(patientUserModel.getState());
                zipEditText.setText(patientUserModel.getZip());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}