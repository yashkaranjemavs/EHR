package com.example.ehr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

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
public class sb_admin_addfacility extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    NavController navController;
    UserModel user;
    public String facility="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_admin_addfacility);
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.admin_nav_host);
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
        String[] users = {"Facility Type", "Laboratory", "Pharmacy", "Insurance"};
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
    }

    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_admin_addfacility.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_admin_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.admin_profile)
                {
                    Intent intent=new Intent(sb_admin_addfacility.this,sb_admin_profile.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_adduser)
                {
                    Intent intent=new Intent(sb_admin_addfacility.this,sb_admin_adduser.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_viewusers)
                {
                    Intent intent=new Intent(sb_admin_addfacility.this,sb_admin_viewusers.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_logout)
                {
                    Intent intent=new Intent(sb_admin_addfacility.this,LoginActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
    public void saveFacilityDetails(View view)
    {
        String baseUrl = "https://ssb4235.uta.cloud/sb_savefacilitydetails.php";
        EditText e1 = (EditText)findViewById(R.id.name);
        EditText e3 = (EditText)findViewById(R.id.email);
        EditText e5 = (EditText)findViewById(R.id.contact);
        EditText e6 = (EditText)findViewById(R.id.address1);
        EditText e7 = (EditText)findViewById(R.id.address2);
        EditText e8 = (EditText)findViewById(R.id.city);
        EditText e9 = (EditText)findViewById(R.id.state);
        EditText e10 = (EditText)findViewById(R.id.zip);
        Spinner s1=(Spinner) findViewById(R.id.spinner1);
        class dbprocess extends AsyncTask<String, Void, JSONObject> {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        // return resultObj;
                    } else {
                        e1.setText("", EditText.BufferType.EDITABLE);
                        e3.setText("", EditText.BufferType.EDITABLE);
                        e5.setText("", EditText.BufferType.EDITABLE);
                        e6.setText("", EditText.BufferType.EDITABLE);
                        e7.setText("", EditText.BufferType.EDITABLE);
                        e8.setText("", EditText.BufferType.EDITABLE);
                        e9.setText("", EditText.BufferType.EDITABLE);
                        e10.setText("", EditText.BufferType.EDITABLE);
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
                    String name= e1.getText().toString();
                    String emailid=e3.getText().toString();
                    String contact= e5.getText().toString();
                    String address1= e6.getText().toString();
                    String address2= e7.getText().toString();
                    String city= e8.getText().toString();
                    String state= e9.getText().toString();
                    String zip= e10.getText().toString();
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("name", "UTF-8") + "=" +
                            URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("emailid", "UTF-8") + "=" +
                            URLEncoder.encode(emailid, "UTF-8") + "&" +
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
                            URLEncoder.encode(zip, "UTF-8") + "&" +
                            URLEncoder.encode("facility", "UTF-8") + "=" +
                            URLEncoder.encode(facility, "UTF-8");
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        facility= adapterView.getItemAtPosition(i).toString();
        //System.out.println("gender1 is: "+gender);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}