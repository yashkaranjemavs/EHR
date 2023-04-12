package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

        holder.testname.setText(testname);
        holder.testid.setText(testid);
        holder.firstname.setText(firstname);

        holder.Viewtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    LaboratoryPendingTestsFragment laboratoryPendingTestsFragment = new LaboratoryPendingTestsFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.laboratory_landing_recyclerview,laboratoryPendingTestsFragment).addToBackStack(null).commit();


            }
        });

    }
    @Override
    public int getItemCount()
    {
        return testsList.size();
    }
}
