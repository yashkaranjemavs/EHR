package com.example.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class sr_AppointmentAdapterClass extends RecyclerView.Adapter<sr_AppointmentAdapterClass.myviewholder> {

    public Context context;
    private List<PatientAppointmentModel> appointments;
    private sr_PatientAppointmentsActivity patientAppointmentsActivity;

    public sr_AppointmentAdapterClass(List<PatientAppointmentModel> appointments, sr_PatientAppointmentsActivity patientAppointmentsActivity) {
        this.appointments = appointments;
        this.patientAppointmentsActivity = patientAppointmentsActivity;
    }


    public class myviewholder extends RecyclerView.ViewHolder {
        private TextView vdate, vtime, patientnotes, providernotes, diagnosis, symptoms, status;
        private LinearLayout providernotesContainer, diagnosisContainer, symptomsContainer;
        private Button cancelButton, viewButton;

        private myviewholder(final View view) {
            super(view);
            vdate = view.findViewById(R.id.patientAppointmentVisitDate);
            vtime = view.findViewById(R.id.patientAppointmentVisitTime);
            patientnotes = view.findViewById(R.id.patientAppointmentPatientNotes);
            providernotes = view.findViewById(R.id.patientAppointmentProviderNotes);
            diagnosis = view.findViewById(R.id.patientAppointmentDiagnosis);
            symptoms = view.findViewById(R.id.patientAppointmentSymptoms);
            status = view.findViewById(R.id.patientAppointmentStatus);

            providernotesContainer = view.findViewById(R.id.patientAppointmentProviderNotesContainer);
            diagnosisContainer = view.findViewById(R.id.patientAppointmentDiagnosisContainer);
            symptomsContainer = view.findViewById(R.id.patientAppointmentSymptomsContainer);

            cancelButton = view.findViewById(R.id.patientAppointmentCancelButton);
            viewButton = view.findViewById(R.id.patientAppointmentViewButton);
        }
    }

    @NonNull
    @Override
    public sr_AppointmentAdapterClass.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sr_singleappointmentrow, parent, false);
        return new myviewholder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        try {
            holder.vdate.setText(appointments.get(position).getVdate());
            holder.vtime.setText(appointments.get(position).getVtime());
            holder.patientnotes.setText(appointments.get(position).getPatientnotes());
            holder.status.setText(appointments.get(position).getStatus());
            holder.providernotes.setText(appointments.get(position).getProvidernotes());
            holder.symptoms.setText(appointments.get(position).getSymptoms());
            holder.diagnosis.setText(appointments.get(position).getDiagnosis());

            if (appointments.get(position).getStatus().equalsIgnoreCase("complete")) {
                holder.providernotesContainer.setVisibility(View.VISIBLE);
                holder.symptomsContainer.setVisibility(View.VISIBLE);
                holder.diagnosisContainer.setVisibility(View.VISIBLE);

                holder.viewButton.setVisibility(View.VISIBLE);

                holder.viewButton.setOnClickListener(view -> {
                    sr_PatientAppointViewDialog dialog = new sr_PatientAppointViewDialog(this.patientAppointmentsActivity, appointments.get(position).getVisitid());
                    dialog.setTitle("Patient Details");
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.getWindow().setAttributes(layoutParams);
                });
            } else {
                holder.providernotesContainer.setVisibility(View.GONE);
                holder.symptomsContainer.setVisibility(View.GONE);
                holder.diagnosisContainer.setVisibility(View.GONE);

                holder.viewButton.setVisibility(View.GONE);
            }

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(appointments.get(position).getVdate());

            Calendar today = Calendar.getInstance();

            Calendar appointmentDate = Calendar.getInstance();
            appointmentDate.setTime(date);

            boolean isAptInFuture = today.before(appointmentDate);

            if (!appointments.get(position).getStatus().equalsIgnoreCase("booked") || !isAptInFuture) {
                holder.cancelButton.setVisibility(View.GONE);
            } else {
                holder.cancelButton.setVisibility(View.VISIBLE);

                holder.cancelButton.setOnClickListener(view -> {
                    String visitid = appointments.get(position).getVisitid();
                    this.patientAppointmentsActivity.cancelAppointment(visitid);
                });
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

}
