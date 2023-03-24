package com.example.ehr.insurance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;

public class InsuranceSubscriberViewHolder extends RecyclerView.ViewHolder {
    TextView firstNameView, lastNameView, expiryView;

    public InsuranceSubscriberViewHolder(@NonNull View itemView) {
        super(itemView);
        firstNameView = itemView.findViewById(R.id.insurance_subscriber_firstName);
        lastNameView = itemView.findViewById(R.id.insurance_subscriber_lastName);
        expiryView = itemView.findViewById(R.id.insurance_subscriber_expiry);
    }
}
