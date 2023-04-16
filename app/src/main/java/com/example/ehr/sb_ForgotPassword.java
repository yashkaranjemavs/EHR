package com.example.ehr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class sb_ForgotPassword extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner role;
    String usertype="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_forgot_password);
        String[] users = {"Admin", "Patient", "Provider","Laboratory","Pharmacy","Insurance Company"};
        Spinner spin = (Spinner) findViewById(R.id.userType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        usertype= adapterView.getItemAtPosition(i).toString();
        System.out.println("gender1 is: "+usertype);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    public void emailPassword(View view)
    {
        String baseUrl = BaseUrl.baseUrl + "/ContactEmail.php";
        EditText e1 = (EditText)findViewById(R.id.email);
        String emailid=e1.getText().toString();
        TextView t1= (TextView) findViewById(R.id.error_text);
        Spinner s1=(Spinner) findViewById(R.id.userType);
        class dbprocess extends AsyncTask<String, Void, JSONObject> {
            protected void onPostExecute(JSONObject resultObj) {
                System.out.println("inside execute method");
                try {
                    if (resultObj.has("error")) {
                        Toast.makeText(getApplicationContext(), "some error 1", Toast.LENGTH_LONG).show();
                        // return resultObj;
                    } else {
                        t1.setText("Password is been emailed");
                        //Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_LONG).show();
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
                    //String gender=s1.toString();
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                            StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("emailid", "UTF-8") + "=" +
                            URLEncoder.encode(emailid, "UTF-8") + "&" +
                            URLEncoder.encode("usertype", "UTF-8") + "=" +
                            URLEncoder.encode(usertype, "UTF-8");
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
}