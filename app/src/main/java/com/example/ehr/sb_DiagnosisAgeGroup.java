package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sb_DiagnosisAgeGroup extends AppCompatActivity {
    NavController navController;
    UserModel user;
    RecyclerView review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_diagnosis_age_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.provider_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
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

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.provider_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
        review=findViewById(R.id.recview);
        review.setLayoutManager(new LinearLayoutManager(this));
        processData();
    }
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_DiagnosisAgeGroup.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.provider_profile)
                {
                    Intent intent=new Intent(sb_DiagnosisAgeGroup.this,sb_ProviderProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                }
                else if(menuItem.getItemId()==R.id.provider_schedule)
                {
                    Intent intent=new Intent(sb_DiagnosisAgeGroup.this,sb_ProviderScheduleActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_appointments)
                {
                    Intent intent=new Intent(sb_DiagnosisAgeGroup.this,sb_provider_appointment.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_patients)
                {
                    Intent intent=new Intent(sb_DiagnosisAgeGroup.this,sb_provider_patientsearch.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_analytics)
                {
                    Intent intent=new Intent(sb_DiagnosisAgeGroup.this,sb_provider_analytics.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_logout)
                {
                    Intent intent=new Intent(sb_DiagnosisAgeGroup.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        String a1=getIntent().getStringExtra("age1");
        String a2=getIntent().getStringExtra("age2");
        int age1=Integer.parseInt(a1);
        int age2=Integer.parseInt(a2);
        String val="age1="+a1+"&$age2="+a2;
        Call<List<sb_ResponseModel_Diagnosisagegroup>> call=sb_apiControllerDiagnosisagegroup
                .getInstance()
                .getApi()
                .getData(a1,a2);
        call.enqueue(new Callback<List<sb_ResponseModel_Diagnosisagegroup>>() {
            @Override
            public void onResponse(Call<List<sb_ResponseModel_Diagnosisagegroup>> call, Response<List<sb_ResponseModel_Diagnosisagegroup>> response) {
                List<sb_ResponseModel_Diagnosisagegroup> data=response.body();
                sb_myadapterDiagnosisagegroup adapter=new sb_myadapterDiagnosisagegroup(data);
                user = (UserModel) getIntent().getSerializableExtra("user");
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                review.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<sb_ResponseModel_Diagnosisagegroup>> call, Throwable t) {
                System.out.println("Error is: "+t.toString());
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}