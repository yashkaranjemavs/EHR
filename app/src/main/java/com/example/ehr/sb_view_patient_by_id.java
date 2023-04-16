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

public class sb_view_patient_by_id extends AppCompatActivity {
    UserModel user;
    NavController navController;
    public String userid="";
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_view_patient_by_id);
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
        fetchPatientFromDB();
    }
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_view_patient_by_id.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.provider_profile)
                {
                    Intent intent=new Intent(sb_view_patient_by_id.this,sb_ProviderProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                }
                else if(menuItem.getItemId()==R.id.provider_schedule)
                {
                    Intent intent=new Intent(sb_view_patient_by_id.this,sb_ProviderScheduleActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_appointments)
                {
                    Intent intent=new Intent(sb_view_patient_by_id.this,sb_provider_appointment.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_patients)
                {
                    Intent intent=new Intent(sb_view_patient_by_id.this,sb_provider_patientsearch.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_analytics)
                {
                    Intent intent=new Intent(sb_view_patient_by_id.this,sb_provider_analytics.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_logout)
                {
                    Intent intent=new Intent(sb_view_patient_by_id.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
    public void fetchPatientFromDB()
    {
        String baseUrl = BaseUrl.baseUrl + "/sb_getPatientDetailsByID.php";
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
                        String dob=resultObj.getString("dob");
                        String gender=resultObj.getString("gender");
                        String contact= resultObj.getString("contact");
                        String email= resultObj.getString("emailid");
                        String addressline1=resultObj.getString("addressline1");
                        String addressline2=resultObj.getString("addressline2");
                        String city=resultObj.getString("city");
                        String state=resultObj.getString("state");
                        String zip=resultObj.getString("zip");
                        String address= addressline1+" "+addressline2;
                        String name=firstname+" "+lastname;
                        t1.setText("Patient: "+name);
                        t2.setText("DOB: "+dob);
                        t3.setText("Gender: "+gender);
                        t4.setText("Contact: "+contact);
                        t5.setText("Email: "+email);
                        t6.setText("Address: "+address);
                        t7.setText("City: "+city);
                        t8.setText("State: "+state);
                        t9.setText("Zip: "+zip);
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
                    String patientid=getIntent().getStringExtra("patientid");
                    System.out.println("pid is: "+patientid);
                    String post_data = URLEncoder.encode("patientid", "UTF-8") + "=" +
                            URLEncoder.encode(patientid, "UTF-8");
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