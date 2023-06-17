package com.example.smstwilio;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements RecyclerAdapter.OnItemClickListener{

    RecyclerView contact_list;

    RecyclerAdapter adapter;
    ArrayList<ContactModel> contactModelList = new ArrayList<>();
    private String param1;
    private String param2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString("param1");
            param2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



        // Inflate the layout for this fragment
        return rootView;
    }

    public static HomeFragment newInstance(String param1,
                                            String param2)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }


    public void onViewCreated(View view,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Assign employeelist to ItemAdapter
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        // Add the following lines to create RecyclerView
        contact_list = requireView().findViewById(R.id.recyclerView);
        ContactModel contactModel=new ContactModel();
        contactModel.setName("Apoorv Yash");
        contactModel.setEmail("apoorvy.it.21@nitj.ac.in");
        contactModel.setPhone("+91-1234567890");
        contactModelList.add(contactModel);
        Log.d("apoorv", ""+contactModelList);
        contact_list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter= new RecyclerAdapter(contactModelList, this);


        adapter.dataChanged(contactModelList);
        contact_list.setAdapter(adapter);
        getJsonFileLocally();




//        return view;


    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void getJsonFileLocally() {

        try{
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            String resposeCode=jsonObject.getString("responseCode");
            String responseMessage=jsonObject.getString("responseMessage");
            String responseTime = jsonObject.getString("responseTime");
            Log.e("Activity", "responseCode -->" + resposeCode);
            Log.e("Activity", "responseMessage -->" + responseMessage);
            Log.e("Activity", "responseTime -->" + responseTime);
            if(resposeCode.equals("100")){

            }
            else{
                Toast.makeText(getContext(), "No record Found", Toast.LENGTH_SHORT).show();

            }
            JSONArray jsonArray=jsonObject.getJSONArray("contacts");
            Log.e("Active", "m_jArry -->" +jsonArray.length());

            for(int i=0; i<jsonArray.length(); i++){
                ContactModel contactModel=new ContactModel();
                JSONObject jsonObject1=jsonArray.getJSONObject(i);

                String name=jsonObject1.getString("name");
                String email=jsonObject1.getString("email");
                String phone=jsonObject1.getString("phone");
                if(i==0){
                    Log.d("Activity", "name -->"+ name);
                    Log.d("Activity", "email -->"+ email);
                    Log.d("Activity", "phone -->"+ phone);
                }
                contactModel.setName(""+ name);
                contactModel.setEmail(""+ email);
                contactModel.setPhone(""+ phone);
                contactModelList.add(contactModel);
            }
            if(contactModelList!=null){
                adapter.dataChanged(contactModelList);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadJSONFromAsset() {
        String json=null;
        try{
            InputStream is= requireContext().getAssets().open("db.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json= new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    @Override
    public void onItemClick(ContactModel dataModel) {
        Fragment fragment=ContactFragment.newInstance(dataModel.getName(), dataModel.getPhone());
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "contact_fragment");
        transaction.addToBackStack(null);
        transaction.commit();

    }
}