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

public class sb_provider_appointment extends AppCompatActivity {
    NavController navController;
    UserModel user;
    RecyclerView review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_provider_appointment);
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
        PopupMenu popupmenu=new PopupMenu(sb_provider_appointment.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.provider_profile)
                {
                    Intent intent=new Intent(sb_provider_appointment.this,sb_ProviderProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                }
                else if(menuItem.getItemId()==R.id.provider_schedule)
                {
                    Intent intent=new Intent(sb_provider_appointment.this,sb_ProviderScheduleActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_appointments)
                {
                    Intent intent=new Intent(sb_provider_appointment.this,sb_provider_appointment.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_patients)
                {
                    Intent intent=new Intent(sb_provider_appointment.this,sb_provider_patientsearch.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_analytics)
                {
                    Intent intent=new Intent(sb_provider_appointment.this,sb_provider_analytics.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_logout)
                {
                    Intent intent=new Intent(sb_provider_appointment.this,LoginActivity.class);
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
        Call<List<sb_ResponseModel_Appointments>> call=sb_apiControllerAppointmentList
                .getInstance()
                .getApi()
                .getData();
        call.enqueue(new Callback<List<sb_ResponseModel_Appointments>>() {
            @Override
            public void onResponse(Call<List<sb_ResponseModel_Appointments>> call, Response<List<sb_ResponseModel_Appointments>> response) {
                List<sb_ResponseModel_Appointments> data=response.body();
                sb_myadapaterAppointmentList adapter=new sb_myadapaterAppointmentList(data);
                user = (UserModel) getIntent().getSerializableExtra("user");
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                review.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<sb_ResponseModel_Appointments>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
