package com.example.ehr;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


public class LaboratoryLandingTestsViewHolder extends  RecyclerView.ViewHolder {
    TextView testname, firstname, testid;
    AppCompatButton Viewtest;

    public LaboratoryLandingTestsViewHolder(@NonNull View view) {
        super(view);
        testname = view.findViewById(R.id.laboratory_test_name);
        testid = view.findViewById(R.id.laboratory_test_id);
        firstname = view.findViewById(R.id.laboratory_first_name);
        //lastname = view.findViewById(R.id.laboratory_last_name);

        Viewtest =view.findViewById(R.id.laboratory_landing_view_test_button);



    }
}