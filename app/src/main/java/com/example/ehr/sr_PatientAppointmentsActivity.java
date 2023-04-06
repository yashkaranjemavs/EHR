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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class sr_PatientAppointmentsActivity extends AppCompatActivity {

    UserModel userModel;
    RecyclerView recyclerView;

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_patientappointments);

        userModel = (UserModel) getIntent().getSerializableExtra("user");

        Toolbar toolbar = findViewById(R.id.patient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Appointments");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        processData(userModel.getUserid());
    }

    public void setAdapter(List<PatientAppointmentModel> appointments) {
        sr_AppointmentAdapterClass adapterClass = new sr_AppointmentAdapterClass(appointments, sr_PatientAppointmentsActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterClass);
    }

    public void processData(String patientid) {
        BackgroundViewAppointmentDetailsWorker backgroundViewAppointmentDetailsWorker = new BackgroundViewAppointmentDetailsWorker(sr_PatientAppointmentsActivity.this);
        backgroundViewAppointmentDetailsWorker.execute("showAppointmentDetails", patientid);
    }

    public void cancelAppointment(String visitid) {
        BackgroundViewAppointmentDetailsWorker backgroundViewAppointmentDetailsWorker = new BackgroundViewAppointmentDetailsWorker(sr_PatientAppointmentsActivity.this);
        backgroundViewAppointmentDetailsWorker.execute("cancelAppointment", visitid);
    }

    public void appointmentCancelled(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        processData(userModel.getUserid());
    }

    private void showMenu(View v) {
        PopupMenu popupmenu = new PopupMenu(sr_PatientAppointmentsActivity.this, v);
        popupmenu.getMenuInflater().inflate(R.menu.patient_menu, popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemprofile) {
                    sr_PatientAppointmentsActivity.this.finish();
                    Intent intent = new Intent(sr_PatientAppointmentsActivity.this, sr_PatientProfileActivity.class);
                    intent.putExtra("user", userModel);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemhome) {
                    sr_PatientAppointmentsActivity.this.finish();
                    Intent intent = new Intent(sr_PatientAppointmentsActivity.this, PatientActivity.class);
                    intent.putExtra("user", userModel);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemappointments) {
                    sr_PatientAppointmentsActivity.this.finish();
                    Intent intent = new Intent(sr_PatientAppointmentsActivity.this, sr_PatientAppointmentsActivity.class);
                    intent.putExtra("user", userModel);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.iteminsurance) {
                    sr_PatientAppointmentsActivity.this.finish();
                    Intent intent = new Intent(sr_PatientAppointmentsActivity.this, sr_PatientInsuranceActivity.class);
                    intent.putExtra("user", userModel);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.itemlogout) {
                    Intent intent = new Intent(sr_PatientAppointmentsActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupmenu.show();
    }


}