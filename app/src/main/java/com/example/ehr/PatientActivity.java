package com.example.ehr;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;

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
import java.util.Calendar;


public class PatientActivity extends AppCompatActivity {


    FrameLayout frameLayout;
    NavController navController;

    UserModel user;

    TextView vdate;
    AppCompatButton chooseDate;

    TextView vtime;
    AppCompatButton chooseTime;

    EditText patientnotes;
    AppCompatButton bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        frameLayout = findViewById(R.id.framelayout);


        user = (UserModel) getIntent().getSerializableExtra("user");


        Toolbar toolbar = (Toolbar) findViewById(R.id.patient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient");

        ImageView menuIcon = findViewById(R.id.menuIcon);
        vdate = findViewById(R.id.show_date);
        chooseDate = (AppCompatButton) findViewById(R.id.choose_date);
        vtime = findViewById(R.id.show_time);
        chooseTime = (AppCompatButton) findViewById(R.id.choose_time);
        patientnotes = (EditText) findViewById(R.id.patient_notes);
        bookButton = (AppCompatButton) findViewById(R.id.book_button);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });


        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        vdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeDialog();
            }
        });

        vtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeDialog();
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookAppointment();
            }
        });

    }


    private void openDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                vdate.setText(year +"-"+ (month + 1) +"-"+ day);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void openTimeDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                vtime.setText(hour +":"+ minute);
            }
        }, 0, 0, true);

        timePickerDialog.show();
    }

    private void bookAppointment() {

        String baseURL="https://sxr4177.uta.cloud/bookAppointment.php";

        class dbprocess extends AsyncTask<String, Void, JSONObject>
        {

            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("Inside Execute");
                try {

                    if(resultObj.has("error"))
                    {
                        Toast.makeText(getApplicationContext(),resultObj.getString("error"),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Appointment Booked",Toast.LENGTH_LONG).show();
                        vdate.setText("");
                        vtime.setText("");
                        patientnotes.setText("");

                        Intent intent = new Intent(PatientActivity.this, sr_PatientAppointmentsActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            protected JSONObject doInBackground(String... strings) {
                System.out.println("Inside doInBackground");
                try {
                    String vdateText = vdate.getText().toString();
                    String vtimeText = vtime.getText().toString();
                    String patientnotesText = patientnotes.getText().toString();
                    String patientid = user.getUserid();
                    URL url= new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream=conn.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                    String post_data = URLEncoder.encode("vdate", "UTF-8") + "=" + URLEncoder.encode(vdateText, "UTF-8") + "&" +
                            URLEncoder.encode("vtime", "UTF-8") + "=" + URLEncoder.encode(vtimeText, "UTF-8") + "&" +
                            URLEncoder.encode("patientnotes", "UTF-8") + "=" + URLEncoder.encode(patientnotesText, "UTF-8") + "&" +
                            URLEncoder.encode("patientid", "UTF-8") + "=" + URLEncoder.encode(patientid, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

                    StringBuilder result = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    conn.disconnect();
                    System.out.println(result);
                    return new JSONObject(result.toString());

                } catch(IOException ex) {
                    throw new RuntimeException(ex);
                } catch(Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseURL);
    }


    private void showMenu(View v) {
        PopupMenu popupmenu = new PopupMenu(PatientActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.patient_menu, popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemprofile) {
                    Intent intent = new Intent(PatientActivity.this, sr_PatientProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemhome) {
                    Intent intent = new Intent(PatientActivity.this, PatientActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemappointments) {
                    Intent intent = new Intent(PatientActivity.this, sr_PatientAppointmentsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.iteminsurance) {
                    Intent intent = new Intent(PatientActivity.this, sr_PatientInsuranceActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemlogout) {
                    Intent intent = new Intent(PatientActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }

}


