package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ps_LaboratoryAdapter extends RecyclerView.Adapter<ps_LaboratoryViewHolder> {


    List<ps_LaboratoryPendingTestsModel> testList;
    ps_LaboratoryFragment testFragment;

    public ps_LaboratoryAdapter(List<ps_LaboratoryPendingTestsModel> testList, ps_LaboratoryFragment testFragment) {
        this.testList = testList;
        this.testFragment = testFragment;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.ps_laboratory_pending_test_view;
    }


    @NonNull
    @Override
    public ps_LaboratoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ps_LaboratoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ps_LaboratoryViewHolder holder, int position) {
        ps_LaboratoryPendingTestsModel testModel = testList.get(position);
        String testname = testModel.getTestname();
        String firstname = testModel.getFirstname();
        String lastname = testModel.getLastname();
        String testreport = testModel.getTestreport();
        String tdate = testModel.getTestdate();

        holder.testname.setText(testname);
        holder.firstname.setText(firstname);
        holder.lastname.setText(lastname);
        holder.testreport.setText(testreport);
        holder.tdate.setText(tdate);

    }
    @Override
    public int getItemCount()
    {
        return testList.size();
    }
}
