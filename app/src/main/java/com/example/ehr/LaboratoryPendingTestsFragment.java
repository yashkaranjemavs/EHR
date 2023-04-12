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

public class LaboratoryPendingTestsFragment extends Fragment {

    UserModel user;
    RecyclerView laboratoryPendingRecyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_laboratory_pending_tests, container, false);

        laboratoryPendingRecyclerview = view.findViewById(R.id.laboratory_pending_recyclerview);
        laboratoryPendingRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));


        user = ((LaboratoryActivity) requireActivity()).getUser();
        String id = user.getUserid();

        BackgroundLaboratoryPendingTestsWorker backgroundWorker = new BackgroundLaboratoryPendingTestsWorker(
                LaboratoryPendingTestsFragment.this);
        backgroundWorker.execute("get_test", id);

        return view;
    }


    public void onLoadSuccess(List<LaboratoryPendingTestsModel> testModels) {
        laboratoryPendingRecyclerview.setAdapter(new LaboratoryPendingTestsAdapter(testModels, LaboratoryPendingTestsFragment.this, new LaboratoryPendingTestsAdapter.TextChangeCallback()
        {
            @Override
            public void textChangedAt(int position, LaboratoryPendingTestsModel text) {
                testModels.set(position, text);
            }
        }));

    }

    public void onAddtestClicked(LaboratoryPendingTestsModel testsModel) {

        BackgroundLaboratoryPendingTestsWorker backgroundWorker = new BackgroundLaboratoryPendingTestsWorker(LaboratoryPendingTestsFragment.this);
        backgroundWorker.execute("add_test", testsModel);
    }

    public void onUpdate(String errorMessage) {
        if (errorMessage.equals("")) {
            Toast.makeText(getActivity(), "Test Added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to add test report", Toast.LENGTH_SHORT).show();
        }

        BackgroundLaboratoryPendingTestsWorker backgroundWorker = new BackgroundLaboratoryPendingTestsWorker(LaboratoryPendingTestsFragment.this);
        backgroundWorker.execute("get_test", user.getUserid());
    }


    public void onFailed(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

}
