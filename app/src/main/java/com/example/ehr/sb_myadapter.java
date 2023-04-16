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

public class sb_myadapter extends RecyclerView.Adapter<sb_myadapter.myViewHolder>
{
    public Context context;
    List<sb_ResponseModel_Schedule> data;
    public sb_myadapter(Context context)
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
    public sb_myadapter(List<sb_ResponseModel_Schedule> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String pname="Patient Name: "+data.get(position).getFirstname()+" "+data.get(position).getLastname();
        String vdate="Date: "+data.get(position).getVdate();
        String vtime="Time: "+data.get(position).getVtime();
        String pnote="Notes: "+data.get(position).getPatientnotes();
        String visitid=data.get(position).getVisitid();
        String one=vdate+" "+vtime;
        holder.t1.setText(pname);
        holder.t2.setText(one);
        holder.t3.setText(pnote);
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, sb_ScheduledAppointment.class);
                intent.putExtra("visitid",visitid);
                intent.putExtra("pname",pname);
                intent.putExtra("vdatetime",one);
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
        TextView t1, t2, t3;
        Button b1;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            b1=itemView.findViewById(R.id.btnTreat);
            context=itemView.getContext();
        }
    }
}
