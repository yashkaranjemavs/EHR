package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ps_LaboratoryTestsAdapter extends RecyclerView.Adapter<ps_LaboratoryTestsAdapter.LaboratoryTestsViewHolder> {


    List<ps_LaboratoryTestsModel> testsList;

    public ps_LaboratoryTestsAdapter(List<ps_LaboratoryTestsModel> testsList) {
        this.testsList = testsList;
    }
    @Override
    public int getItemViewType(final int position) {
        return R.layout.ps_laboratory_test_view;
    }

    public class LaboratoryTestsViewHolder extends  RecyclerView.ViewHolder {
         TextView testname, firstname,lastname;

        public LaboratoryTestsViewHolder(View view) {
            super(view);
            testname = view.findViewById(R.id.laboratory_test_name);
            firstname = view.findViewById(R.id.laboratory_first_name);
            lastname = view.findViewById(R.id.laboratory_last_name);

        }
    }

    @NonNull
    @Override
    public LaboratoryTestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new LaboratoryTestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaboratoryTestsViewHolder holder, int position) {
        ps_LaboratoryTestsModel testsModel = testsList.get(position);
        String testname = testsModel.getTestname();
        String firstname = testsModel.getFirstname();
        String lastname = testsModel.getLastname();

        holder.testname.setText(testname);
        holder.firstname.setText(firstname);
        holder.lastname.setText(lastname);

    }
    @Override
    public int getItemCount()
    {
        return testsList.size();
    }
}
