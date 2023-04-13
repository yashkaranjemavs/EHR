package com.example.ehr;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class LaboratoryTestDialog extends Dialog {
    private LaboratoryLandingTestsModel laboratoryLandingTestsModel;

    private TextView nameTextView, genderTextView, dobTextView, emailidTextView, contactTextView,
            address1TextView, address2TextView, cityTextView, stateTextView, zipTextView,patientidTextView;

    public LaboratoryTestDialog(Context ctx, LaboratoryLandingTestsModel laboratoryLandingTestsModel) {
        super(ctx);

        this.laboratoryLandingTestsModel = laboratoryLandingTestsModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.laboratory_test_dialog);

        nameTextView = findViewById(R.id.laboratory_patient_dialog_name);
        genderTextView = findViewById(R.id.laboratory_patient_dialog_gender);
        dobTextView = findViewById(R.id.laboratory_patient_dialog_dob);
        cityTextView = findViewById(R.id.laboratory_patient_dialog_city);
        stateTextView = findViewById(R.id.laboratory_patient_dialog_state);
        zipTextView = findViewById(R.id.laboratory_patient_dialog_zip);
        contactTextView = findViewById(R.id.laboratory_patient_dialog_contact);
        emailidTextView = findViewById(R.id.laboratory_patient_dialog_email);
        address1TextView = findViewById(R.id.laboratory_patient_dialog_address1);
        address2TextView = findViewById(R.id.laboratory_patient_dialog_address2);
        patientidTextView = findViewById(R.id.laboratory_patient_dialog_patientid);

        AppCompatButton closeButton = findViewById(R.id.laboratory_patient_dialog_close_button);

        closeButton.setOnClickListener(view -> {
            dismiss();
        });
        setViews();
    }

    private void setViews() {
        nameTextView.setText(laboratoryLandingTestsModel.getFirstname() + " " + laboratoryLandingTestsModel.getLastname());
        genderTextView.setText(laboratoryLandingTestsModel.getGender());
        dobTextView.setText(laboratoryLandingTestsModel.getDob());
        cityTextView.setText(laboratoryLandingTestsModel.getCity());
        stateTextView.setText(laboratoryLandingTestsModel.getState());
        zipTextView.setText(laboratoryLandingTestsModel.getZip());
        contactTextView.setText(laboratoryLandingTestsModel.getContact());
        emailidTextView.setText(laboratoryLandingTestsModel.getEmailid());
        address1TextView.setText(laboratoryLandingTestsModel.getAddress1());
        address2TextView.setText(laboratoryLandingTestsModel.getAddress2());
        patientidTextView.setText(laboratoryLandingTestsModel.getPatientid());

    }
}
