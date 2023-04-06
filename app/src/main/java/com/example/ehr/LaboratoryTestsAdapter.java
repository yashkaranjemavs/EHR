package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LaboratoryTestsAdapter extends RecyclerView.Adapter<LaboratoryTestsViewHolder> {


    List<LaboratoryAllTestsModel> testsList;
    LaboratoryTestsFragment testsFragment;

    public LaboratoryTestsAdapter(List<LaboratoryAllTestsModel> testsList, LaboratoryTestsFragment testsFragment) {
        this.testsList = testsList;
        this.testsFragment = testsFragment;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.laboratory_all_test_view;
    }


    @NonNull
    @Override
    public LaboratoryTestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new LaboratoryTestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaboratoryTestsViewHolder holder, int position) {
        LaboratoryAllTestsModel testsModel = testsList.get(position);
        String testname = testsModel.getTestname();
        String testid = testsModel.getTestid();
        String firstname = testsModel.getFirstname().concat(" ").concat(testsModel.getLastname());
        //String lastname = testsModel.getLastname();
        String testreport = testsModel.getTestreport();
        String tdate = testsModel.getTestdate();
        String status = testsModel.getStatus();

        holder.testname.setText(testname);
        holder.testid.setText(testid);
        holder.firstname.setText(firstname);
        //holder.lastname.setText(lastname);
        holder.testreport.setText(testreport);
        holder.tdate.setText(tdate);
        holder.status.setText(status);

    }
    @Override
    public int getItemCount()
    {
        return testsList.size();
    }
}
