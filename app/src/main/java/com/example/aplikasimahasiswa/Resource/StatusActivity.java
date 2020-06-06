package com.example.aplikasimahasiswa.Resource;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.Model.StatusItem;
import com.example.aplikasimahasiswa.R;
import com.example.aplikasimahasiswa.repository.StatusAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StatusActivity extends Fragment {
    private RecyclerView RV_Status;
    private StatusAdapter RV_Adapter;
    private RecyclerView.LayoutManager RV_LayoutManager;
    public ArrayList<StatusItem> ItemList;
    private String nim;
    private TextView noStatus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_status,container,false);
        ItemList = new ArrayList<>();
        RV_Status = view.findViewById(R.id.status_RecyclerView);
        RV_LayoutManager = new LinearLayoutManager(getContext());
        RV_Status.setLayoutManager(RV_LayoutManager);
        noStatus = view.findViewById(R.id.noStatus);
        //sortArray();
        SharedPref sharedpref = new SharedPref(getContext());
        Mahasiswa mahasiswa = sharedpref.load();
        nim = mahasiswa.get_nim();
        getApplimentStatus(nim);
        return view;
    }
    public void sortArray(){
        Collections.sort(ItemList, new Comparator<StatusItem>() {
            @Override
            public int compare(StatusItem o1, StatusItem o2) {
                return Integer.valueOf(o1.Sort()).compareTo(o2.Sort());
            }
        });
    }
    public void removeItem(int position){
        ItemList.remove(position);
        RV_Adapter.notifyItemRemoved(position);
    }
    private void getApplimentStatus(final String nim){
        JSONObject param = new JSONObject();
        try{
            param.put("NIM", nim);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/android_api/api/checkjob",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("data")&&!response.isNull("data")) {
                                JSONArray job_detail = response.getJSONArray("data");
                                for(int i = 0; i<job_detail.length();i++){
                                    JSONObject jsonObject = job_detail.getJSONObject(i);
                                    String job_name = jsonObject.getString("JobName");
                                    String job_id = jsonObject.getString("JobID");
                                    String perusahaan_name = jsonObject.getString("NamaPerusahaan");
                                    String status = jsonObject.getString("ApplimentStatus");
                                    StatusItem statusItem = new StatusItem(perusahaan_name,status);
                                    statusItem.setJobName(job_name);
                                    statusItem.setJobID(job_id);
                                    ItemList.add(statusItem);
                                }
                            }
                            else{
                                noStatus.setText("No Data!");

                            }
                            sortArray();
                            RV_Adapter = new StatusAdapter(ItemList,getContext());
                            RV_Status.setAdapter(RV_Adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error"+e, Toast.LENGTH_SHORT).show();
                        }
                        RV_Adapter.setOnItemClickListener(new StatusAdapter.OnItemClickListener() {
                            @Override
                            public void onDeleteClick(int position) {
                                removeItem(position);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error : "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
    private void delete(){

    }

}
