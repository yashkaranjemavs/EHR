package com.example.ehr;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ps_LaboratoryTestsFragment extends Fragment
{

    UserModel user;
    RecyclerView laboratoryTestRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.ps_fragment_laboratory_tests, container, false);



        laboratoryTestRecyclerView = view.findViewById(R.id.laboratory_test_recyclerview);
        laboratoryTestRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if (getArguments() == null) {
            return view;
        }
        user = ((ps_LaboratoryActivity) requireActivity()).getUser();
        String id = user.getUserid();

        ps_BackgroundLaboratoryTestsWorker backgroundWorker = new ps_BackgroundLaboratoryTestsWorker(
                ps_LaboratoryTestsFragment.this);
        backgroundWorker.execute("get_tests", id);

        return view;
    }

    public void onLoadSuccess(List<ps_LaboratoryTestsModel> testsModels) {

        laboratoryTestRecyclerView.setAdapter(new ps_LaboratoryTestsAdapter(testsModels,ps_LaboratoryTestsFragment.this));

        //insuranceClaimRecyclerView.setAdapter(new InsuranceClaimAdapter(insuranceClaim, InsuranceFragment.this));
    }

    public void onLoadFailed(String errorMessage) {
        System.out.println("Failed to Load");
    }
}