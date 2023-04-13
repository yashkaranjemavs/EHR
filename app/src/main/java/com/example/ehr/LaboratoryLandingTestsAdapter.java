package com.example.ehr;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LaboratoryLandingTestsAdapter extends RecyclerView.Adapter<LaboratoryLandingTestsViewHolder> {


    List<LaboratoryLandingTestsModel> testsList;
//    LaboratoryLandingTestsFragment testsFragment;

    public LaboratoryLandingTestsAdapter(List<LaboratoryLandingTestsModel> testsList) {
        this.testsList = testsList;
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

        String testid = testsModel.getTestid();
        String testname = testsModel.getTestname();
        String firstname = testsModel.getFirstname().concat(" ").concat(testsModel.getLastname());

        holder.testid.setText(testid);
        holder.testname.setText(testname);
        holder.firstname.setText(firstname);

        holder.Viewtest.setOnClickListener(view -> {
            LaboratoryPendingTestDialog dialog = new LaboratoryPendingTestDialog(view.getContext(), testsModel);
            dialog.show();

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);

        });


        holder.Viewpatient.setOnClickListener(view -> {
            LaboratoryTestDialog dialog = new LaboratoryTestDialog(view.getContext(), testsModel);
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
