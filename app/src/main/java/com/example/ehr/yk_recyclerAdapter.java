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
    final String statusPrefix = "Status: ";

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

            toggleButtonStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(toggleButtonStatus.isChecked()){
                        doUpdateStatus(medicationid.getText().toString(), COMPLETED);
                        toggleButtonStatus.setTextOn(COMPLETED);
                        status.setText(statusPrefix+COMPLETED);
                        toggleButtonStatus.setTextColor(Color.parseColor("#00ff00"));
                    }else{
                        doUpdateStatus(medicationid.getText().toString(), PENDING);
                        toggleButtonStatus.setTextOff(PENDING);
                        status.setText(statusPrefix+PENDING);
                        toggleButtonStatus.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
            });
        }
    }

    public void doUpdateStatus(String medicationid, String status){
        medicationid = medicationid.split("[ ]")[1];
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

            holder.medicationid.setText(holder.medicationid.getText().toString().concat(medicationid));
            holder.status.setText(holder.status.getText().toString().concat(status));
            holder.patientName.setText(holder.patientName.getText().toString().concat(patientFirstname.concat(" ").concat(patientLastname)));
            if (status.equalsIgnoreCase(COMPLETED)){
                holder.toggleButtonStatus.setText(COMPLETED);
                holder.toggleButtonStatus.setTextColor(Color.parseColor("#00ff00"));
            }else if(status.equalsIgnoreCase(PENDING)) {
                holder.toggleButtonStatus.setText(PENDING);
                holder.toggleButtonStatus.setTextColor(Color.parseColor("#ff0000"));
            }else{
                holder.toggleButtonStatus.setText("Invalid Status");
            }
            holder.medications.setText(holder.medications.getText().toString().concat(medications));
            holder.patientid.setText(holder.patientid.getText().toString().concat(patientid));
            holder.patientNotes.setText(holder.patientNotes.getText().toString().concat(patientnotes));
            holder.providerNotes.setText(holder.providerNotes.getText().toString().concat(providernotes));
            holder.patientEmailid.setText(holder.patientEmailid.getText().toString().concat(patientemailid));
            holder.patientContact.setText(holder.patientContact.getText().toString().concat(patientcontact));
        }else{
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
        }
    }

    @Override
    public int getItemCount() {
        return listOfRecords.size();
    }
}
