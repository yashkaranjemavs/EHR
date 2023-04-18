package com.example.ehr;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class yk_recyclerAdapter extends RecyclerView.Adapter<yk_recyclerAdapter.MyViewHolder>{
    private ArrayList<JSONObject> listOfRecords;
    final String COMPLETED = "Completed";
    final String PENDING = "Pending";

    public yk_recyclerAdapter(ArrayList<JSONObject> listOfRecords) {
        this.listOfRecords = listOfRecords;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView medicationid;
        private TextView status;
        private TextView patientName;
        private TextView patientid;
        private TextView medications;
        private TextView patientNotes;
        private TextView providerNotes;
        private TextView patientEmailid;
        private TextView patientContact;
        private ToggleButton toggleButtonStatus;

        private TextView medicationidHeading, statusHeading, patientNameHeading, patientidHeading, medicationsHeading, patientNotesHeading, providerNotesHeading, patientEmailidHeading, patientContactHeading;

        public MyViewHolder(final View view){
            super(view);
            medicationid = view.findViewById(R.id.textViewMedicationId);
            status = view.findViewById(R.id.textViewStatus);
            patientName = view.findViewById(R.id.textViewPatientName);
            toggleButtonStatus = view.findViewById(R.id.toggleButtonStatus);
            patientid = view.findViewById(R.id.textView7);
            medications = view.findViewById(R.id.textView9);
            patientNotes = view.findViewById(R.id.textView10);
            providerNotes = view.findViewById(R.id.textView11);
            patientEmailid = view.findViewById(R.id.textView12);
            patientContact = view.findViewById(R.id.textView13);

            medicationidHeading = view.findViewById(R.id.textViewMedicationIdHeading);
            statusHeading = view.findViewById(R.id.textViewStatusHeading);
            patientNameHeading = view.findViewById(R.id.textViewPatientNameHeading);
            patientidHeading = view.findViewById(R.id.textView7Heading);
            medicationsHeading = view.findViewById(R.id.textView9Heading);
            patientNotesHeading = view.findViewById(R.id.textView10Heading);
            providerNotesHeading = view.findViewById(R.id.textView11Heading);
            patientEmailidHeading = view.findViewById(R.id.textView12Heading);
            patientContactHeading = view.findViewById(R.id.textView13Heading);

            toggleButtonStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(toggleButtonStatus.isChecked()){
                        doUpdateStatus(medicationid.getText().toString(), COMPLETED);
                        toggleButtonStatus.setTextOn(COMPLETED);
                        status.setText(COMPLETED);
                        toggleButtonStatus.setTextColor(Color.parseColor("#00ff00"));
                    }else{
                        doUpdateStatus(medicationid.getText().toString(), PENDING);
                        toggleButtonStatus.setTextOff(PENDING);
                        status.setText(PENDING);
                        toggleButtonStatus.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
            });
        }
    }

    public void doUpdateStatus(String medicationid, String status){
        BackgroundMedicationDetailWorker backgroundMedicationDetailWorker = new BackgroundMedicationDetailWorker(new PharmacyActivity());
        backgroundMedicationDetailWorker.execute("updateMedicationStatus", medicationid, status);
    }

    @NonNull
    @Override
    public yk_recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull yk_recyclerAdapter.MyViewHolder holder, int position) {
        String medicationid = null, status = null, patientFirstname = null, patientLastname = null, medications=null, patientid=null, providernotes=null, patientnotes=null, patientcontact=null, patientemailid=null;
        if(!listOfRecords.get(0).has("error")){
            try {
                medicationid = (String) listOfRecords.get(position).get("medicationid");
                status = (String) listOfRecords.get(position).get("status");
                patientFirstname = (String) listOfRecords.get(position).get("patientFirstname");
                patientLastname = (String) listOfRecords.get(position).get("patientLastname");
                medications = (String) listOfRecords.get(position).get("medications");
                patientid = (String) listOfRecords.get(position).get("patientid");
                patientnotes = (String) listOfRecords.get(position).get("patientnotes");
                providernotes = (String) listOfRecords.get(position).get("providernotes");
                patientemailid = (String) listOfRecords.get(position).get("patientemailid");
                patientcontact = (String) listOfRecords.get(position).get("patientcontact");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            holder.medicationid.setText(medicationid);
            holder.status.setText(status);
            holder.patientName.setText(patientFirstname.concat(" ").concat(patientLastname));
            if (status.equalsIgnoreCase(COMPLETED)){
                holder.toggleButtonStatus.setText(COMPLETED);
                holder.toggleButtonStatus.setTextColor(Color.parseColor("#00ff00"));
            }else if(status.equalsIgnoreCase(PENDING)) {
                holder.toggleButtonStatus.setText(PENDING);
                holder.toggleButtonStatus.setTextColor(Color.parseColor("#ff0000"));
            }else{
                holder.toggleButtonStatus.setText("Invalid Status");
            }
            holder.medications.setText(medications);
            holder.patientid.setText(patientid);
            holder.patientNotes.setText(patientnotes);
            holder.providerNotes.setText(providernotes);
            holder.patientEmailid.setText(patientemailid);
            holder.patientContact.setText(patientcontact);
        }else{
//            Set fields to empty string
            holder.medicationid.setText("");
            holder.status.setText("");
            holder.patientName.setText("");
            holder.toggleButtonStatus.setText("");
            holder.medications.setText("");
            holder.patientid.setText("");
            holder.patientNotes.setText("");
            holder.providerNotes.setText("");
            holder.patientEmailid.setText("");
            holder.patientContact.setText("");

//            Set field headings to empty string
            holder.medicationidHeading.setText("");
            holder.statusHeading.setText("");
            holder.patientNameHeading.setText("");
            holder.medicationsHeading.setText("");
            holder.patientidHeading.setText("");
            holder.patientNotesHeading.setText("");
            holder.providerNotesHeading.setText("");
            holder.patientEmailidHeading.setText("");
            holder.patientContactHeading.setText("");

//            Set field background to white
            holder.medicationid.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.status.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.patientName.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.medications.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.patientid.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.patientNotes.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.providerNotes.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.patientEmailid.setBackgroundColor(Color.argb(255, 255, 255, 255));
            holder.patientContact.setBackgroundColor(Color.argb(255, 255, 255, 255));

//            Disable the status button
            holder.toggleButtonStatus.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return listOfRecords.size();
    }
}
