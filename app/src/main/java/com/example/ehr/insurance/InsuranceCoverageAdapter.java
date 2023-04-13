package com.example.ehr.insurance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;
import com.example.ehr.insurance.model.InsuranceCoverageModel;

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
        String insuranceCoverage = insuranceCoverageModel.getInsuranceCoverage();

        holder.nameView.setText(firstName.concat(" ").concat(lastName));
        holder.insuranceCoverageView.setText("$".concat(insuranceCoverage));

        holder.viewButton.setOnClickListener(view -> {
            InsuranceCoverageDialog dialog = new InsuranceCoverageDialog(view.getContext(), insuranceCoverageModel);
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
        return insuranceCoverages.size();
    }
}
