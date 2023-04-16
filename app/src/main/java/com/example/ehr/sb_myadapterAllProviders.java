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

public class sb_myadapterAllProviders extends RecyclerView.Adapter<sb_myadapterAllProviders.myViewHolder>
{
    public Context context;
    List<sb_ResponseModel_AllProviders> data;
    public sb_myadapterAllProviders(Context context)
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
    public sb_myadapterAllProviders(List<sb_ResponseModel_AllProviders> data) {
        System.out.println("I'm called---------------------------");
        this.data = data;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowproviderlist,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String pname="Name: "+data.get(position).getFirstname()+" "+data.get(position).getLastname();
        String gender=data.get(position).getGender();
        String dob=data.get(position).getDob();
        String gd="Gender: "+gender+" DOB: "+dob;
        String providerid=data.get(position).getProviderid();
        String address1=data.get(position).getAddressline1();
        String address2=data.get(position).getAddressline2();
        String city=data.get(position).getCity();
        String state=data.get(position).getState();
        String zip=data.get(position).getZip();
        String contact="Contact: "+data.get(position).getContact();
        String speciality="Speciality: "+data.get(position).getSpeciality();
        String address="Address: "+address1+" "+address2+"\nCity: "+city+" State: "+state+" Zip: "+zip;
        String emailid="Email: "+data.get(position).getEmailid();
        holder.t1.setText(pname);
        holder.t2.setText(gd);
        holder.t3.setText(speciality);
        holder.t4.setText(contact);
        holder.t5.setText(emailid);
        holder.t6.setText(address);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1, t2, t3, t4,t5,t6;
        Button b1;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            t5=itemView.findViewById(R.id.t5);
            t6=itemView.findViewById(R.id.t6);
            context=itemView.getContext();
        }
    }
}
