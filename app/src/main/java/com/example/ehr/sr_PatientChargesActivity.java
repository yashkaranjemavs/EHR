package com.example.ehr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class sr_PatientChargesActivity extends AppCompatActivity {
    UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_patientcharges);

        Toolbar toolbar = findViewById(R.id.patient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Charges");

        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }

    private void showMenu(View v) {
        PopupMenu popupmenu = new PopupMenu(sr_PatientChargesActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.patient_menu, popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemprofile) {
                    sr_PatientChargesActivity.this.finish();
                    Intent intent = new Intent(sr_PatientChargesActivity.this, sr_PatientProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemhome) {
                    sr_PatientChargesActivity.this.finish();
                    Intent intent = new Intent(sr_PatientChargesActivity.this, PatientActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemappointments) {
                    sr_PatientChargesActivity.this.finish();
                    Intent intent = new Intent(sr_PatientChargesActivity.this, sr_PatientAppointmentsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.iteminsurance) {
                    sr_PatientChargesActivity.this.finish();
                    Intent intent = new Intent(sr_PatientChargesActivity.this, sr_PatientInsuranceActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemlogout) {
                    Intent intent = new Intent(sr_PatientChargesActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }
}