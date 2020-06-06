package com.example.aplikasimahasiswa.Resource;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.Model.Perusahaan;
import com.example.aplikasimahasiswa.R;
import com.example.aplikasimahasiswa.repository.ExpandableTextViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JobDetailFragment extends Fragment {

    ExpandableListView expandableTextView;
    ArrayList<String> jobDetailList,jobID;
    Bundle bundle;
    String id,address,email,name;
    TextView companyName,companyAddress,companyEmail,companyPhone;
    FloatingActionButton add;
    Button apply;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { 
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_job_detail,
                container,false);
        expandableTextView = view.findViewById(R.id.elv_job_vacancy);
        companyAddress = view.findViewById(R.id.tv_info_address);
        companyName = view.findViewById(R.id.tv_company_name);
        companyPhone = view.findViewById(R.id.tv_info_contact);
        companyEmail = view.findViewById(R.id.tv_info_email);
        add = view.findViewById(R.id.fab);
        bundle = getArguments();
        if(bundle!=null){
            id = bundle.getString("id");
            address = bundle.getString("address");
            email = bundle.getString("email");
            name = bundle.getString("name");
            companyName.setText(name);
            companyEmail.setText("Email: "+email);
            companyAddress.setText("Alamat: "+address);
            getData(id);
            getPhone(id);
        }
        else {
            SharedPref sharedPref = new SharedPref(getContext());
            Perusahaan perusahaan = new Perusahaan();
            perusahaan = sharedPref.loadPerusahaan();
            id = perusahaan.getPerusahaanID();
            address = perusahaan.getPerusahaanAddress();
            email = perusahaan.getPerusahaanEmail();
            name = perusahaan.getPerusahaanName();
            companyName.setText(name);
            companyEmail.setText("Email: " + email);
            companyAddress.setText("Alamat: " + address);
            getData(id);
            getPhone(id);
        }
        //getFragmentManager().popBackStackImmediate();
        SharedPref sharedPref = new SharedPref(getContext());
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa = sharedPref.load();
        if(!mahasiswa.get_role().equals("")){
            add.setVisibility(View.GONE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddJobActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void getData(String id){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2/android_api/api/companydetail?PerusahaanID="+id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //String strtext = getArguments().getString("id");
                            JSONArray detailPerusahaan = response.getJSONArray("data");
                            jobDetailList = new ArrayList<>();
                            jobID = new ArrayList<>();
                            for(int i = 0; i<detailPerusahaan.length();i++){
                                JSONObject jsonObject = detailPerusahaan.getJSONObject(i);

                                String jobName = jsonObject.getString("JobName");
                                String idJob = jsonObject.getString("JobID");
                                jobDetailList.add(jobName);
                                jobID.add(idJob);
                            }

                            ExpandableTextViewAdapter adapter = new ExpandableTextViewAdapter(getContext(),jobDetailList,jobID);
                            expandableTextView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error3 : "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private void getPhone(String id) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2/android_api/api/getphone?PerusahaanID=" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //String strtext = getArguments().getString("id");
                            JSONArray phoneNumber = response.getJSONArray("data");
                            jobDetailList = new ArrayList<>();
                            for (int i = 0; i < phoneNumber.length(); i++) {
                                JSONObject jsonObject = phoneNumber.getJSONObject(i);
                                String phone = jsonObject.getString("PhoneNumber");
                                companyPhone.setText("Kontak: " + phone);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error4" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error5 : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
