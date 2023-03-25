package com.example.ehr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
        //String tdate = testModel.getTestdate();

        holder.testname.setText(testname);
        holder.firstname.setText(firstname);
        holder.lastname.setText(lastname);
        holder.testreport.setText(testreport);
        //holder.tdate.setText(tdate);
        holder.Addtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View editLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.ps_laboratory_pending_test_view,null);
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
