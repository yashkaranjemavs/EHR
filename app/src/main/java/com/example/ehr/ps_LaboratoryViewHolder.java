package com.example.ehr;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


public class ps_LaboratoryViewHolder extends  RecyclerView.ViewHolder {
    TextView testname, firstname,lastname;
    EditText testreport;

    AppCompatButton Addtest;

    public ps_LaboratoryViewHolder(@NonNull View view) {
        super(view);
        testname = view.findViewById(R.id.laboratory_test_name);
        firstname = view.findViewById(R.id.laboratory_first_name);
        lastname = view.findViewById(R.id.laboratory_last_name);
        testreport = view.findViewById(R.id.laboratory_test_report);
        //tdate = view.findViewById(R.id.laboratory_test_date);

        Addtest =view.findViewById(R.id.laboratory_pending_test_view_button);

    }
}