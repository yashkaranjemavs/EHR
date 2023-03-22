package com.example.ehr.insurance;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehr.R;
import com.example.ehr.UserModel;
import com.example.ehr.insurance.model.InsuranceProfileModel;
import com.example.ehr.insurance.worker.BackgroundInsuranceProfileWorker;

public class InsuranceProfileFragment extends Fragment {
    UserModel user;
    ProgressBar progressBar;
    TextView errorTextView;
    TextView updateErrorTextView;
    EditText emailTextView;
    EditText passwordTextView;
    EditText nameTextView;
    EditText contactTextView;
    EditText address1TextView;
    EditText address2TextView;
    EditText cityTextView;
    EditText stateTextView;
    EditText zipTextView;
    LinearLayout detailsLinearLayout;
    AppCompatButton updateBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insurance_profile, container, false);

        errorTextView = view.findViewById(R.id.insurance_profile_error);
        progressBar = view.findViewById(R.id.insurance_profile_progress);

        updateErrorTextView = view.findViewById(R.id.insurance_profile_update_error);

        detailsLinearLayout = view.findViewById(R.id.insurance_profile_details);
        nameTextView = view.findViewById(R.id.insurance_profile_name);
        emailTextView = view.findViewById(R.id.insurance_profile_email);
        passwordTextView = view.findViewById(R.id.insurance_profile_password);
        contactTextView = view.findViewById(R.id.insurance_profile_contact);
        address1TextView = view.findViewById(R.id.insurance_profile_address1);
        address2TextView = view.findViewById(R.id.insurance_profile_address2);
        cityTextView = view.findViewById(R.id.insurance_profile_city);
        stateTextView = view.findViewById(R.id.insurance_profile_state);
        zipTextView = view.findViewById(R.id.insurance_profile_zip);

        updateBtn = view.findViewById(R.id.insurance_profile_update_button);

        if (getArguments() == null) {
            return view;
        }

        user = (UserModel) getArguments().getSerializable("user");
        String id = user.getUserid();

        updateErrorTextView.setText("");
        errorTextView.setText("");
        errorTextView.setVisibility(View.GONE);
        detailsLinearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        BackgroundInsuranceProfileWorker backgroundWorker = new BackgroundInsuranceProfileWorker(
                InsuranceProfileFragment.this);
        backgroundWorker.execute("get_profile", id);


        updateBtn.setOnClickListener(view1 -> {
            updateErrorTextView.setText("");
            errorTextView.setText("");
            errorTextView.setVisibility(View.GONE);
            detailsLinearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            String name = nameTextView.getText().toString();
            String email = emailTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            String contact = contactTextView.getText().toString();
            String address1 = address1TextView.getText().toString();
            String address2 = address2TextView.getText().toString();
            String city = cityTextView.getText().toString();
            String state = stateTextView.getText().toString();
            String zip = zipTextView.getText().toString();

            InsuranceProfileModel insuranceProfileModel = new InsuranceProfileModel(
                    id, email, password, name, contact, address1, address2, city, state, zip);

            BackgroundInsuranceProfileWorker backgroundWorker2 = new BackgroundInsuranceProfileWorker(
                    InsuranceProfileFragment.this);
            backgroundWorker2.execute("update_profile", insuranceProfileModel);
        });

        return view;
    }

    public void onLoadSuccess(InsuranceProfileModel insuranceProfile) {
        progressBar.setVisibility(View.GONE);
        detailsLinearLayout.setVisibility(View.VISIBLE);

        updateErrorTextView.setText("");

        nameTextView.setText(insuranceProfile.getName());
        emailTextView.setText(insuranceProfile.getEmailId());
        passwordTextView.setText(insuranceProfile.getPassword());
        contactTextView.setText(insuranceProfile.getContact());
        address1TextView.setText(insuranceProfile.getAddress1());
        address2TextView.setText(insuranceProfile.getAddress2());
        cityTextView.setText(insuranceProfile.getCity());
        stateTextView.setText(insuranceProfile.getState());
        zipTextView.setText(insuranceProfile.getZip());
    }

    public void onLoadFailed(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);

        errorTextView.setText(errorMessage);
    }

    public void onUpdate(String errorMessage, InsuranceProfileModel insuranceProfile) {
        if (errorMessage.equals("")) {
            Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
        onLoadSuccess(insuranceProfile);
        updateErrorTextView.setText(errorMessage);
    }
}