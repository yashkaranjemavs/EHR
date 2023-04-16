package com.example.ehr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
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
import java.util.List;

public class sb_ScheduledAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    UserModel user;
    NavController navController;
    public String userid="";
    public String labname="";
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_scheduled_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.provider_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        /*user = (UserModel) getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        userid=user.getUserid(); */
        //SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        user= (UserModel) getIntent().getSerializableExtra("user");
        userid=user.getUserid();
        //userid=sharedPreferences.getString("userid",user.getUserid());

        //System.out.println("value in scheduled is: "+userid);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.provider_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
        menuIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(ProviderActivity.this,"You clicked on the icon",Toast.LENGTH_SHORT).show();
                showMenu(v);
            }
        });
        Spinner s2=(Spinner)findViewById(R.id.Laboratory);
        s2.setOnItemSelectedListener(this);
        Spinner s4=(Spinner)findViewById(R.id.Pharmacy);
        s4.setOnItemSelectedListener(this);
        Button btn1, btn2, btn3, btn4;
        btn1=(Button)findViewById(R.id.btnsave1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDSN();
            }
        });
        btn2=(Button)findViewById(R.id.btnsave2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLabTest();
            }
        });
        btn3=(Button)findViewById(R.id.btnsave3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedications();
            }
        });
        btn4=(Button)findViewById(R.id.btnsave4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCharges();
            }
        });
        Spinner sp1=(Spinner)findViewById(R.id.Laboratory);
        View v1=sp1.getEmptyView();
        processData();
        getLaboratoryList(v1);
        Spinner sp2=(Spinner)findViewById(R.id.Pharmacy);
        View v2=sp1.getEmptyView();
        getPharmacyList(v2);
        fetchVisitDataFromDB();
        //fetchLabtestFromDB();
        //fetchMedicationsFromDB();
        //fetchVisitChargesFromDB();
    }
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_ScheduledAppointment.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.provider_profile)
                {
                    Intent intent=new Intent(sb_ScheduledAppointment.this,sb_ProviderProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                }
                else if(menuItem.getItemId()==R.id.provider_schedule)
                {
                    Intent intent=new Intent(sb_ScheduledAppointment.this,sb_ProviderScheduleActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_appointments)
                {
                    Intent intent=new Intent(sb_ScheduledAppointment.this,sb_provider_appointment.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_patients)
                {
                    Intent intent=new Intent(sb_ScheduledAppointment.this,sb_provider_patientsearch.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_analytics)
                {
                    Intent intent=new Intent(sb_ScheduledAppointment.this,sb_provider_analytics.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_logout)
                {
                    Intent intent=new Intent(sb_ScheduledAppointment.this,LoginActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
    public void processData()
    {
        user = (UserModel) getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        TextView t1=(TextView) findViewById(R.id.t1);
        TextView t2=(TextView) findViewById(R.id.t2);
        String pname=getIntent().getStringExtra("pname");
        String vdatetime=getIntent().getStringExtra("vdatetime");
        String visitid=getIntent().getStringExtra("visitid");
        t1.setText(pname);
        t2.setText(vdatetime);
    }
    public void getLaboratoryList(View view)
    {
        String baseUrl = BaseUrl.baseUrl + "/sb_getlablist.php";
        Spinner sp=(Spinner) findViewById(R.id.Laboratory);
        class dbprocess extends AsyncTask<String, Void, JSONArray>
        {
            protected void onPostExecute(JSONArray resultObj)
            {
                try {
                    List<String> allNames = new ArrayList<String>();
                    for (int i=0; i<resultObj.length(); i++) {
                        JSONObject actor = resultObj.getJSONObject(i);
                        String name = actor.getString("name");
                        allNames.add(name);
                    }
                    Spinner  spinner1 = (Spinner) findViewById(R.id.Laboratory);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                            (sb_ScheduledAppointment.this, android.R.layout.simple_spinner_item,allNames );
                    dataAdapter.setDropDownViewResource
                            (android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(dataAdapter);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "some error", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            @Override
            protected JSONArray doInBackground(String... strings)
            {
                System.out.println("inside doInBackground method");
                StringBuilder result = new StringBuilder();
                try {
                    //String userid=user.getUserid();
                    URL url =new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));
                    String line = "";
                    while ((line = br.readLine()) != null)
                    {
                        result.append(line);
                    }
                    System.out.println("Value returned is: "+result);
                    br.close();
                    inputStream.close();
                    conn.disconnect();
                    JSONArray jarray=new JSONArray(result.toString());
                    return jarray;
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);

    }
    public void getPharmacyList(View view)
    {
        String baseUrl = BaseUrl.baseUrl + "/sb_getPharmacylist.php";
        Spinner sp=(Spinner) findViewById(R.id.Laboratory);
        class dbprocess extends AsyncTask<String, Void, JSONArray>
        {
            protected void onPostExecute(JSONArray resultObj)
            {
                try {
                    List<String> allNames = new ArrayList<String>();
                    for (int i=0; i<resultObj.length(); i++) {
                        JSONObject actor = resultObj.getJSONObject(i);
                        String name = actor.getString("name");
                        allNames.add(name);
                    }
                    Spinner  spinner1 = (Spinner) findViewById(R.id.Pharmacy);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                            (sb_ScheduledAppointment.this, android.R.layout.simple_spinner_item,allNames );
                    dataAdapter.setDropDownViewResource
                            (android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(dataAdapter);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "some error", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            @Override
            protected JSONArray doInBackground(String... strings)
            {
                System.out.println("inside doInBackground method");
                StringBuilder result = new StringBuilder();
                try {
                    //String userid=user.getUserid();
                    URL url =new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));
                    String line = "";
                    while ((line = br.readLine()) != null)
                    {
                        result.append(line);
                    }
                    System.out.println("Value returned is: "+result);
                    br.close();
                    inputStream.close();
                    conn.disconnect();
                    JSONArray jarray=new JSONArray(result.toString());
                    return jarray;
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);

    }
    public void fetchVisitDataFromDB()
    {
        String baseUrl = BaseUrl.baseUrl + "/sb_getvisitbyid.php";
        EditText e1 = (EditText)findViewById(R.id.Diagnosis);
        EditText e2 = (EditText)findViewById(R.id.Symptoms);
        EditText e3 = (EditText)findViewById(R.id.ProviderNotes);
        class dbprocess extends AsyncTask<String, Void, JSONObject>
        {
            protected void onPostExecute(JSONObject resultObj)
            {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        //return resultObj;
                    }
                    else {
                        String diagnosis= resultObj.getString("diagnosis");
                        String symptoms= resultObj.getString("symptoms");
                        String providernotes= resultObj.getString("providernotes");
                        if(diagnosis.equalsIgnoreCase("Default"))
                        {
                            System.out.println("value of diagnosis is: "+diagnosis);
                        }
                        else
                        {
                            e1.setText(diagnosis,EditText.BufferType.EDITABLE);
                            e2.setText(symptoms,EditText.BufferType.EDITABLE);
                            e3.setText(providernotes,EditText.BufferType.EDITABLE);
                            Button btn=(Button)findViewById(R.id.btnsave1);
                            btn.setVisibility(View.GONE);
                            e1.setEnabled(false);
                            e2.setEnabled(false);
                            e3.setEnabled(false);
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "some error 2", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            @Override
            protected JSONObject doInBackground(String... strings) {
                System.out.println("inside doInBackground method");
                StringBuilder result = new StringBuilder();
                try {
                    //String userid=user.getUserid();
                    URL url =new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String visitid=getIntent().getStringExtra("visitid");
                    String post_data = URLEncoder.encode("visitid", "UTF-8") + "=" +
                            URLEncoder.encode(visitid, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));
                    String line = "";
                    while ((line = br.readLine()) != null)
                    {
                        result.append(line);
                    }
                    System.out.println("Value returned is: "+result);
                    br.close();
                    inputStream.close();
                    conn.disconnect();
                    return new JSONObject(result.toString());
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);
    }
    public void addDSN()
    {
        EditText e1, e2, e3;
        e1=(EditText) findViewById(R.id.Diagnosis);
        e2=(EditText) findViewById(R.id.Symptoms);
        e3=(EditText) findViewById(R.id.ProviderNotes);
        String diagnosis=e1.getText().toString();
        String symptoms=e2.getText().toString();
        String providernotes=e3.getText().toString();
        String baseUrl = BaseUrl.baseUrl + "/sb_updatevisitbyid.php";
        String pname=getIntent().getStringExtra("pname");
        String vdatetime=getIntent().getStringExtra("vdatetime");
        String visitid=getIntent().getStringExtra("visitid");
        class dbprocess extends AsyncTask<String, Void, JSONObject>
        {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        // return resultObj;
                    } else {
                        String diagnosis = resultObj.getString("diagnosis");
                        String symptoms = resultObj.getString("symptoms");
                        String providernotes = resultObj.getString("providernotes");
                        String visitid = resultObj.getString("visitid");
                        e1.setText(diagnosis, EditText.BufferType.EDITABLE);
                        e2.setText(symptoms, EditText.BufferType.EDITABLE);
                        e3.setText(providernotes, EditText.BufferType.EDITABLE);
                        Button btn=(Button)findViewById(R.id.btnsave1);
                        btn.setVisibility(View.GONE);
                        e1.setEnabled(false);
                        e2.setEnabled(false);
                        e3.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "some error 2", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            StringBuilder result = new StringBuilder();

            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("providerid", "UTF-8") + "=" +
                            URLEncoder.encode(userid, "UTF-8") + "&" +
                            URLEncoder.encode("visitid", "UTF-8") + "=" +
                            URLEncoder.encode(visitid, "UTF-8") + "&" +
                            URLEncoder.encode("diagnosis", "UTF-8") + "=" +
                            URLEncoder.encode(diagnosis, "UTF-8") + "&" +
                            URLEncoder.encode("symptoms", "UTF-8") + "=" +
                            URLEncoder.encode(symptoms, "UTF-8") + "&" +
                            URLEncoder.encode("providernotes", "UTF-8") + "=" +
                            URLEncoder.encode(providernotes, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));
                    String line = "";

                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println("Value returned is: " + result);
                    br.close();
                    inputStream.close();
                    conn.disconnect();
                    return new JSONObject(result.toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);
    }
    public void addLabTest()
    {
        StringBuilder result = new StringBuilder();
        EditText e1=(EditText) findViewById(R.id.TestName);
        String testname=e1.getText().toString();
        String baseUrl = BaseUrl.baseUrl + "/sb_addlabtest.php";
        String visitid=getIntent().getStringExtra("visitid");
        Spinner s3=(Spinner) findViewById(R.id.Laboratory);
        class dbprocess extends AsyncTask<String, Void, JSONObject>
        {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        // return resultObj;
                    } else {
                        String testname = resultObj.getString("testname");
                        String testreport = resultObj.getString("testreport");
                        String labname1 = labname;
                        String visitid = resultObj.getString("visitid");
                        String togo="Test: "+testname+" Test Report: "+testreport;
                        e1.setText(togo, EditText.BufferType.EDITABLE);
                        Button btn=(Button)findViewById(R.id.btnsave2);
                        btn.setVisibility(View.GONE);
                        s3.setVisibility(View.GONE);
                        e1.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "some error 2", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("visitid", "UTF-8") + "=" +
                            URLEncoder.encode(visitid, "UTF-8") + "&" +
                            URLEncoder.encode("testname", "UTF-8") + "=" +
                            URLEncoder.encode(testname, "UTF-8") + "&" +
                            URLEncoder.encode("labname", "UTF-8") + "=" +
                            URLEncoder.encode(labname, "UTF-8");
                    System.out.println("values for test, visitid:"+visitid+" testname:"+testname+" labname:"+labname);
                    bufferedWriter.write(post_data);
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));
                    String line = "";

                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println("Value returned is: " + result);
                    br.close();
                    inputStream.close();
                    conn.disconnect();
                    return new JSONObject(result.toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);
    }
    public void addMedications()
    {
        StringBuilder result = new StringBuilder();
        EditText e1=(EditText) findViewById(R.id.Medication);
        String medication=e1.getText().toString();
        String baseUrl = BaseUrl.baseUrl + "/sb_addMedication.php";
        String visitid=getIntent().getStringExtra("visitid");
        Spinner s3=(Spinner) findViewById(R.id.Pharmacy);
        class dbprocess extends AsyncTask<String, Void, JSONObject>
        {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        // return resultObj;
                    } else {
                        String medication = resultObj.getString("medication");
                        String status = resultObj.getString("status");
                        String labname1 = labname;
                        String visitid = resultObj.getString("visitid");
                        String togo="Medication: "+medication+" Status: "+status;
                        e1.setText(togo, EditText.BufferType.EDITABLE);
                        Button btn=(Button)findViewById(R.id.btnsave3);
                        btn.setVisibility(View.GONE);
                        s3.setVisibility(View.GONE);
                        e1.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "some error 2", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("visitid", "UTF-8") + "=" +
                            URLEncoder.encode(visitid, "UTF-8") + "&" +
                            URLEncoder.encode("medication", "UTF-8") + "=" +
                            URLEncoder.encode(medication, "UTF-8") + "&" +
                            URLEncoder.encode("pharmacy", "UTF-8") + "=" +
                            URLEncoder.encode(labname, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));
                    String line = "";

                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println("Value returned is: " + result);
                    br.close();
                    inputStream.close();
                    conn.disconnect();
                    return new JSONObject(result.toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);
    }
    public void addCharges()
    {
        StringBuilder result = new StringBuilder();
        EditText e1=(EditText) findViewById(R.id.Charges);
        String charges=e1.getText().toString();
        EditText e2=(EditText) findViewById(R.id.Patientpay);
        String patientpay=e2.getText().toString();
        String baseUrl = BaseUrl.baseUrl + "/sb_addCharges.php";
        String visitid=getIntent().getStringExtra("visitid");
        class dbprocess extends AsyncTask<String, Void, JSONObject>
        {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        // return resultObj;
                    } else {
                        String charges = resultObj.getString("charges");
                        String status = resultObj.getString("status");
                        String visitid = resultObj.getString("visitid");
                        String togo="Charges: "+charges+" Status: "+status;
                        e1.setText(togo, EditText.BufferType.EDITABLE);
                        Button btn=(Button)findViewById(R.id.btnsave4);
                        btn.setVisibility(View.GONE);
                        e1.setEnabled(false);
                        e2.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "some error 2", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("visitid", "UTF-8") + "=" +
                            URLEncoder.encode(visitid, "UTF-8") + "&" +
                            URLEncoder.encode("patientpay", "UTF-8") + "=" +
                            URLEncoder.encode(patientpay, "UTF-8") + "&" +
                            URLEncoder.encode("charges", "UTF-8") + "=" +
                            URLEncoder.encode(charges, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,
                            StandardCharsets.ISO_8859_1));
                    String line = "";

                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println("Value returned is: " + result);
                    br.close();
                    inputStream.close();
                    conn.disconnect();
                    return new JSONObject(result.toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        labname= adapterView.getItemAtPosition(i).toString();
       // Toast.makeText(getApplicationContext(), "Selected Laboratory: "+labname ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}