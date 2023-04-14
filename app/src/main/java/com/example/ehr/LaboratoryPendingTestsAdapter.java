package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LaboratoryPendingTestsAdapter extends RecyclerView.Adapter<LaboratoryPendingTestsViewHolder> {


    List<LaboratoryPendingTestsModel> testsList;
    LaboratoryPendingTestsFragment testsFragment;

    public LaboratoryPendingTestsAdapter(List<LaboratoryPendingTestsModel> testsList, LaboratoryPendingTestsFragment testsFragment) {
        this.testsList = testsList;
        this.testsFragment = testsFragment;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.laboratory_pending_test_view;
    }


    @NonNull
    @Override
    public LaboratoryPendingTestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new LaboratoryPendingTestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaboratoryPendingTestsViewHolder holder, int position) {
        LaboratoryPendingTestsModel testsModel = testsList.get(position);

        String testid = testsModel.getTestid();
        String testname = testsModel.getTestname();
        String firstname = testsModel.getFirstname().concat(" ").concat(testsModel.getLastname());

        holder.testid.setText(testid);
        holder.testname.setText(testname);
        holder.firstname.setText(firstname);

        holder.Viewtest.setOnClickListener(view -> {
            LaboratoryTestDetailsDialog dialog = new LaboratoryTestDetailsDialog(view.getContext(), testsModel, testsFragment);
            dialog.show();

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);

        });


        holder.firstname.setOnClickListener(view -> {
            LaboratoryPatientDetailsDialog dialog = new LaboratoryPatientDetailsDialog(view.getContext(), testsModel);
            dialog.show();

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        });

    }
    @Override
    public int getItemCount()
    {
        return testsList.size();
    }
}