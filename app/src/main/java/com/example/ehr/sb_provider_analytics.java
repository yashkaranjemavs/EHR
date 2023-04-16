package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class sb_provider_analytics extends AppCompatActivity {
    NavController navController;
    UserModel user;
    Button button1, button2, button3, button4;
    int age1=0, age2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_provider_analytics);
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
        button1 = (Button) findViewById(R.id.btnMedicines);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedicationsActivity();
            }
        });
        button2 = (Button) findViewById(R.id.btnDiagnosis);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiagnosisActivity();
            }
        });
        button3 = (Button) findViewById(R.id.btnInsurances);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsurancesActivity();
            }
        });
    }
    public void openMedicationsActivity(){
        EditText e1=(EditText) findViewById(R.id.medminage);
        EditText e2=(EditText) findViewById(R.id.medmaxage);
        String age1=e1.getText().toString();
        String age2=e2.getText().toString();
        Intent intent = new Intent(this, sb_Medicinesagegroup.class);
        intent.putExtra("age1", age1);
        intent.putExtra("age2", age2);
        startActivity(intent);
    }
    public void openDiagnosisActivity(){
        EditText e1=(EditText) findViewById(R.id.digminage);
        EditText e2=(EditText) findViewById(R.id.digmaxage);
        String age1=e1.getText().toString();
        String age2=e2.getText().toString();
        Intent intent = new Intent(this, sb_DiagnosisAgeGroup.class);
        intent.putExtra("age1", age1);
        intent.putExtra("age2", age2);
        startActivity(intent);
    }
    public void openInsurancesActivity(){
        Intent intent = new Intent(this, sb_InsuranceCount.class);
        startActivity(intent);
    }

    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_provider_analytics.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.provider_profile)
                {
                    Intent intent=new Intent(sb_provider_analytics.this,sb_ProviderProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                }
                else if(menuItem.getItemId()==R.id.provider_schedule)
                {
                    Intent intent=new Intent(sb_provider_analytics.this,sb_ProviderScheduleActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_appointments)
                {
                    Intent intent=new Intent(sb_provider_analytics.this,sb_provider_appointment.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_patients)
                {
                    Intent intent=new Intent(sb_provider_analytics.this,sb_provider_patientsearch.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_analytics)
                {
                    Intent intent=new Intent(sb_provider_analytics.this,sb_provider_analytics.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.provider_logout)
                {
                    Intent intent=new Intent(sb_provider_analytics.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
}
