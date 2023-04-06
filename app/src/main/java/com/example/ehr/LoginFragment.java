package com.example.ehr;

import android.content.Intent;
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

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ehr.insurance.InsuranceActivity;




public class LoginFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    TextView email;
    TextView password;
    TextView forgotPassword;
    TextView registerHere;
    TextView errorText;
    Spinner role;
    AppCompatButton loginBtn;
    View scrollView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scrollView = inflater.inflate(R.layout.fragment_login, container, false);

        email = scrollView.findViewById(R.id.email);
        password = scrollView.findViewById(R.id.password);
        forgotPassword = scrollView.findViewById(R.id.forgot_password);
        registerHere = scrollView.findViewById(R.id.register);
        errorText = scrollView.findViewById(R.id.error_text);
        loginBtn = scrollView.findViewById(R.id.login_button);
        role = scrollView.findViewById(R.id.userType);
        role.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity().getBaseContext(), R.array.user_type_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailEditText = scrollView.findViewById(R.id.email);
                Bundle bundle = new Bundle();

                bundle.putString("email", emailEditText.getText().toString());
                Navigation.findNavController(view).navigate(R.id.action_login_to_forgotPassword, bundle);
            }
        });

        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                Navigation.findNavController(view).navigate(R.id.action_login_to_patientRegisterFragment, bundle);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String userTypeText = role.getSelectedItem().toString();

                BackgroundLoginWorker backgroundWorker = new BackgroundLoginWorker(LoginFragment.this);
                backgroundWorker.execute("login", emailText, passwordText, userTypeText);
            }
        });
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

    public void onLoginSuccess(UserModel user) {
        Intent intent;
        String role = user.getRole();

        switch (role) {
            case "Admin":
                intent = new Intent(getActivity(), AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case "Patient":
                intent = new Intent(getActivity(), PatientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case "Provider":
                intent = new Intent(getActivity(), ProviderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case "Laboratory":
                intent = new Intent(getActivity(), LaboratoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case "Pharmacy":
                intent = new Intent(getActivity(), PharmacyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case "Insurance Company":
                intent = new Intent(getActivity(), InsuranceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    }

    public void onLoginFailed(String errorMessage) {
        errorText.setText(errorMessage);
    }
}