package com.example.ehr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

public class sb_ProviderProfileActivity extends AppCompatActivity {

    UserModel user;
    public String userid1="";
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_provider_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.provider_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(ProviderActivity.this,"You clicked on the icon",Toast.LENGTH_SHORT).show();
                showMenu(v);
            }
        });
        user = (UserModel) getIntent().getSerializableExtra("user");
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.provider_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
        fetchProfileData();
    }

    public void fetchProfileData()
    {
        user = (UserModel) getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        String baseUrl = "https://ssb4235.uta.cloud/sb_getproviderprofile.php";
        EditText e1 = (EditText)findViewById(R.id.firstname);
        EditText e2 = (EditText)findViewById(R.id.lastname);
        EditText e3 = (EditText)findViewById(R.id.email);
        EditText e4 = (EditText)findViewById(R.id.password);
        EditText e5 = (EditText)findViewById(R.id.contact);
        EditText e6 = (EditText)findViewById(R.id.address1);
        EditText e7 = (EditText)findViewById(R.id.address2);
        EditText e8 = (EditText)findViewById(R.id.city);
        EditText e9 = (EditText)findViewById(R.id.state);
        EditText e10 = (EditText)findViewById(R.id.zip);
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
                        String firstname= resultObj.getString("firstname");
                        String lastname= resultObj.getString("lastname");
                        String contact= resultObj.getString("contact");
                        String address1= resultObj.getString("address1");
                        String address2= resultObj.getString("address2");
                        String city= resultObj.getString("city");
                        String state= resultObj.getString("state");
                        String zip= resultObj.getString("zip");
                        String emailid=resultObj.getString("emailid");
                        String password=resultObj.getString("password");
                        e1.setText(firstname,EditText.BufferType.EDITABLE);
                        e2.setText(lastname,EditText.BufferType.EDITABLE);
                        e3.setText(emailid,EditText.BufferType.EDITABLE);
                        e4.setText(password,EditText.BufferType.EDITABLE);
                        e5.setText(contact,EditText.BufferType.EDITABLE);
                        e6.setText(address1,EditText.BufferType.EDITABLE);
                        e7.setText(address2,EditText.BufferType.EDITABLE);
                        e8.setText(city,EditText.BufferType.EDITABLE);
                        e9.setText(state,EditText.BufferType.EDITABLE);
                        e10.setText(zip,EditText.BufferType.EDITABLE);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "some error 2", Toast.LENGTH_LONG).show();
                }
                //return resultObj;
            }
            @Override
            protected JSONObject doInBackground(String... strings)
            {

                System.out.println("inside doInBackground method");
                StringBuilder result = new StringBuilder();
                try {
                    String userid=user.getUserid();
                    URL url =new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("providerid", "UTF-8") + "=" +
                            URLEncoder.encode(userid, "UTF-8");
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
    public void updateProvideProfile(View view)
    {
        user = (UserModel) getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        String baseUrl = "https://ssb4235.uta.cloud/sb_updateproviderprofile.php";
        EditText e1 = (EditText)findViewById(R.id.firstname);
        EditText e2 = (EditText)findViewById(R.id.lastname);
        EditText e3 = (EditText)findViewById(R.id.email);
        EditText e4 = (EditText)findViewById(R.id.password);
        EditText e5 = (EditText)findViewById(R.id.contact);
        EditText e6 = (EditText)findViewById(R.id.address1);
        EditText e7 = (EditText)findViewById(R.id.address2);
        EditText e8 = (EditText)findViewById(R.id.city);
        EditText e9 = (EditText)findViewById(R.id.state);
        EditText e10 = (EditText)findViewById(R.id.zip);
        class dbprocess extends AsyncTask<String, Void, JSONObject> {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                       // return resultObj;
                    } else {
                        String firstname = resultObj.getString("firstname");
                        String lastname = resultObj.getString("lastname");
                        String contact = resultObj.getString("contact");
                        String address1 = resultObj.getString("address1");
                        String address2 = resultObj.getString("address2");
                        String city = resultObj.getString("city");
                        String state = resultObj.getString("state");
                        String zip = resultObj.getString("zip");
                        String emailid = resultObj.getString("emailid");
                        String password = resultObj.getString("password");
                        e1.setText(firstname, EditText.BufferType.EDITABLE);
                        e2.setText(lastname, EditText.BufferType.EDITABLE);
                        e3.setText(emailid, EditText.BufferType.EDITABLE);
                        e4.setText(password, EditText.BufferType.EDITABLE);
                        e5.setText(contact, EditText.BufferType.EDITABLE);
                        e6.setText(address1, EditText.BufferType.EDITABLE);
                        e7.setText(address2, EditText.BufferType.EDITABLE);
                        e8.setText(city, EditText.BufferType.EDITABLE);
                        e9.setText(state, EditText.BufferType.EDITABLE);
                        e10.setText(zip, EditText.BufferType.EDITABLE);
                        Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_LONG).show();
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
                    String firstname= e1.getText().toString();
                    String lastname= e2.getText().toString();
                    String emailid=e3.getText().toString();
                    String password=e4.getText().toString();
                    String contact= e5.getText().toString();
                    String address1= e6.getText().toString();
                    String address2= e7.getText().toString();
                    String city= e8.getText().toString();
                    String state= e9.getText().toString();
                    String zip= e10.getText().toString();
                    String userid = user.getUserid();
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
                            URLEncoder.encode("firstname", "UTF-8") + "=" +
                            URLEncoder.encode(firstname, "UTF-8") + "&" +
                            URLEncoder.encode("lastname", "UTF-8") + "=" +
                            URLEncoder.encode(lastname, "UTF-8") + "&" +
                            URLEncoder.encode("emailid", "UTF-8") + "=" +
                            URLEncoder.encode(emailid, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" +
                            URLEncoder.encode(password, "UTF-8") + "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" +
                            URLEncoder.encode(contact, "UTF-8") + "&" +
                            URLEncoder.encode("address1", "UTF-8") + "=" +
                            URLEncoder.encode(address1, "UTF-8") + "&" +
                            URLEncoder.encode("address2", "UTF-8") + "=" +
                            URLEncoder.encode(address2, "UTF-8") + "&" +
                            URLEncoder.encode("city", "UTF-8") + "=" +
                            URLEncoder.encode(city, "UTF-8") + "&" +
                            URLEncoder.encode("state", "UTF-8") + "=" +
                            URLEncoder.encode(state, "UTF-8") + "&" +
                            URLEncoder.encode("zip", "UTF-8") + "=" +
                            URLEncoder.encode(zip, "UTF-8");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        dbprocess obj=new dbprocess();
        obj.execute(baseUrl);
    }

    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_ProviderProfileActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.provider_profile)
                {
                    Intent intent=new Intent(sb_ProviderProfileActivity.this,sb_ProviderProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_schedule)
                {
                    Intent intent=new Intent(sb_ProviderProfileActivity.this,sb_ProviderScheduleActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_appointments)
                {
                    Intent intent=new Intent(sb_ProviderProfileActivity.this,sb_provider_appointment.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_patients)
                {
                    Intent intent=new Intent(sb_ProviderProfileActivity.this,sb_provider_patientsearch.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_analytics)
                {
                    Intent intent=new Intent(sb_ProviderProfileActivity.this,sb_provider_analytics.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_logout)
                {
                    Intent intent=new Intent(sb_ProviderProfileActivity.this,LoginActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
}