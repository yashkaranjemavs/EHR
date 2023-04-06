package com.example.ehr;


import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;


public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText firstname;
    EditText lastname;
    EditText emailid;
    EditText gender;
    TextView dob;
    EditText contact;
    EditText addressline1;
    EditText addressline2;
    EditText city;
    EditText state;
    EditText zip;
    EditText password;
    EditText confirmPassword;
    TextView errorText;
    AppCompatButton registerBtn;
    TextView signIn;
    View scrollView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scrollView = inflater.inflate(R.layout.fragment_register, container, false);

        firstname = scrollView.findViewById(R.id.patient_register_firstname);
        lastname = scrollView.findViewById(R.id.patient_register_lastname);
        emailid = scrollView.findViewById(R.id.patient_register_email);
        gender = scrollView.findViewById(R.id.patient_register_gender);
        dob = scrollView.findViewById(R.id.patient_register_dob);
        contact = scrollView.findViewById(R.id.patient_register_contact);
        addressline1 = scrollView.findViewById(R.id.patient_register_address1);
        addressline2 = scrollView.findViewById(R.id.patient_register_address2);
        city = scrollView.findViewById(R.id.patient_register_city);
        state = scrollView.findViewById(R.id.patient_register_state);
        zip = scrollView.findViewById(R.id.patient_register_zip);
        password = scrollView.findViewById(R.id.patient_register_password);
        confirmPassword = scrollView.findViewById(R.id.patient_register_confirm_password);

        errorText = scrollView.findViewById(R.id.patient_register_error_text);

        signIn = scrollView.findViewById(R.id.patient_register_signin_button);
        registerBtn = scrollView.findViewById(R.id.patient_register_button);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Navigation.findNavController(view).navigate(R.id.action_patientRegisterFragment_to_login, bundle);
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstnameText = firstname.getText().toString();
                String lastnameText = lastname.getText().toString();
                String emailidText = emailid.getText().toString();
                String genderText = gender.getText().toString();
                String dobText = dob.getText().toString();
                String contactText = contact.getText().toString();
                String addressline1Text = addressline1.getText().toString();
                String addressline2Text = addressline2.getText().toString();
                String cityText = city.getText().toString();
                String stateText = state.getText().toString();
                String zipText = zip.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();

                errorText.setText("");

                PatientUserModel patient = new PatientUserModel(
                        null, emailidText, passwordText, confirmPasswordText, firstnameText, lastnameText,
                        genderText, dobText, contactText, addressline1Text, addressline2Text, cityText, stateText, zipText);
                if (isInputsValid(patient)) {
                    registerPatient(patient);
                }
            }
        });
        return scrollView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onRegisterSuccess() {
        firstname.setText("");
        lastname.setText("");
        gender.setText("");
        dob.setText("");
        emailid.setText("");
        password.setText("");
        confirmPassword.setText("");
        contact.setText("");
        addressline1.setText("");
        addressline2.setText("");
        city.setText("");
        state.setText("");
        zip.setText("");
        errorText.setText("");

        Toast.makeText(getActivity(), "Patient registered", Toast.LENGTH_LONG).show();
        Navigation.findNavController(scrollView).navigate(R.id.action_patientRegisterFragment_to_login);
    }

    public void registerPatient(PatientUserModel patient) {

        String baseURL = BaseUrl.baseUrl + "/patientRegistration.php";
        class dbprocess extends AsyncTask<String, Void, JSONObject> {
            @Override
            protected void onPostExecute(JSONObject resultObj) {
                try {
                    if (resultObj.has("error")) {
                        onRegisterFailed(resultObj.getString("error"));
                    } else {
                        onRegisterSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected JSONObject doInBackground(String... strings) {
                try {

                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(patient.getFirstname(), "UTF-8") + "&" +
                            URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(patient.getLastname(), "UTF-8") + "&" +
                            URLEncoder.encode("emailid", "UTF-8") + "=" + URLEncoder.encode(patient.getEmailId(), "UTF-8") + "&" +
                            URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(patient.getGender(), "UTF-8") + "&" +
                            URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(patient.getDob(), "UTF-8") + "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(patient.getContact(), "UTF-8") + "&" +
                            URLEncoder.encode("addressline1", "UTF-8") + "=" + URLEncoder.encode(patient.getAddress1(), "UTF-8") + "&" +
                            URLEncoder.encode("addressline2", "UTF-8") + "=" + URLEncoder.encode(patient.getAddress2(), "UTF-8") + "&" +
                            URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(patient.getCity(), "UTF-8") + "&" +
                            URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(patient.getZip(), "UTF-8") + "&" +
                            URLEncoder.encode("zip", "UTF-8") + "=" + URLEncoder.encode(patient.getZip(), "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(patient.getPassword(), "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));


                    StringBuilder result = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    conn.disconnect();
                    return new JSONObject(result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
        dbprocess obj = new dbprocess();
        obj.execute(baseURL);
    }

    private boolean isInputsValid(PatientUserModel patient) {
        if (patient.getFirstname().isEmpty()) {
            errorText.setText("First name is required");
            return false;
        }
        if (patient.getLastname().isEmpty()) {
            errorText.setText("Last name is required");
            return false;
        }
        if (patient.getEmailId().isEmpty()) {
            errorText.setText("Email is required");
            return false;
        }
        if (patient.getPassword().isEmpty()) {
            errorText.setText("Password is required");
            return false;
        }
        if (!patient.getPassword().equals(patient.getConfirmPassword())) {
            errorText.setText("Password did not matched");
            return false;
        }
        if (patient.getDob().isEmpty()) {
            errorText.setText("Dob is required");
            return false;
        }
        if (patient.getGender().isEmpty()) {
            errorText.setText("Gender is required");
            return false;
        }

        return true;
    }

    private void openDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dob.setText(year +"-"+ (month + 1) +"-"+ day);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    public void onRegisterFailed(String errorMessage) {
        errorText.setText(errorMessage);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }
}