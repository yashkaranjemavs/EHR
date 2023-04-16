package com.example.ehr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ForgotPasswordFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner role;
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
        role = (Spinner) scrollView.findViewById(R.id.userType);
        role.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity().getBaseContext(), R.array.user_type_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);
        return scrollView;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();

        if (position == 0) {
            TextView hint = (TextView) adapterView.getChildAt(0);
            if (hint != null) {
                hint.setTextColor(Color.parseColor("#909CB4"));
                hint.setTextSize(19);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}