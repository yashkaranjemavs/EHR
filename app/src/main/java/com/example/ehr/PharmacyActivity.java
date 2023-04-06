package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;


public class PharmacyActivity extends AppCompatActivity {
    NavController navController;
    UserModel userModel;
    ToggleButton toggleButton;
    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        userModel = (UserModel) getIntent().getSerializableExtra("user");

        Toolbar toolbar = (Toolbar) findViewById(R.id.pharmacy_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pharmacy");

        toggleButton = (ToggleButton) findViewById(R.id.toggleButtonMedications);
        recycleView = (RecyclerView) findViewById(R.id.medication_recyclerViewId);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(toggleButton.isChecked()){
                    toggleButton.setTextOn("Pending Medications");
                    showMedications(userModel.getEmailid(), true);
                }else{
                    showMedications(userModel.getEmailid(), false);
                }
            }
        });
        showMedications(userModel.getEmailid(), false);

//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.pharmacy_nav_host);
//        if (navHostFragment != null) {
//            navController = NavHostFragment.findNavController(navHostFragment);
//            NavigationUI.setupWithNavController(toolbar, navController);
//        }
    }

    public void setAdapter(ArrayList<JSONObject> listOfRecords){
        yk_recyclerAdapter recyclerAdapter = new yk_recyclerAdapter(listOfRecords);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(recyclerAdapter);
    }

    public void showMedications(String emailid, boolean showOnlyPending){
        String onlyPending = String.valueOf(showOnlyPending);
        BackgroundMedicationDetailWorker backgroundMedicationDetailWorker = new BackgroundMedicationDetailWorker(PharmacyActivity.this);
        backgroundMedicationDetailWorker.execute("showMedicationDetails", emailid, onlyPending);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.yk_vertical_ellipsis, menu);
        return true;
    }

    public Boolean performViewProfile() {
        Intent intent = new Intent(PharmacyActivity.this, ViewPharmacyProfileActivity.class);
        intent.putExtra("emailid", userModel.getEmailid());
        startActivity(intent);
        return true;
    }

    public Boolean performLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                return performViewProfile();
            case R.id.item2:
                return performLogout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}