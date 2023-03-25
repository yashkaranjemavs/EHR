package com.example.ehr;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ps_LaboratoryFragment extends Fragment {

    UserModel user;
    RecyclerView laboratoryRecyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ps_fragment_laboratory, container, false);

        laboratoryRecyclerview = view.findViewById(R.id.laboratory_recyclerview);
        laboratoryRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));


        user = ((ps_LaboratoryActivity) requireActivity()).getUser();
        String id = user.getUserid();

        ps_BackgroundLaboratoryPendingTestsWorker backgroundWorker = new ps_BackgroundLaboratoryPendingTestsWorker(
                ps_LaboratoryFragment.this);
        backgroundWorker.execute("get_test", id);

        return view;
    }


    public void onLoadSuccess(List<ps_LaboratoryPendingTestsModel> testModels) {
        laboratoryRecyclerview.setAdapter(new ps_LaboratoryAdapter(testModels,ps_LaboratoryFragment.this));
    }

    public void onLoadFailed(String errorMessage) {
        System.out.println("Failed to Load");
    }


}
