package com.example.ehr;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


public class LaboratoryPendingTestsViewHolder extends  RecyclerView.ViewHolder {
    TextView testname, laboratoryid,visitid,tdate,firstname,testid;
    EditText testreport;

    AppCompatButton Addtest;

    public LaboratoryPendingTestsViewHolder(@NonNull View view) {
        super(view);
        testid = view.findViewById(R.id.laboratory_test_id);
        laboratoryid = view.findViewById(R.id.laboratory_id);
        visitid = view.findViewById(R.id.laboratory_visit_id);
        testname = view.findViewById(R.id.laboratory_test_name);
        testreport = view.findViewById(R.id.laboratory_test_report);
        tdate = view.findViewById(R.id.laboratory_test_date);
        firstname = view.findViewById(R.id.laboratory_first_name);
        //lastname = view.findViewById(R.id.laboratory_last_name);

        Addtest =view.findViewById(R.id.laboratory_pending_test_add_test_button);

    }
}