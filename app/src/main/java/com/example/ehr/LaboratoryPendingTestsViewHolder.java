package com.example.ehr;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


public class LaboratoryPendingTestsViewHolder extends  RecyclerView.ViewHolder {
    TextView testname, firstname, testid;
    AppCompatButton Viewtest;

    public LaboratoryPendingTestsViewHolder(@NonNull View view) {
        super(view);
        testname = view.findViewById(R.id.laboratory_test_name);
        testid = view.findViewById(R.id.laboratory_test_id);
        firstname = view.findViewById(R.id.laboratory_first_name);
        Viewtest =view.findViewById(R.id.laboratory_pending_view_test_button);
    }
}