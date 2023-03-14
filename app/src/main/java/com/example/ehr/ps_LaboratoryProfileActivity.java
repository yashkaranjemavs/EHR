package com.example.ehr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ps_LaboratoryProfileActivity extends AppCompatActivity {
    private static final String baseUrl = "https://pxs9233.uta.cloud/ps_getlabprofile.php";

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_laboratory_profile);
        //end of fetching profile data
        Toolbar toolbar = (Toolbar) findViewById(R.id.laboratory_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(LaboratoryActivity.this,"You clicked on the icon",Toast.LENGTH_SHORT).show();
                showMenu(v);
            }
        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.laboratory_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
        fetchProfileData();
    }

    public void fetchProfileData()
    {
        EditText e1 = (EditText)findViewById(R.id.name);
        EditText e2 = (EditText)findViewById(R.id.email);
        EditText e3 = (EditText)findViewById(R.id.password);
        EditText e4 = (EditText)findViewById(R.id.contact);
        EditText e5 = (EditText)findViewById(R.id.address1);
        EditText e6 = (EditText)findViewById(R.id.address2);
        EditText e7 = (EditText)findViewById(R.id.city);
        EditText e8 = (EditText)findViewById(R.id.state);
        EditText e9 = (EditText)findViewById(R.id.zip);
        class dbprocess extends AsyncTask<String, Void, JSONObject>
        {

            protected void onPostExecute(JSONObject resultObj)
            {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {
                        String firstname= resultObj.getString("name");
                        String contact= resultObj.getString("contact");
                        String address1= resultObj.getString("address1");
                        String address2= resultObj.getString("address2");
                        String city= resultObj.getString("city");
                        String state= resultObj.getString("state");
                        String zip= resultObj.getString("zip");
                        String emailid=resultObj.getString("emailid");
                        String password=resultObj.getString("password");
                        e1.setText(firstname,EditText.BufferType.EDITABLE);
                        e2.setText(emailid,EditText.BufferType.EDITABLE);
                        e3.setText(password,EditText.BufferType.EDITABLE);
                        e4.setText(contact,EditText.BufferType.EDITABLE);
                        e5.setText(address1,EditText.BufferType.EDITABLE);
                        e6.setText(address2,EditText.BufferType.EDITABLE);
                        e7.setText(city,EditText.BufferType.EDITABLE);
                        e8.setText(state,EditText.BufferType.EDITABLE);
                        e9.setText(zip,EditText.BufferType.EDITABLE);
                    }
                }
                catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), "some error 2", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected JSONObject doInBackground(String... strings)
            {

                System.out.println("inside doInBackground method");
                StringBuilder result = new StringBuilder();
                try {
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
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(ps_LaboratoryProfileActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.ps_laboratory_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
// Navigation from Profile page to Profile activity page when clicked profile
                if(menuItem.getItemId()==R.id.laboratory_menuitem_profile)
                {
                    Intent intent=new Intent(ps_LaboratoryProfileActivity.this,ps_LaboratoryProfileActivity.class);
                    startActivity(intent);
                }
// Navigation from Profile page to Tests activity page when clicked tests
                else if(menuItem.getItemId()==R.id.laboratory_menuitem_tests)
                {
                    Intent intent=new Intent(ps_LaboratoryProfileActivity.this,ps_LaboratoryTestsActivity.class);
                    startActivity(intent);
                }
// Navigation from Profile page to login activity page when clicked logout
                else if(menuItem.getItemId()==R.id.laboratory_menuitem_logout)
                {
                    Intent intent=new Intent(ps_LaboratoryProfileActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
}