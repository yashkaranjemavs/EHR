package com.example.ehr;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


public class PatientActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    NavController navController;

    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        frameLayout = findViewById(R.id.framelayout);

        user = (UserModel) getIntent().getSerializableExtra("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.patient_toolbar);
        setSupportActionBar(toolbar);

        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.patient_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
    }

    private void showMenu(View v) {
        PopupMenu popupmenu = new PopupMenu(PatientActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.patient_menu, popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemprofile) {
                    Intent intent = new Intent(PatientActivity.this, sr_PatientProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemappointments) {
                    Intent intent = new Intent(PatientActivity.this, sr_PatientAppointmentsActivity.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemcharges) {
                    Intent intent = new Intent(PatientActivity.this, sr_PatientChargesActivity.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.iteminsurance) {
                    Intent intent = new Intent(PatientActivity.this, sr_PatientInsuranceActivity.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemlogout) {
                    Intent intent = new Intent(PatientActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }

}


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.patient_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.itemprofile:
//                Fragment fragment=new ProfileFragment();
//                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.framelayout, fragment);
//                transaction.commit();
//                break;
//            case R.id.itemappointments:
//                Fragment fragment1=new AppointmentsFragment();
//                FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
//                transaction1.replace(R.id.framelayout, fragment1);
//                transaction1.commit();
//                break;
//            case R.id.itemcharges:
//                Fragment fragment2=new ChargesFragment();
//                FragmentTransaction transaction2=getSupportFragmentManager().beginTransaction();
//                transaction2.replace(R.id.framelayout, fragment2);
//                transaction2.commit();
//                break;
//            case R.id.iteminsurance:
//                Fragment fragment3=new InsurancesFragment();
//                FragmentTransaction transaction3=getSupportFragmentManager().beginTransaction();
//                transaction3.replace(R.id.framelayout, fragment3);
//                transaction3.commit();
//                break;
//
//        }
//        return true;
//    }
