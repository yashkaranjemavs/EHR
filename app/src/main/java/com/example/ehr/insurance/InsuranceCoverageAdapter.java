package com.example.ehr.insurance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;
import com.example.ehr.insurance.model.InsuranceClaimModel;
import com.example.ehr.insurance.model.InsuranceCoverageModel;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;

import java.util.List;

public class InsuranceCoverageAdapter extends RecyclerView.Adapter<InsuranceCoverageViewHolder> {
    List<InsuranceCoverageModel> insuranceCoverages;

    public InsuranceCoverageAdapter(List<InsuranceCoverageModel> insuranceCoverages) {
        this.insuranceCoverages = insuranceCoverages;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.insurance_coverage_view;
    }

    @NonNull
    @Override
    public InsuranceCoverageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new InsuranceCoverageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsuranceCoverageViewHolder holder, int position) {
        InsuranceCoverageModel insuranceCoverageModel = insuranceCoverages.get(position);
        String firstName = insuranceCoverageModel.getFirstName();
        String lastName = insuranceCoverageModel.getLastName();
        String charges = insuranceCoverageModel.getCharges();
        String patientPayment = insuranceCoverageModel.getPatientPayment();
        String insuranceCoverage = insuranceCoverageModel.getInsuranceCoverage();

        holder.firstNameView.setText(firstName);
        holder.lastNameView.setText(lastName);
        holder.chargesView.setText("$"+charges);
        holder.patientPaymentView.setText("$"+patientPayment);
        holder.insuranceCoverageView.setText("$"+insuranceCoverage);

    }

    @Override
    public int getItemCount() {
        return insuranceCoverages.size();
    }
}
