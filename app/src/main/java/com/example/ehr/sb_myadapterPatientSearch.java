package com.example.ehr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class sb_myadapterPatientSearch extends RecyclerView.Adapter<sb_myadapterPatientSearch.myViewHolder>{

    public Context context;
    List<sb_ResponseModel_PatientSearch> data;
    public sb_myadapterPatientSearch(Context context)
    {
        if(this.context==null)
        {
            System.out.println("it's null");
        }
        else
        {
            System.out.println("it's not null");
        }
        System.out.println("I'm called---------------------------");
        this.context=context;
    }
    public sb_myadapterPatientSearch(List<sb_ResponseModel_PatientSearch> data) {
        System.out.println("I'm called---------------------------");
        this.data = data;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowpatientlist,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String pname="Patient Name: "+data.get(position).getFirstname()+" "+data.get(position).getLastname();
        String gender="Gender: "+data.get(position).getGender();
        String dob="DOB: "+data.get(position).getDob();
        String patientid=data.get(position).getPatientid();
        holder.t1.setText(pname);
        holder.t2.setText(gender);
        holder.t3.setText(dob);
        holder.t4.setText(patientid);
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, sb_view_patient_by_id.class);
                intent.putExtra("patientid",patientid);
                intent.putExtra("pname",pname);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1, t2, t3, t4;
        Button b1;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            b1=itemView.findViewById(R.id.btnView);
            context=itemView.getContext();
        }
    }
}
