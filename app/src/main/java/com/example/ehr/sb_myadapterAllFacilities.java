package com.example.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class sb_myadapterAllFacilities extends RecyclerView.Adapter<sb_myadapterAllFacilities.myViewHolder>
{
    public Context context;
    List<sb_ResponseModel_AllFacilities> data;
    public sb_myadapterAllFacilities(Context context)
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
    public sb_myadapterAllFacilities(List<sb_ResponseModel_AllFacilities> data) {
        System.out.println("I'm called---------------------------");
        this.data = data;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowfacilitylist,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String name="Name: "+data.get(position).getName();
        String address1=data.get(position).getAddressline1();
        String address2=data.get(position).getAddressline2();
        String city=data.get(position).getCity();
        String state=data.get(position).getState();
        String zip=data.get(position).getZip();
        String contact="Contact: "+data.get(position).getContact();
        String address="Address: "+address1+" "+address2+"\nCity: "+city+" State: "+state+" Zip: "+zip;
        String emailid="Email: "+data.get(position).getEmailid();
        holder.t1.setText(name);
        holder.t2.setText(contact);
        holder.t3.setText(emailid);
        holder.t4.setText(address);
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
            context=itemView.getContext();
        }
    }
}
