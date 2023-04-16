package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class sb_admin_viewusers extends AppCompatActivity {
    NavController navController;
    UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_admin_viewusers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        ImageView menuIcon=findViewById(R.id.menuIcon);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.admin_nav_host);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(toolbar, navController);
        }
        menuIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(ProviderActivity.this,"You clicked on the icon",Toast.LENGTH_SHORT).show();
                showMenu(v);
            }
        });
        Button button1,button2,button3,button4;
        button1 = (Button) findViewById(R.id.btnViewProvider);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewProviderActivity();
            }
        });
        button2 = (Button) findViewById(R.id.btnviewlab);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewLabActivity();
            }
        });
        button3 = (Button) findViewById(R.id.btnviewpharmacy);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewPharmacyActivity();
            }
        });
        button4 = (Button) findViewById(R.id.btnviewins);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewInsActivity();
            }
        });
    }
    public void openViewProviderActivity(){
        Intent intent = new Intent(this, sb_admin_viewprovider.class);
        startActivity(intent);
    }
    public void openViewLabActivity(){
        Intent intent = new Intent(this, sb_admin_viewallfacilities.class);
        intent.putExtra("type","Laboratory");
        startActivity(intent);
    }
    public void openViewPharmacyActivity(){
        Intent intent = new Intent(this, sb_admin_viewallfacilities.class);
        intent.putExtra("type","Pharmacy");
        startActivity(intent);
    }
    public void openViewInsActivity(){
        Intent intent = new Intent(this, sb_admin_viewallfacilities.class);
        intent.putExtra("type","Insurance");
        startActivity(intent);
    }
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_admin_viewusers.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_admin_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.admin_profile)
                {
                    Intent intent=new Intent(sb_admin_viewusers.this,sb_admin_profile.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_adduser)
                {
                    Intent intent=new Intent(sb_admin_viewusers.this,sb_admin_adduser.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_viewusers)
                {
                    Intent intent=new Intent(sb_admin_viewusers.this,sb_admin_viewusers.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_logout)
                {
                    Intent intent=new Intent(sb_admin_viewusers.this,LoginActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
}