package com.example.ehr;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class LaboratoryTestsViewHolder extends  RecyclerView.ViewHolder {
    TextView testname, firstname,lastname, testid, testreport, tdate,status;

    public LaboratoryTestsViewHolder(@NonNull View view) {
        super(view);
        testname = view.findViewById(R.id.laboratory_test_name);
        testid = view.findViewById(R.id.laboratory_test_id);
        firstname = view.findViewById(R.id.laboratory_first_name);
        //lastname = view.findViewById(R.id.laboratory_last_name);
        testreport = view.findViewById(R.id.laboratory_test_report);
        tdate = view.findViewById(R.id.laboratory_test_date);
        status = view.findViewById(R.id.laboratory_test_status);

    }
}