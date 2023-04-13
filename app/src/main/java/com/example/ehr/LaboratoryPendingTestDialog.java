package com.example.ehr;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;



public class LaboratoryPendingTestDialog extends Dialog {
    private LaboratoryLandingTestsModel laboratoryLandingTestsModel;

    private TextView laboratoryidTextView,testidTextView,visitidTextView,testnameTextView,testdateTextView,nameTextView;
private EditText testreportTextView;
    public LaboratoryPendingTestDialog(Context ctx, LaboratoryLandingTestsModel laboratoryLandingTestsModel) {
        super(ctx);

        this.laboratoryLandingTestsModel = laboratoryLandingTestsModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.laboratory_pendingtest_dialog);

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
            String testreport = testreportTextView.getText().toString();



        });
        closeButton.setOnClickListener(view -> {
            dismiss();
        });
        setViews();
    }

    private void setViews() {
        nameTextView.setText(laboratoryLandingTestsModel.getFirstname() + " " + laboratoryLandingTestsModel.getLastname());
        laboratoryidTextView.setText(laboratoryLandingTestsModel.getLaboratoryid());
        testidTextView.setText(laboratoryLandingTestsModel.getTestid());
        visitidTextView.setText(laboratoryLandingTestsModel.getVisitid());
        testnameTextView.setText(laboratoryLandingTestsModel.getTestname());
        testdateTextView.setText(laboratoryLandingTestsModel.getTdate());
        testreportTextView.setText(laboratoryLandingTestsModel.getTestreport());
    }
}
