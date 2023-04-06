package com.example.ehr.insurance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;

public class InsuranceCoverageViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, chargesView, patientPaymentView, insuranceCoverageView;

    public InsuranceCoverageViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.insurance_coverage_name);
        chargesView = itemView.findViewById(R.id.insurance_coverage_charges);
        patientPaymentView = itemView.findViewById(R.id.insurance_coverage_patientPayment);
        insuranceCoverageView = itemView.findViewById(R.id.insurance_coverage_insuranceCoverage);
    }
}
