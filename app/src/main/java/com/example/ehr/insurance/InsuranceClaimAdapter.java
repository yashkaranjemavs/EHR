package com.example.ehr.insurance;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;
import com.example.ehr.insurance.model.InsuranceClaimModel;
import com.example.ehr.insurance.worker.BackgroundInsuranceClaimWorker;

import java.util.List;

public class InsuranceClaimAdapter extends RecyclerView.Adapter<InsuranceClaimViewHolder> {
    List<InsuranceClaimModel> insuranceClaims;
    AlertDialog.Builder alertDialogBuilder;
    InsuranceFragment fragment;

    public InsuranceClaimAdapter(List<InsuranceClaimModel> insuranceClaims, InsuranceFragment fragment) {
        this.insuranceClaims = insuranceClaims;
        this.fragment = fragment;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.insurance_claim_view;
    }

    @NonNull
    @Override
    public InsuranceClaimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        alertDialogBuilder = new AlertDialog.Builder(parent.getContext());
        return new InsuranceClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsuranceClaimViewHolder holder, int position) {
        InsuranceClaimModel insuranceClaim = insuranceClaims.get(position);
        String firstName = insuranceClaim.getFirstName();
        String lastName = insuranceClaim.getLastName();
        String charges = insuranceClaim.getCharges();
        String patientPayment = insuranceClaim.getPatientPayment();

        holder.firstNameView.setText(firstName);
        holder.lastNameView.setText(lastName);
        holder.chargesView.setText("$"+charges);
        holder.patientPaymentView.setText("$"+patientPayment);

        holder.payButton.setOnClickListener(view -> {
            int balance = Integer.parseInt(charges) - Integer.parseInt(patientPayment);
            String message = "Pay the remaining balance of $" + balance + " for " + firstName + " " + lastName + ".";

            alertDialogBuilder.setTitle("Pay balance");
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
                insuranceClaim.setInsuranceCoverage(String.valueOf(balance));
                fragment.onPayClicked(insuranceClaim);
            });

            alertDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.cancel();
            });

            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return insuranceClaims.size();
    }
}