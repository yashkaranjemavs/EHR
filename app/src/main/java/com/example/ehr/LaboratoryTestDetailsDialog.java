package com.example.ehr;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;



public class LaboratoryTestDetailsDialog extends Dialog {
    private LaboratoryPendingTestsModel laboratoryPendingTestsModel;

    private TextView laboratoryidTextView,testidTextView,visitidTextView,testnameTextView,testdateTextView,nameTextView;
    private EditText testreportTextView;
    LaboratoryPendingTestsFragment fragment;
    public LaboratoryTestDetailsDialog(Context ctx, LaboratoryPendingTestsModel laboratoryPendingTestsModel, LaboratoryPendingTestsFragment fragment) {
        super(ctx);

        this.laboratoryPendingTestsModel = laboratoryPendingTestsModel;
        this.fragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.laboratory_test_details_dialog);

        nameTextView = findViewById(R.id.laboratory_first_name);
        laboratoryidTextView = findViewById(R.id.laboratory_id);
        testidTextView = findViewById(R.id.laboratory_test_id);
        visitidTextView = findViewById(R.id.laboratory_visit_id);
        testnameTextView = findViewById(R.id.laboratory_test_name);
        testdateTextView = findViewById(R.id.laboratory_test_date);
        testreportTextView = findViewById(R.id.laboratory_test_report);

        AppCompatButton addbutton = findViewById(R.id.laboratory_pending_test_add_test_button);
        AppCompatButton closeButton = findViewById(R.id.laboratory_patient_dialog_close_button);

        addbutton.setOnClickListener(view -> {
            laboratoryPendingTestsModel.setTestreport(testreportTextView.getText().toString());

            fragment.onAddtestClicked(laboratoryPendingTestsModel);
            dismiss();

        });
        closeButton.setOnClickListener(view -> {
            dismiss();
        });
        setViews();
    }


    private void setViews() {
        nameTextView.setText(laboratoryPendingTestsModel.getFirstname() + " " + laboratoryPendingTestsModel.getLastname());
        laboratoryidTextView.setText(laboratoryPendingTestsModel.getLaboratoryid());
        testidTextView.setText(laboratoryPendingTestsModel.getTestid());
        visitidTextView.setText(laboratoryPendingTestsModel.getVisitid());
        testnameTextView.setText(laboratoryPendingTestsModel.getTestname());
        testdateTextView.setText(laboratoryPendingTestsModel.getTdate());
        testreportTextView.setText(laboratoryPendingTestsModel.getTestreport());
    }
}
