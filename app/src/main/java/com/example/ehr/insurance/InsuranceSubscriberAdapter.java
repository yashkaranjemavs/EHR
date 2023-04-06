package com.example.ehr.insurance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;
import com.example.ehr.insurance.model.InsuranceCoverageModel;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;

import java.util.List;

public class InsuranceSubscriberAdapter extends RecyclerView.Adapter<InsuranceSubscriberViewHolder> {
    List<InsuranceSubscriberModel> insuranceSubscribers;

    public InsuranceSubscriberAdapter(List<InsuranceSubscriberModel> insuranceSubscribers) {
        this.insuranceSubscribers = insuranceSubscribers;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.insurance_subscriber_view;
    }

    @NonNull
    @Override
    public InsuranceSubscriberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new InsuranceSubscriberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsuranceSubscriberViewHolder holder, int position) {
        InsuranceSubscriberModel insuranceSubscriberModel = this.insuranceSubscribers.get(position);
        String firstName = insuranceSubscriberModel.getFirstName();
        String lastName = insuranceSubscriberModel.getLastName();
        String expiry = insuranceSubscriberModel.getExpiry();

        holder.nameView.setText(firstName.concat(" ").concat(lastName));
        holder.expiryView.setText(expiry);
    }

    @Override
    public int getItemCount() {
        return insuranceSubscribers.size();
    }
}
