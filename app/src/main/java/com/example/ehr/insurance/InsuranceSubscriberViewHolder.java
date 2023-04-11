package com.example.ehr.insurance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;

public class InsuranceSubscriberViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, expiryView;
    AppCompatButton viewButton;

    public InsuranceSubscriberViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.insurance_subscriber_name);
        expiryView = itemView.findViewById(R.id.insurance_subscriber_expiry);

        viewButton = itemView.findViewById(R.id.insurance_subscriber_view_button);
    }
}
