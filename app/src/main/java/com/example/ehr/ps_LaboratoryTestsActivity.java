package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class ps_LaboratoryTestsActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_laboratory_tests);

        UserModel user = (UserModel) getIntent().getSerializableExtra("user");

        Toolbar toolbar = (Toolbar) findViewById(R.id.laboratory_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(LaboratoryActivity.this,"You clicked on the icon",Toast.LENGTH_SHORT).show();
                showMenu(v);
            }
        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.laboratory_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
    }
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(ps_LaboratoryTestsActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.ps_laboratory_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
// Navigation from Tests page to Profile activity page when clicked profile

                if(menuItem.getItemId()==R.id.laboratory_menuitem_profile)
                {
                    Intent intent=new Intent(ps_LaboratoryTestsActivity.this, ps_LaboratoryProfileActivity.class);
                    startActivity(intent);
                }
// Navigation from Tests page to tests activity page when clicked tests
                else if(menuItem.getItemId()==R.id.laboratory_menuitem_tests)
                {
                    Intent intent=new Intent(ps_LaboratoryTestsActivity.this,ps_LaboratoryTestsActivity.class);
                    startActivity(intent);
                }
// Navigation from Tests page to login activity page when clicked logout
                else if(menuItem.getItemId()==R.id.laboratory_menuitem_logout)
                {
                    Intent intent=new Intent(ps_LaboratoryTestsActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
}
