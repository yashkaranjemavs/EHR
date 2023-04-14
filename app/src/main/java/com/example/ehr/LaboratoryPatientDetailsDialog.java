package com.example.ehr;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class LaboratoryPatientDetailsDialog extends Dialog {
    private LaboratoryPendingTestsModel laboratoryPendingTestsModel;

    private TextView nameTextView, genderTextView, dobTextView, emailidTextView, contactTextView,
            address1TextView, address2TextView, cityTextView, stateTextView, zipTextView,patientidTextView;

    public LaboratoryPatientDetailsDialog(Context ctx, LaboratoryPendingTestsModel laboratoryPendingTestsModel) {
        super(ctx);

        this.laboratoryPendingTestsModel = laboratoryPendingTestsModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.laboratory_patient_details_dialog);

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
        nameTextView.setText(laboratoryPendingTestsModel.getFirstname() + " " + laboratoryPendingTestsModel.getLastname());
        genderTextView.setText(laboratoryPendingTestsModel.getGender());
        dobTextView.setText(laboratoryPendingTestsModel.getDob());
        cityTextView.setText(laboratoryPendingTestsModel.getCity());
        stateTextView.setText(laboratoryPendingTestsModel.getState());
        zipTextView.setText(laboratoryPendingTestsModel.getZip());
        contactTextView.setText(laboratoryPendingTestsModel.getContact());
        emailidTextView.setText(laboratoryPendingTestsModel.getEmailid());
        address1TextView.setText(laboratoryPendingTestsModel.getAddress1());
        address2TextView.setText(laboratoryPendingTestsModel.getAddress2());
        patientidTextView.setText(laboratoryPendingTestsModel.getPatientid());

    }
}
