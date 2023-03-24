package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ps_LaboratoryTestsAdapter extends RecyclerView.Adapter<ps_LaboratoryTestsViewHolder> {


    List<ps_LaboratoryTestsModel> testsList;
    ps_LaboratoryTestsFragment testsFragment;

    public ps_LaboratoryTestsAdapter(List<ps_LaboratoryTestsModel> testsList, ps_LaboratoryTestsFragment testsFragment) {
        this.testsList = testsList;
        this.testsFragment = testsFragment;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.ps_laboratory_test_view;
    }


    @NonNull
    @Override
    public ps_LaboratoryTestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ps_LaboratoryTestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ps_LaboratoryTestsViewHolder holder, int position) {
        ps_LaboratoryTestsModel testsModel = testsList.get(position);
        String testname = testsModel.getTestname();
        String firstname = testsModel.getFirstname();
        String lastname = testsModel.getLastname();
        String testreport = testsModel.getTestreport();

        holder.testname.setText(testname);
        holder.firstname.setText(firstname);
        holder.lastname.setText(lastname);
        holder.testreport.setText(testreport);

    }
    @Override
    public int getItemCount()
    {
        return testsList.size();
    }
}
