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

public class sb_admin_viewprovider extends AppCompatActivity {
    NavController navController;
    RecyclerView review;
    UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_admin_viewprovider);
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
        review=findViewById(R.id.recview);
        review.setLayoutManager(new LinearLayoutManager(this));
        processData();
    }
    private void showMenu(View v)
    {
        PopupMenu popupmenu=new PopupMenu(sb_admin_viewprovider.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.sb_admin_popup_menu,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.admin_profile)
                {
                    Intent intent=new Intent(sb_admin_viewprovider.this,sb_admin_profile.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_adduser)
                {
                    Intent intent=new Intent(sb_admin_viewprovider.this,sb_admin_adduser.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_viewusers)
                {
                    Intent intent=new Intent(sb_admin_viewprovider.this,sb_admin_viewusers.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.admin_logout)
                {
                    Intent intent=new Intent(sb_admin_viewprovider.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
    public void processData()
    {
        Call<List<sb_ResponseModel_AllProviders>> call=sb_apiControllerAllProviders
                .getInstance()
                .getApi()
                .getData();
        call.enqueue(new Callback<List<sb_ResponseModel_AllProviders>>() {
            @Override
            public void onResponse(Call<List<sb_ResponseModel_AllProviders>> call, Response<List<sb_ResponseModel_AllProviders>> response) {
                List<sb_ResponseModel_AllProviders> data=response.body();
                System.out.println("response is: "+response.toString());
                sb_myadapterAllProviders adapter=new sb_myadapterAllProviders(data);
                user = (UserModel) getIntent().getSerializableExtra("user");
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                review.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<sb_ResponseModel_AllProviders>> call, Throwable t) {
                System.out.println("receive is: "+t.toString());
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}