package com.example.smstwilio;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {
//    private Context context;

//
//    public RecyclerAdapter(Context context, List<ContactModel> contactModelList){
//        this.context=context;
//        this.contactModelList=contactModelList;
//    }

//
//
//
//
//    @NonNull
//    @Override
//    public RecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
//        return new CustomViewHolder(item);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerAdapter.CustomViewHolder holder, int position) {
//        ContactModel contactModel=contactModelList.get(position);
//        holder.name.setText("Name: "+contactModel.getName());
//        holder.phone.setText("Phone: "+contactModel.getPhone());
//        holder.email.setText("Email: "+contactModel.getEmail());
//    }
//
//    @Override
//    public int getItemCount() {
//        return contactModelList.size();
//    }
//
//    public class CustomViewHolder extends RecyclerView.ViewHolder {
//        private TextView name;
//        private TextView email;
//        private TextView phone;
//
//        public CustomViewHolder(View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.emp_id);
//            phone = itemView.findViewById(R.id.phone);
//            email = itemView.findViewById(R.id.email);
//        }
//    }
//}
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<ContactModel> emplist;
    private OnItemClickListener listener;
    public RecyclerAdapter(ArrayList<ContactModel> emplist, OnItemClickListener listener) {
        this.emplist = emplist;
        this.listener=listener;
    }
        public void dataChanged(ArrayList<ContactModel>contactModelList){
        Log.e("Active", "data changed-->" + contactModelList.size());
        this.emplist=contactModelList;
        this.listener=listener;
        notifyDataSetChanged();
    }
    // This method creates a new ViewHolder object for each item in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item and return a new ViewHolder object
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    // This method returns the total
    // number of items in the data set
    @Override
    public int getItemCount() {
        return emplist.size();
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactModel currentEmp = emplist.get(position);
        holder.name.setText(currentEmp.getName());
        holder.email.setText(currentEmp.getEmail());
        holder.phone.setText(currentEmp.getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                listener.onItemClick(emplist.get(position));
            }
        });
    }
//    public void bind(final  item, final OnItemClickListener listener) {
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                listener.onItemClick(item);
//            }
//        });
//    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView email;
        private TextView phone;
        private Button button;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            button =itemView.findViewById(R.id.img_call);
        }


    }
    public interface OnItemClickListener{
        void onItemClick(ContactModel dataModel);
    }
}
