package com.example.ehr.insurance;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.ehr.R;
import com.example.ehr.UserModel;

import java.util.List;


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

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.insurance_nav_host);
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        switch (id){
            case R.id.insurance_menu_profile:
                if (currentFragment instanceof InsuranceFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host)).
                            navigate(R.id.action_insuranceFragment_to_insuranceProfileFragment, bundle);
                } else if (currentFragment instanceof InsuranceProfileFragment) {
                    break;
                } else if (currentFragment instanceof InsuranceCoverageFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                            .navigate(R.id.action_insuranceCoverageFragment_to_insuranceProfileFragment, bundle);
                } else if (currentFragment instanceof InsuranceSubscribersFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                            .navigate(R.id.action_insuranceSubscribersFragment_to_insuranceProfileFragment, bundle);
                }
                break;
            case R.id.insurance_menu_coverage:
                if (currentFragment instanceof InsuranceFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host)).
                            navigate(R.id.action_insuranceFragment_to_insuranceCoverageFragment, bundle);
                } else if (currentFragment instanceof InsuranceProfileFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                            .navigate(R.id.action_insuranceProfileFragment_to_insuranceCoverageFragment, bundle);
                } else if (currentFragment instanceof InsuranceCoverageFragment) {
                    break;
                } else if (currentFragment instanceof InsuranceSubscribersFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                            .navigate(R.id.action_insuranceSubscribersFragment_to_insuranceCoverageFragment, bundle);
                }
                break;
            case R.id.insurance_menu_subscribers:
                if (currentFragment instanceof InsuranceFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host)).
                            navigate(R.id.action_insuranceFragment_to_insuranceSubscribersFragment, bundle);
                } else if (currentFragment instanceof InsuranceProfileFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                            .navigate(R.id.action_insuranceProfileFragment_to_insuranceSubscribersFragment, bundle);
                } else if (currentFragment instanceof InsuranceCoverageFragment) {
                    Navigation.findNavController(findViewById(R.id.insurance_nav_host))
                            .navigate(R.id.action_insuranceCoverageFragment_to_insuranceSubscribersFragment, bundle);
                } else if (currentFragment instanceof InsuranceSubscribersFragment) {
                    break;
                }
                break;
            case R.id.insurance_menu_logout:
//                Toast.makeText(MainActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}