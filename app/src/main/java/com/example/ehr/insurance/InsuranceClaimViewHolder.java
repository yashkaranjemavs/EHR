package com.example.ehr.insurance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;

public class InsuranceClaimViewHolder extends RecyclerView.ViewHolder {
    TextView firstNameView, lastNameView, chargesView, patientPaymentView;
    AppCompatButton payButton;

    public InsuranceClaimViewHolder(@NonNull View itemView) {
        super(itemView);
        firstNameView = itemView.findViewById(R.id.insurance_claim_firstName);
        lastNameView = itemView.findViewById(R.id.insurance_claim_lastName);
        chargesView = itemView.findViewById(R.id.insurance_claim_charges);
        patientPaymentView = itemView.findViewById(R.id.insurance_claim_patientPayment);

        payButton = itemView.findViewById(R.id.insurance_claim_pay);
    }
}
