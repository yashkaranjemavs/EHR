package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LaboratoryLandingTestsAdapter extends RecyclerView.Adapter<LaboratoryLandingTestsViewHolder> {


    List<LaboratoryLandingTestsModel> testsList;
    LaboratoryLandingTestsFragment testsFragment;

    public LaboratoryLandingTestsAdapter(List<LaboratoryLandingTestsModel> testsList, LaboratoryLandingTestsFragment testsFragment) {
        this.testsList = testsList;
        this.testsFragment = testsFragment;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.laboratory_landing_test_view;
    }


    @NonNull
    @Override
    public LaboratoryLandingTestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new LaboratoryLandingTestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaboratoryLandingTestsViewHolder holder, int position) {
        LaboratoryLandingTestsModel testsModel = testsList.get(position);
        String testname = testsModel.getTestname();
        String testid = testsModel.getTestid();
        String firstname = testsModel.getFirstname().concat(" ").concat(testsModel.getLastname());
        //String lastname = testsModel.getLastname();

        holder.testname.setText(testname);
        holder.testid.setText(testid);
        holder.firstname.setText(firstname);
       // holder.lastname.setText(lastname);


    }
    @Override
    public int getItemCount()
    {
        return testsList.size();
    }
}
