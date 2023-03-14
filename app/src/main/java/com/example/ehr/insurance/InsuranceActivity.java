package com.example.ehr.insurance;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.ehr.R;
import com.example.ehr.UserModel;


public class InsuranceActivity extends AppCompatActivity {
    NavController navController;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        user = (UserModel) getIntent().getSerializableExtra("user");

        Toolbar toolbar = findViewById(R.id.insurance_toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.insurance_menu);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.insurance_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insurance_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        switch (id){
            case R.id.insurance_menu_profile:
                Navigation.findNavController(findViewById(R.id.insurance_nav_host)).
                        navigate(R.id.action_insuranceFragment_to_insuranceProfileFragment, bundle);
                break;
            case R.id.insurance_menu_coverage:
                Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                        .navigate(R.id.action_insuranceFragment_to_insuranceCoverageFragment, bundle);
                break;
            case R.id.insurance_menu_subscribers:
                Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                        .navigate(R.id.action_insuranceFragment_to_insuranceSubscribersFragment, bundle);
                break;
            case R.id.insurance_menu_logout:
//                Toast.makeText(MainActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}