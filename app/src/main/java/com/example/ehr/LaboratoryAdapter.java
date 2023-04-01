package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class LaboratoryAdapter extends RecyclerView.Adapter<LaboratoryViewHolder> {


    List<LaboratoryPendingTestsModel> testList;
    LaboratoryFragment testFragment;

    public LaboratoryAdapter(List<LaboratoryPendingTestsModel> testList, LaboratoryFragment testFragment) {
        this.testList = testList;
        this.testFragment = testFragment;
    }


    @Override
    public int getItemViewType(final int position) {
        return R.layout.laboratory_pending_test_view;
    }


    @NonNull
    @Override
    public LaboratoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new LaboratoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaboratoryViewHolder holder, int position) {
        LaboratoryPendingTestsModel testModel = testList.get(position);
        String testname = testModel.getTestname();
        String firstname = testModel.getFirstname();
        String lastname = testModel.getLastname();
        String testreport = testModel.getTestreport();
        String testid = testModel.getTestid();

        holder.testname.setText(testname);
        holder.firstname.setText(firstname);
        holder.lastname.setText(lastname);
        holder.testreport.setText(testreport);
        holder.testid.setText(testid);

        holder.Addtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.laboratory_pending_test_view,null);
                EditText Testreport = editLayout.findViewById(R.id.laboratory_test_report);
                Testreport.setText(testModel.getTestreport());

                String testreport = Testreport.getText().toString();
                String visitid = testModel.getVisitid().toString();
                String laboratoryid = testModel.getLaboratoryid().toString();

                testModel.setTestreport(testreport);
                testFragment.onAddtestClicked(testModel);

            }
        });

    }
    @Override
    public int getItemCount()
    {
        return testList.size();
    }
}
