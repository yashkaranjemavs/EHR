package com.example.ehr;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ps_LaboratoryTestsViewHolder extends  RecyclerView.ViewHolder {
    TextView testname, firstname,lastname, testreport;

    public ps_LaboratoryTestsViewHolder(@NonNull View view) {
        super(view);
        testname = view.findViewById(R.id.laboratory_test_name);
        firstname = view.findViewById(R.id.laboratory_first_name);
        lastname = view.findViewById(R.id.laboratory_last_name);
        testreport = view.findViewById(R.id.laboratory_test_report);

    }
}