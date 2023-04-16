package com.example.ehr;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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

public class sb_admin_addprovider extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    NavController navController;
    UserModel user;
    public String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_admin_addprovider);
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
        EditText dateEdt = findViewById(R.id.dob);
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        sb_admin_addprovider.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        String[] users = {"Gender", "Male", "Female"};
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
    }

    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_admin_addprovider.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_admin_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.admin_profile)
                {
                    Intent intent=new Intent(sb_admin_addprovider.this,sb_admin_profile.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_adduser)
                {
                    Intent intent=new Intent(sb_admin_addprovider.this,sb_admin_adduser.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_viewusers)
                {
                    Intent intent=new Intent(sb_admin_addprovider.this,sb_admin_viewusers.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_logout)
                {
                    Intent intent=new Intent(sb_admin_addprovider.this,LoginActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
    public void saveProviderDetails(View view)
    {
        String baseUrl = BaseUrl.baseUrl;
        baseUrl += "/sb_saveproviderdetails.php";
        EditText e1 = (EditText)findViewById(R.id.firstname);
        EditText e2 = (EditText)findViewById(R.id.lastname);
        EditText e3 = (EditText)findViewById(R.id.email);
        EditText e4 = (EditText)findViewById(R.id.dob);
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
                        e2.setText("", EditText.BufferType.EDITABLE);
                        e3.setText("", EditText.BufferType.EDITABLE);
                        e4.setText("", EditText.BufferType.EDITABLE);
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
                    String firstname= e1.getText().toString();
                    String lastname= e2.getText().toString();
                    String emailid=e3.getText().toString();
                    String dob=e4.getText().toString();
                    String contact= e5.getText().toString();
                    String address1= e6.getText().toString();
                    String address2= e7.getText().toString();
                    String city= e8.getText().toString();
                    String state= e9.getText().toString();
                    String zip= e10.getText().toString();
                    //String gender=s1.toString();
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    System.out.println("gender2 is: "+gender);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("firstname", "UTF-8") + "=" +
                            URLEncoder.encode(firstname, "UTF-8") + "&" +
                            URLEncoder.encode("lastname", "UTF-8") + "=" +
                            URLEncoder.encode(lastname, "UTF-8") + "&" +
                            URLEncoder.encode("emailid", "UTF-8") + "=" +
                            URLEncoder.encode(emailid, "UTF-8") + "&" +
                            URLEncoder.encode("dob", "UTF-8") + "=" +
                            URLEncoder.encode(dob, "UTF-8") + "&" +
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
                            URLEncoder.encode("gender", "UTF-8") + "=" +
                            URLEncoder.encode(gender, "UTF-8");
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
        gender= adapterView.getItemAtPosition(i).toString();
        System.out.println("gender1 is: "+gender);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}