package com.example.ehr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

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

public class sb_ViewAppointmentDetailsByID extends AppCompatActivity {
    UserModel user;
    NavController navController;
    public String userid="";
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_view_appointment_details_by_id);
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.provider_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        userid = sharedPreferences.getString("userid","notset");
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
                showMenu(v);
            }
        });
        fetchVisitDataFromDB();
    }
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_ViewAppointmentDetailsByID.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.provider_profile)
                {
                    Intent intent=new Intent(sb_ViewAppointmentDetailsByID.this,sb_ProviderProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                }
                else if(menuItem.getItemId()==R.id.provider_schedule)
                {
                    Intent intent=new Intent(sb_ViewAppointmentDetailsByID.this,sb_ProviderScheduleActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_appointments)
                {
                    Intent intent=new Intent(sb_ViewAppointmentDetailsByID.this,sb_provider_appointment.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_patients)
                {
                    Intent intent=new Intent(sb_ViewAppointmentDetailsByID.this,sb_provider_patientsearch.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_analytics)
                {
                    Intent intent=new Intent(sb_ViewAppointmentDetailsByID.this,sb_provider_analytics.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_logout)
                {
                    Intent intent=new Intent(sb_ViewAppointmentDetailsByID.this,LoginActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();

    }
    public void fetchVisitDataFromDB()
    {
        String baseUrl = BaseUrl.baseUrl + "/sb_getAppointmentDetailsByID.php";
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
        t1=(TextView) findViewById(R.id.t1);
        t2=(TextView) findViewById(R.id.t2);
        t3=(TextView) findViewById(R.id.t3);
        t4=(TextView) findViewById(R.id.t4);
        t5=(TextView) findViewById(R.id.t5);
        t6=(TextView) findViewById(R.id.t6);
        t7=(TextView) findViewById(R.id.t7);
        t8=(TextView) findViewById(R.id.t8);
        t9=(TextView) findViewById(R.id.t9);
        t10=(TextView) findViewById(R.id.t10);
        t11=(TextView) findViewById(R.id.t11);
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
                        String firstname=resultObj.getString("firstname");
                        String lastname=resultObj.getString("lastname");
                        String vdate=resultObj.getString("vdate");
                        String vtime=resultObj.getString("vtime");
                        String diagnosis= resultObj.getString("diagnosis");
                        String symptoms= resultObj.getString("symptoms");
                        String providernotes=resultObj.getString("providernotes");
                        String patientnotes=resultObj.getString("patientnotes");
                        String testname=resultObj.getString("testname");
                        String testreport=resultObj.getString("testreport");
                        String medication=resultObj.getString("medications");
                        String charges=resultObj.getString("charges");
                        String name=firstname+" "+lastname;
                        t1.setText("Patient: "+name);
                        t2.setText("Date: "+vdate);
                        t3.setText("Time: "+vtime);
                        t4.setText("Diagnosis: "+diagnosis);
                        t5.setText("Symptoms: "+symptoms);
                        t6.setText("Patient Notes: "+patientnotes);
                        t7.setText("Provider Notes: "+providernotes);
                        t8.setText("Test Name: "+testname);
                        t9.setText("Test Report: "+testreport);
                        t10.setText("Medications: "+medication);
                        t11.setText("Charges: "+charges);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
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
}