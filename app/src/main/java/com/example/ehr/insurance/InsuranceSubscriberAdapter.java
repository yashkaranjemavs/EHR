package com.example.ehr.insurance;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;
import com.example.ehr.insurance.model.InsuranceCoverageModel;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;
import com.example.ehr.sr_PatientAppointViewDialog;

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

        holder.viewButton.setOnClickListener(view -> {
            InsuranceSubscriberDialog dialog = new InsuranceSubscriberDialog(view.getContext(), insuranceSubscriberModel);
            dialog.show();

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        });
    }

    @Override
    public int getItemCount() {
        return insuranceSubscribers.size();
    }
}
