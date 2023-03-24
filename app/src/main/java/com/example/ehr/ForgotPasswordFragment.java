package com.example.ehr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class ForgotPasswordFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View scrollView = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        EditText emailEditText = scrollView.findViewById(R.id.email);
        String email = getArguments() != null ? getArguments().getString("email") : "";
        emailEditText.setText(email);

        return scrollView;
    }
}