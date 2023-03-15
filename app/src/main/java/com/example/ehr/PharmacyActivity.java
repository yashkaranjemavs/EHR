package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


public class PharmacyActivity extends AppCompatActivity {
    NavController navController;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        userModel = (UserModel) getIntent().getSerializableExtra("user");

        Toolbar toolbar = (Toolbar) findViewById(R.id.pharmacy_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pharmacy");

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.pharmacy_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
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
        finish();
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