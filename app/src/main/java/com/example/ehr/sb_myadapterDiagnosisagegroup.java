package com.example.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class sb_myadapterDiagnosisagegroup extends RecyclerView.Adapter<sb_myadapterDiagnosisagegroup.myViewHolder>
{

    public Context context;
    List<sb_ResponseModel_Diagnosisagegroup> data;
    public sb_myadapterDiagnosisagegroup(Context context)
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
    public sb_myadapterDiagnosisagegroup(List<sb_ResponseModel_Diagnosisagegroup> data) {
        System.out.println("I'm called---------------------------");
        this.data = data;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdiagnosisagegroup,parent,false);
        return new myViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String medname="Diagnosis: "+data.get(position).getDiagnosis();
        String count="Count: "+data.get(position).getTotal();
        holder.t1.setText(medname);
        holder.t2.setText(count);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1, t2;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            context=itemView.getContext();
        }
    }
}
