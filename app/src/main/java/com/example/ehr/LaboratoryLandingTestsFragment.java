package com.example.ehr;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class LaboratoryLandingTestsFragment extends Fragment
{
    UserModel user;
    RecyclerView laboratoryLandingTestRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_laboratory_landing_tests, container, false);
        laboratoryLandingTestRecyclerView = view.findViewById(R.id.laboratory_landing_recyclerview);
        laboratoryLandingTestRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        user = ((LaboratoryActivity) requireActivity()).getUser();
        String id = user.getUserid();

        BackgroundLaboratoryLandingTestsWorker backgroundWorker = new BackgroundLaboratoryLandingTestsWorker(
                LaboratoryLandingTestsFragment.this);
        backgroundWorker.execute("get_landing_tests", id);

        return view;
    }

    public void onLoadSuccess(List<LaboratoryLandingTestsModel> testsModels) {
        laboratoryLandingTestRecyclerView.setAdapter(new LaboratoryLandingTestsAdapter(testsModels));
    }

    public void onLoadFailed(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}