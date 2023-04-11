package com.example.ehr.insurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ehr.R;
import com.example.ehr.UserModel;
import com.example.ehr.insurance.model.InsuranceSubscriberModel;
import com.example.ehr.insurance.worker.BackgroundInsuranceSubscriberWorker;

import java.util.List;

public class InsuranceSubscribersFragment extends Fragment {

    UserModel user;
    RecyclerView insuranceSubscribersRecyclerView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_insurance_subscribers, container, false);

        user = ((InsuranceActivity) requireActivity()).getUser();
        String id = user.getUserid();

        progressBar = view.findViewById(R.id.insurance_subscriber_progress);

        insuranceSubscribersRecyclerView = view.findViewById(R.id.insurance_subscriber_recyclerView);
        insuranceSubscribersRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        progressBar.setVisibility(View.VISIBLE);
        insuranceSubscribersRecyclerView.setVisibility(View.GONE);

        BackgroundInsuranceSubscriberWorker backgroundWorker = new BackgroundInsuranceSubscriberWorker(
                InsuranceSubscribersFragment.this);
        backgroundWorker.execute("get_subscribers", id);

        return view;
    }

public void onLoadSuccess(List<InsuranceSubscriberModel> insuranceSubscriber) {
    progressBar.setVisibility(View.GONE);
    insuranceSubscribersRecyclerView.setVisibility(View.VISIBLE);

    insuranceSubscribersRecyclerView.setAdapter(new InsuranceSubscriberAdapter(insuranceSubscriber));
}

    public void onLoadFailed(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}