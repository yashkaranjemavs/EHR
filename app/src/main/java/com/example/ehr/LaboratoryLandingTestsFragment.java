package com.example.ehr;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class LaboratoryLandingTestsFragment extends Fragment
{
    UserModel user;
    RecyclerView laboratoryTestRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_laboratory_landing_tests, container, false);



        laboratoryTestRecyclerView = view.findViewById(R.id.laboratory_landing_recyclerview);
        laboratoryTestRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


      /*  if (getArguments() == null) {
            return view;
        }*/
        user = ((LaboratoryActivity) requireActivity()).getUser();
        String id = user.getUserid();

        BackgroundLaboratoryLandingTestsWorker backgroundWorker = new BackgroundLaboratoryLandingTestsWorker(
                LaboratoryLandingTestsFragment.this);
        backgroundWorker.execute("get_landing_tests", id);

        return view;
    }

    public void onLoadSuccess(List<LaboratoryLandingTestsModel> testsModels) {
        laboratoryTestRecyclerView.setAdapter(new LaboratoryLandingTestsAdapter(testsModels, LaboratoryLandingTestsFragment.this));
    }

    public void onLoadFailed(String errorMessage) {
        System.out.println("Failed to Load");
    }
}