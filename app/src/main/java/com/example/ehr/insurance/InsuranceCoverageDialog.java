package com.example.ehr.insurance;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.ehr.R;
import com.example.ehr.insurance.model.InsuranceCoverageModel;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;

public class InsuranceCoverageDialog extends Dialog {
    private InsuranceCoverageModel insuranceCoverageModel;
    private TextView nameTextView, chargesTextView, patientPaymentTextView, insuranceCoverageTextView, statusTextView;

    public InsuranceCoverageDialog(Context ctx, InsuranceCoverageModel insuranceCoverageModel) {
        super(ctx);

        this.insuranceCoverageModel = insuranceCoverageModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.insurance_coverage_dialog);

        nameTextView = findViewById(R.id.insurance_coverage_dialog_name);
        chargesTextView = findViewById(R.id.insurance_coverage_dialog_charges);
        patientPaymentTextView = findViewById(R.id.insurance_coverage_dialog_patient_payment);
        insuranceCoverageTextView = findViewById(R.id.insurance_coverage_dialog_insurance_coverage);
        statusTextView = findViewById(R.id.insurance_coverage_dialog_status);

        AppCompatButton closeButton = findViewById(R.id.insurance_coverage_dialog_close_button);

        closeButton.setOnClickListener(view -> {
            dismiss();
        });

        setViews();
    }

    private void setViews() {
        nameTextView.setText(insuranceCoverageModel.getFirstName() + " " + insuranceCoverageModel.getLastName());
        chargesTextView.setText(insuranceCoverageModel.getCharges());
        patientPaymentTextView.setText(insuranceCoverageModel.getPatientPayment());
        insuranceCoverageTextView.setText(insuranceCoverageModel.getInsuranceCoverage());
        statusTextView.setText(insuranceCoverageModel.getStatus());
    }
}
