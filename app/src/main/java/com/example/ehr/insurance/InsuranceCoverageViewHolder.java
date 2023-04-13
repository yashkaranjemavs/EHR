package com.example.ehr.insurance;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;

public class InsuranceCoverageViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, insuranceCoverageView;
    AppCompatButton viewButton;

    public InsuranceCoverageViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.insurance_coverage_name);
        insuranceCoverageView = itemView.findViewById(R.id.insurance_coverage_insuranceCoverage);

        viewButton = itemView.findViewById(R.id.insurance_coverage_view_button);
    }
}
