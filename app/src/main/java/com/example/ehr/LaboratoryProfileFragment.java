package com.example.ehr;

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

import com.example.ehr.BackgroundLaboratoryProfileWorker;

public class LaboratoryProfileFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_laboratory_profile, container, false);

        errorTextView = view.findViewById(R.id.laboratory_profile_error);
        progressBar = view.findViewById(R.id.laboratory_profile_progress);

        updateErrorTextView = view.findViewById(R.id.laboratory_profile_update_error);

        detailsLinearLayout = view.findViewById(R.id.laboratory_profile_details);
        nameTextView = view.findViewById(R.id.laboratory_profile_name);
        emailTextView = view.findViewById(R.id.laboratory_profile_email);
        passwordTextView = view.findViewById(R.id.laboratory_profile_password);
        contactTextView = view.findViewById(R.id.laboratory_profile_contact);
        address1TextView = view.findViewById(R.id.laboratory_profile_address1);
        address2TextView = view.findViewById(R.id.laboratory_profile_address2);
        cityTextView = view.findViewById(R.id.laboratory_profile_city);
        stateTextView = view.findViewById(R.id.laboratory_profile_state);
        zipTextView = view.findViewById(R.id.laboratory_profile_zip);
        updateBtn = view.findViewById(R.id.laboratory_profile_update_button);

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


        BackgroundLaboratoryProfileWorker backgroundWorker = new BackgroundLaboratoryProfileWorker(
                LaboratoryProfileFragment.this);
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

            LaboratoryUserModel lab = new LaboratoryUserModel(
                    id, email, password, name, contact, address1, address2, city, state, zip);

            BackgroundLaboratoryProfileWorker backgroundWorker2 = new BackgroundLaboratoryProfileWorker(
                    LaboratoryProfileFragment.this);
            backgroundWorker2.execute("update_profile", lab);
        });

        return view;
    }

    public void onLoadSuccess(LaboratoryUserModel lab) {
        updateErrorTextView.setText("");
        errorTextView.setText("");
        errorTextView.setVisibility(View.GONE);
        detailsLinearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        nameTextView.setText(lab.getName());
        emailTextView.setText(lab.getEmailId());
        passwordTextView.setText(lab.getPassword());
        contactTextView.setText(lab.getContact());
        address1TextView.setText(lab.getAddress1());
        address2TextView.setText(lab.getAddress2());
        cityTextView.setText(lab.getCity());
        stateTextView.setText(lab.getState());
        zipTextView.setText(lab.getZip());
    }

    public void onLoadFailed(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);

        errorTextView.setText(errorMessage);
    }

    public void onUpdate(String errorMessage, LaboratoryUserModel lab) {
        Toast.makeText(getActivity(), "Data saved successfully", Toast.LENGTH_SHORT).show();
        onLoadSuccess(lab);
        updateErrorTextView.setText(errorMessage);
    }
}