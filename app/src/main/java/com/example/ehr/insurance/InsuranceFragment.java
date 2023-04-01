package com.example.ehr.insurance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.R;
import com.example.ehr.UserModel;
import com.example.ehr.insurance.model.InsuranceClaimModel;
import com.example.ehr.insurance.worker.BackgroundInsuranceClaimWorker;

import java.util.List;

public class InsuranceFragment extends Fragment {
    UserModel user;
    RecyclerView insuranceClaimRecyclerView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_insurance, container, false);

        user = ((InsuranceActivity) requireActivity()).getUser();
        String id = user.getUserid();

        progressBar = view.findViewById(R.id.insurance_claim_progress);

        insuranceClaimRecyclerView = view.findViewById(R.id.insurance_claim_recyclerView);
        insuranceClaimRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        progressBar.setVisibility(View.VISIBLE);
        insuranceClaimRecyclerView.setVisibility(View.GONE);

        BackgroundInsuranceClaimWorker backgroundWorker = new BackgroundInsuranceClaimWorker(InsuranceFragment.this);
        backgroundWorker.execute("get_claims", id);

        return view;
    }

    public void onLoadSuccess(List<InsuranceClaimModel> insuranceClaim) {
        progressBar.setVisibility(View.GONE);
        insuranceClaimRecyclerView.setVisibility(View.VISIBLE);

        insuranceClaimRecyclerView.setAdapter(new InsuranceClaimAdapter(insuranceClaim, InsuranceFragment.this));
    }

    public void onPayClicked(InsuranceClaimModel insuranceClaim) {
        progressBar.setVisibility(View.VISIBLE);
        insuranceClaimRecyclerView.setVisibility(View.GONE);

        BackgroundInsuranceClaimWorker backgroundWorker = new BackgroundInsuranceClaimWorker(InsuranceFragment.this);
        backgroundWorker.execute("update_claim", insuranceClaim);
    }

    public void onUpdate(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(getActivity(), "Updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to update", Toast.LENGTH_SHORT).show();
        }

        BackgroundInsuranceClaimWorker backgroundWorker = new BackgroundInsuranceClaimWorker(InsuranceFragment.this);
        backgroundWorker.execute("get_claims", user.getUserid());
    }

    public void onFailed(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }
}