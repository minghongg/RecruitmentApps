package com.example.aplikasimahasiswa.Resource;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.ListMahasiswaApplyItem;
import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.Model.Perusahaan;
import com.example.aplikasimahasiswa.Model.StatusItem;
import com.example.aplikasimahasiswa.R;
import com.example.aplikasimahasiswa.repository.ListMahasiswaApplyAdapter;
import com.example.aplikasimahasiswa.repository.StatusAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplimentFragment extends Fragment {

    private RecyclerView RV_ListMahasiswa;
    private RecyclerView.Adapter RV_Adapter;
    private RecyclerView.LayoutManager RV_LayoutManager;
    public ArrayList<ListMahasiswaApplyItem> listMahasiswaApplyItems;
    private TextView status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_appliment,
                container,false);

        listMahasiswaApplyItems = new ArrayList<>();

        RV_ListMahasiswa = view.findViewById(R.id.ListMahasiswaApply_RecyclerView);
        RV_LayoutManager = new LinearLayoutManager(getContext());
        RV_ListMahasiswa.setLayoutManager(RV_LayoutManager);
        status  = view.findViewById(R.id.noStatus);
        SharedPref sharedPref = new SharedPref(getContext());
        Perusahaan perusahaan = sharedPref.loadPerusahaan();
        String companyID = perusahaan.getPerusahaanID();
        getAppliments(companyID);
        return view;
    }
    private void getAppliments(final String CompanyID){
        JSONObject param = new JSONObject();
        try{
            param.put("PerusahaanID", CompanyID);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/android_api/api/getapplicants",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("data")&&!response.isNull("data")) {
                                JSONArray applicants_list = response.getJSONArray("data");
                                for(int i = 0; i<applicants_list.length();i++){
                                    JSONObject jsonObject = applicants_list.getJSONObject(i);
                                    String job_name = jsonObject.getString("JobName");
                                    String job_id = jsonObject.getString("JobID");
                                    String username = jsonObject.getString("Nama");
                                    String nim = jsonObject.getString("NIM");
                                    String appliment_id = jsonObject.getString("ApplimentID");

                                    ListMahasiswaApplyItem item = new ListMahasiswaApplyItem(username,job_name);
                                    String status = jsonObject.getString("ApplimentStatus");
                                    item.setJob_ID(job_id);
                                    item.setStatus(status);
                                    item.setNim(nim);
                                    item.setApplimentID(appliment_id);
                                    listMahasiswaApplyItems.add(item);
                                }
                            }
                            else{
                               status.setText("No Data!");
                            }
                            //sortArray();
                            //RV_Adapter = new StatusAdapter(ItemList,getContext());
                            RV_Adapter = new ListMahasiswaApplyAdapter(listMahasiswaApplyItems, getContext(), new ListMahasiswaApplyAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(ListMahasiswaApplyItem item) {
                                    String nim = item.getNim();
                                    ProfileActivity fragment = new ProfileActivity();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("NIM",nim);
                                    fragment.setArguments(bundle);
                                    FragmentManager manager = getFragmentManager();
                                    manager.beginTransaction().replace(R.id.main_fragment_container,fragment).addToBackStack(null).commit();
                                }
                            });
                            //RV_Status.setAdapter(RV_Adapter);
                            RV_ListMahasiswa.setAdapter(RV_Adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error"+e, Toast.LENGTH_SHORT).show();
                        }
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
}
