package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


public class LaboratoryActivity extends AppCompatActivity {
    NavController navController;
    UserModel user;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);

        user = (UserModel) getIntent().getSerializableExtra("user");

        toolbar = findViewById(R.id.laboratory_toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.laboratory_menu);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.laboratory_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.laboratory_menu, menu);
        return true;
    }

    private LaboratoryActivity getActivity() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int laboratoryid = item.getItemId();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.laboratory_nav_host);
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        switch (laboratoryid){

            case R.id.laboratory_menu_home:
//              Navigating from Laboratory Profile Fragment or Laboratory Tests Fragment to Laboratory Fragment Home Page
                if (currentFragment instanceof LaboratoryPendingTestsFragment)
                {
                    break;
                }
                else if (currentFragment instanceof LaboratoryProfileFragment)
                {
                    Navigation.findNavController(findViewById(R.id.laboratory_nav_host)).navigate(R.id.action_laboratoryProfileFragment_to_laboratoryLandingTestsFragment, bundle);
                }
                else if (currentFragment instanceof LaboratoryAllTestsFragment)
                {
                    Navigation.findNavController(findViewById(R.id.laboratory_nav_host)).navigate(R.id.action_laboratoryAllTestsFragment_to_laboratoryLandingTestsFragment, bundle);
                }
                toolbar.setNavigationIcon(null);
                break;

            case R.id.laboratory_menu_profile:
//              Navigating from Laboratory Fragment or Laboratory Tests Fragment to Laboratory Profile Fragment Page
                if (currentFragment instanceof LaboratoryPendingTestsFragment)
                {
                    Navigation.findNavController(findViewById(R.id.laboratory_nav_host)).navigate(R.id.action_laboratoryLandingTestsFragment_to_laboratoryProfileFragment, bundle);
                }
                else if (currentFragment instanceof LaboratoryProfileFragment)
                {
                    break;
                }
                else if (currentFragment instanceof LaboratoryAllTestsFragment)
                {
                    Navigation.findNavController(findViewById(R.id.laboratory_nav_host)).navigate(R.id.action_laboratoryTestsFragment_to_laboratoryProfileFragment, bundle);
                }
                toolbar.setNavigationIcon(null);
                break;

//              Navigating from Laboratory Fragment or Laboratory profile Fragment to Laboratory Tests Fragment Page
            case R.id.laboratory_menu_tests:
                if (currentFragment instanceof LaboratoryPendingTestsFragment)
                {
                    Navigation.findNavController(findViewById(R.id.laboratory_nav_host)).navigate(R.id.action_laboratoryLandingTestsFragment_to_laboratoryAllTestsFragment, bundle);
                }
                else if (currentFragment instanceof LaboratoryProfileFragment)
                {
                    Navigation.findNavController(findViewById(R.id.laboratory_nav_host)).navigate(R.id.action_laboratoryProfileFragment_to_laboratoryTestsFragment, bundle);
                }
                else if (currentFragment instanceof LaboratoryAllTestsFragment)
                {
                    break;
                }
                toolbar.setNavigationIcon(null);
                break;


            case R.id.laboratory_menu_logout:
//              Navigating from Laboratory Fragment to Login Activity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public UserModel getUser() {
        return user;
    }


}