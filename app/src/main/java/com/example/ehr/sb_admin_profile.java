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

public class sb_admin_profile extends AppCompatActivity {
    NavController navController;
    UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_admin_profile);
        UserModel user = (UserModel) getIntent().getSerializableExtra("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.admin_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
        user = (UserModel) getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        menuIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(ProviderActivity.this,"You clicked on the icon",Toast.LENGTH_SHORT).show();
                showMenu(v);
            }
        });
        fetchProfileData();
    }

    public void updateAdminProfile(View view)
    {
        String baseUrl = "https://ssb4235.uta.cloud/sb_updateadminprofile.php";
        EditText e3 = (EditText)findViewById(R.id.email);
        EditText e4 = (EditText)findViewById(R.id.password);
        class dbprocess extends AsyncTask<String, Void, JSONObject> {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        // return resultObj;
                    } else {

                        String emailid = resultObj.getString("emailid");
                        String password = resultObj.getString("password");
                        e3.setText(emailid, EditText.BufferType.EDITABLE);
                        e4.setText(password, EditText.BufferType.EDITABLE);
                        e3.setEnabled(false);
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
                    String emailid=e3.getText().toString();
                    String password=e4.getText().toString();
                    String userid = "admin@admin.com";
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("emailid", "UTF-8") + "=" +
                            URLEncoder.encode(userid, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" +
                            URLEncoder.encode(password, "UTF-8");
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
        PopupMenu popupmenu=new PopupMenu(sb_admin_profile.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_admin_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.admin_profile)
                {
                    Intent intent=new Intent(sb_admin_profile.this,sb_admin_profile.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_adduser)
                {
                    Intent intent=new Intent(sb_admin_profile.this,sb_admin_addprovider.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_viewusers)
                {
                    Intent intent=new Intent(sb_admin_profile.this,sb_provider_appointment.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_logout)
                {
                    Intent intent=new Intent(sb_admin_profile.this,LoginActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }

    public void fetchProfileData()
    {
        user = (UserModel) getIntent().getSerializableExtra("user");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        String baseUrl = "https://ssb4235.uta.cloud/sb_getadminprofile.php";
        EditText e3 = (EditText)findViewById(R.id.email);
        EditText e4 = (EditText)findViewById(R.id.password);
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
                        String emailid=resultObj.getString("emailid");
                        String password=resultObj.getString("password");
                        e3.setText(emailid,EditText.BufferType.EDITABLE);
                        e4.setText(password,EditText.BufferType.EDITABLE);
                        e3.setEnabled(false);
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
                    String userid="admin@admin.com";
                    URL url =new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("emailid", "UTF-8") + "=" +
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
}