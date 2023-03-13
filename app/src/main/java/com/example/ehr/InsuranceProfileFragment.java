package com.example.ehr;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

public class InsuranceProfileFragment extends Fragment {
    ProgressBar progressBar;
    TextView errorTextView;
    TextView emailTextView;
    TextView nameTextView;
    TextView contactTextView;
    TextView address1TextView;
    TextView address2TextView;
    TextView cityTextView;
    TextView stateTextView;
    TextView zipTextView;
    LinearLayout detailsLinearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insurance_profile, container, false);
        setHasOptionsMenu(true);

        errorTextView = view.findViewById(R.id.insurance_profile_error);
        progressBar = view.findViewById(R.id.insurance_profile_progress);

        detailsLinearLayout = view.findViewById(R.id.insurance_profile_details);
        nameTextView = view.findViewById(R.id.insurance_profile_name);
        emailTextView = view.findViewById(R.id.insurance_profile_email);
        contactTextView = view.findViewById(R.id.insurance_profile_contact);
        address1TextView = view.findViewById(R.id.insurance_profile_address1);
        address2TextView = view.findViewById(R.id.insurance_profile_address2);
        cityTextView = view.findViewById(R.id.insurance_profile_city);
        stateTextView = view.findViewById(R.id.insurance_profile_state);
        zipTextView = view.findViewById(R.id.insurance_profile_zip);

        if (getArguments() == null) {
            return view;
        }

        UserModel user = (UserModel) getArguments().getSerializable("user");
        String id = user.getUserid();

        errorTextView.setText("");
        errorTextView.setVisibility(View.GONE);
        detailsLinearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        BackgroundInsuranceProfileWorker backgroundWorker = new BackgroundInsuranceProfileWorker(InsuranceProfileFragment.this);
        backgroundWorker.execute("get_profile", id);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.insurance_menu);
        if(item!=null)
            item.setVisible(false);
    }

    public void onSuccess(InsuranceCompanyModel insuranceCompany) {
        progressBar.setVisibility(View.GONE);
        detailsLinearLayout.setVisibility(View.VISIBLE);

        nameTextView.setText(insuranceCompany.getName());
        emailTextView.setText(insuranceCompany.getEmailId());
        contactTextView.setText(insuranceCompany.getContact());
        address1TextView.setText(insuranceCompany.getAddress1());
        address2TextView.setText(insuranceCompany.getAddress2());
        cityTextView.setText(insuranceCompany.getCity());
        stateTextView.setText(insuranceCompany.getState());
        zipTextView.setText(insuranceCompany.getZip());
    }

    public void onFailed(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);

        errorTextView.setText(errorMessage);
    }
}