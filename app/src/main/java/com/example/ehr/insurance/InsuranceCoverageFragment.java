package com.example.ehr.insurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ehr.R;
import com.example.ehr.UserModel;
import com.example.ehr.insurance.model.InsuranceCoverageModel;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;
import com.example.ehr.insurance.worker.BackgroundInsuranceCoverageWorker;
import com.example.ehr.insurance.worker.BackgroundInsuranceSubscriberWorker;

import java.util.List;

public class InsuranceCoverageFragment extends Fragment {

    UserModel user;

    RecyclerView insuranceCoverageRecyclerView;

    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_insurance_coverage, container, false);

        user = ((InsuranceActivity) requireActivity()).getUser();
        String id = user.getUserid();

        progressBar = view.findViewById(R.id.insurance_coverage_progress);

        insuranceCoverageRecyclerView = view.findViewById(R.id.insurance_coverage_recyclerView);
        insuranceCoverageRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        progressBar.setVisibility(View.VISIBLE);
        insuranceCoverageRecyclerView.setVisibility(View.GONE);

        BackgroundInsuranceCoverageWorker backgroundWorker = new BackgroundInsuranceCoverageWorker(
                InsuranceCoverageFragment.this);
        backgroundWorker.execute("get_coverages", id);

        return view;
    }

    public void onLoadSuccess(List<InsuranceCoverageModel> insuranceCoverage) {
        progressBar.setVisibility(View.GONE);
        insuranceCoverageRecyclerView.setVisibility(View.VISIBLE);

        insuranceCoverageRecyclerView.setAdapter(new InsuranceCoverageAdapter(insuranceCoverage));
    }

    public void onLoadFailed(String errorMessage) {
        System.out.println("onLoadFailed");
    }
}