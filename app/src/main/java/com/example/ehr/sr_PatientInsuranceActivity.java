package com.example.ehr;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class sr_PatientInsuranceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    UserModel user;
    TextView insurance_name, insurance_email, insurance_contact, insurance_expiry, insurance_status;
    String insuranceNameText, insuranceEmailText,  insuranceContactText, insuranceExpiryText, insuranceStatusText, selectedInsuranceId;
    LinearLayout insuranceView, addInsuranceForm;
    Button disableInsuranceButton, addInsuranceButton;
    Spinner insuranceNameSpinner;
    ArrayAdapter<CharSequence> adapter;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_patientinsurance);

        user = (UserModel) getIntent().getSerializableExtra("user");

        Toolbar toolbar = findViewById(R.id.patient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Insurance");

        ImageView menuIcon = findViewById(R.id.menuIcon);

        insuranceView = findViewById(R.id.patientInsuranceContent);
        addInsuranceForm = findViewById(R.id.patientAddInsuranceForm);

        disableInsuranceButton = findViewById(R.id.patientInsuranceDisableButton);
        addInsuranceButton = findViewById(R.id.patientAddInsuranceButton);

        insuranceNameSpinner = (Spinner) findViewById(R.id.patient_insurance_name_spinner);
        insuranceNameSpinner.setOnItemSelectedListener(this);

        insurance_name = findViewById(R.id.patient_insurance_name);
        insurance_email =findViewById(R.id.patient_insurance_email);
        insurance_contact =findViewById(R.id.patient_insurance_contact);
        insurance_expiry = findViewById(R.id.patient_insurance_expiry);
        insurance_status = findViewById(R.id.patient_insurance_status);

        insuranceNameText = insurance_name.getText().toString();
        insuranceEmailText = insurance_email.getText().toString();
        insuranceContactText = insurance_contact.getText().toString();
        insuranceExpiryText = insurance_expiry.getText().toString();
        insuranceStatusText = insurance_status.getText().toString();

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        disableInsuranceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableInsurance(v);
            }
        });

        addInsuranceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInsurance(v);
            }
        });

        getActiveInsurance();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        PatientInsuranceNameModel insurance = (PatientInsuranceNameModel) adapterView.getItemAtPosition(position);

        if (insurance.getInsurerid() == null) {
            TextView hint = (TextView) adapterView.getChildAt(0);
            if (hint != null) {
                hint.setTextColor(Color.parseColor("#909CB4"));
                hint.setTextSize(19);
            }
        }

        selectedInsuranceId = insurance.getInsurerid();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getActiveInsurance() {
        insuranceView.setVisibility(View.GONE);
        addInsuranceForm.setVisibility(View.GONE);
        dbprocess dbProcessObj = new dbprocess();
        dbProcessObj.execute("getActiveInsurance", user.getUserid());
    }

    private void getInsuranceNames() {
        dbprocess dbProcessObj = new dbprocess();
        dbProcessObj.execute("getInsuranceNames");
    }

    private void disableInsurance(View view) {
        dbprocess dbProcessObj = new dbprocess();
        dbProcessObj.execute("disableInsurance", user.getUserid());
    }

    private void addInsurance(View view) {
        if (selectedInsuranceId == null) {
            Toast.makeText(sr_PatientInsuranceActivity.this,"Please select an insurance",Toast.LENGTH_LONG).show();
            return;
        }

        dbprocess dbProcessObj = new dbprocess();
        dbProcessObj.execute("addInsurance", user.getUserid(), selectedInsuranceId);
    }

    class dbprocess extends AsyncTask<String, Void, String>
    {
        String actionType;

        @Override
        protected String doInBackground(String... params) {
            actionType = params[0];
            String baseUrl = "https://sxr4177.uta.cloud/";
            try {
                if (actionType.equalsIgnoreCase("getActiveInsurance")) {
                    String patientid = params[1];
                    String urlString = baseUrl + "sr_getPatientInsurance.php?patientid=" + patientid;

                    URL url = new URL(urlString);

                    return handleRequest(url, "GET", null);
                } else if (actionType.equalsIgnoreCase("disableInsurance")) {
                    String patientid = params[1];
                    String urlString = baseUrl + "sr_disableInsurance.php";

                    URL url = new URL(urlString);

                    String post_data = URLEncoder.encode("patientid", "UTF-8") + "=" + URLEncoder.encode(patientid, "UTF-8");
                    return handleRequest(url, "POST", post_data);
                } else if (actionType.equalsIgnoreCase("getInsuranceNames")) {
                    String urlString = baseUrl + "sr_getInsuranceNames.php";
                    URL url = new URL(urlString);

                    return handleRequest(url, "GET", null);
                } else if (actionType.equalsIgnoreCase("addInsurance")) {
                    String patientid = params[1];
                    String insurerid = params[2];

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, 1);
                    String expiry = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

                    String urlString = baseUrl + "sr_addInsurance.php";
                    URL url = new URL(urlString);

                    String post_data = URLEncoder.encode("patientid", "UTF-8") + "=" + URLEncoder.encode(patientid, "UTF-8") + "&" +
                            URLEncoder.encode("insurerid", "UTF-8") + "=" + URLEncoder.encode(insurerid, "UTF-8") + "&" +
                            URLEncoder.encode("expiry", "UTF-8") + "=" + URLEncoder.encode(expiry, "UTF-8");
                    return handleRequest(url, "POST", post_data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String resultString) {
            try {
                if (actionType.equals("getActiveInsurance")) {
                    JSONObject resultObj = new JSONObject(resultString);

                    if(resultObj.has("error"))
                    {
                        Toast.makeText(sr_PatientInsuranceActivity.this,resultObj.getString("error"),Toast.LENGTH_LONG).show();
                    } else if (resultObj.has("insurance") && resultObj.isNull("insurance")) {
                        getInsuranceNames();
                    } else
                    {
                        insuranceView.setVisibility(View.VISIBLE);
                        insurance_name.setText(insuranceNameText.concat(resultObj.getString("name")));
                        insurance_email.setText(insuranceEmailText.concat(resultObj.getString("emailid")));
                        insurance_contact.setText(insuranceContactText.concat(resultObj.getString("contact")));
                        insurance_expiry.setText(insuranceExpiryText.concat(resultObj.getString("expiry")));
                        insurance_status.setText(insuranceStatusText.concat(resultObj.getString("status")));
                    }
                } else if (actionType.equals("disableInsurance")) {
                    JSONObject resultObj = new JSONObject(resultString);

                    if(resultObj.has("error"))
                    {
                        Toast.makeText(sr_PatientInsuranceActivity.this,resultObj.getString("error"),Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(sr_PatientInsuranceActivity.this,resultObj.getString("success"),Toast.LENGTH_SHORT).show();
                        getInsuranceNames();
                    }
                    getActiveInsurance();
                } else if (actionType.equals("getInsuranceNames")) {
                    JSONArray resultArr = new JSONArray(resultString);
                    List<PatientInsuranceNameModel> insurances = new ArrayList<>();

                    insurances.add(new PatientInsuranceNameModel(null, "Select Insurance"));

                    for (int i=0; i<resultArr.length(); i++) {
                        JSONObject appObj = (JSONObject) resultArr.get(i);
                        String insurerid = appObj.getString("insurerid");
                        String name = appObj.getString("name");

                        PatientInsuranceNameModel insurance = new PatientInsuranceNameModel(insurerid, name);
                        insurances.add(insurance);
                    }

                    addInsuranceForm.setVisibility(View.VISIBLE);
                    ArrayAdapter<PatientInsuranceNameModel> adapter = new ArrayAdapter(sr_PatientInsuranceActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, insurances);
                    insuranceNameSpinner.setAdapter(adapter);
                } else if (actionType.equals("addInsurance")) {
                    JSONObject resultObj = new JSONObject(resultString);

                    if (resultObj.has("error")) {
                        Toast.makeText(sr_PatientInsuranceActivity.this, resultObj.getString("error"), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    getActiveInsurance();
                }
            } catch (JSONException e) {
                Toast.makeText(sr_PatientInsuranceActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

        private String handleRequest(URL url, String method, String postData) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod(method);
                httpURLConnection.setDoInput(true);


                if (method == "POST") {
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));


                    bufferedWriter.write(postData);
                    bufferedWriter.close();
                    outputStream.close();
                }

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                        StandardCharsets.ISO_8859_1));

                StringBuilder result = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result.toString();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }

    private void showMenu(View v) {
        PopupMenu popupmenu = new PopupMenu(sr_PatientInsuranceActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.patient_menu, popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemprofile) {
                    sr_PatientInsuranceActivity.this.finish();
                    Intent intent = new Intent(sr_PatientInsuranceActivity.this, sr_PatientProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemhome) {
                    sr_PatientInsuranceActivity.this.finish();
                    Intent intent = new Intent(sr_PatientInsuranceActivity.this, PatientActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemappointments) {
                    sr_PatientInsuranceActivity.this.finish();
                    Intent intent = new Intent(sr_PatientInsuranceActivity.this, sr_PatientAppointmentsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.iteminsurance) {
                    sr_PatientInsuranceActivity.this.finish();
                    Intent intent = new Intent(sr_PatientInsuranceActivity.this, sr_PatientInsuranceActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemlogout) {
                    Intent intent = new Intent(sr_PatientInsuranceActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
}