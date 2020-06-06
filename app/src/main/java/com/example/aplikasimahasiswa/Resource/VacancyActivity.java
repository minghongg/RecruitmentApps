package com.example.aplikasimahasiswa.Resource;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.Vacancy;
import com.example.aplikasimahasiswa.R;
import com.example.aplikasimahasiswa.repository.VacancyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VacancyActivity extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager llnLayout;
    private ArrayList<Vacancy> VacancyList;
    private VacancyAdapter _VacancyAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_vacancy,
                container,false);

        recyclerView = view.findViewById(R.id.vacancy_recycle_view);
        llnLayout= new LinearLayoutManager(this.getActivity().getBaseContext());
        recyclerView.setLayoutManager(llnLayout);
        VacancyList = new ArrayList<>();

        //dummy data

//        Vacancy vacancy = new Vacancy();
//        vacancy.setVacancyName("Dummy 1");
//        VacancyList.add(vacancy);
//        vacancy.setVacancyName("Dummy 2");
//        VacancyList.add(vacancy);
        getData();




        return view;
    }

    public void setAdapter(){
        _VacancyAdapter = new VacancyAdapter(VacancyList, new VacancyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vacancy vacancy) {
                String id = vacancy.getVacancyID();
                String address = vacancy.getVacancyAddress();
                String email = vacancy.getVacancyEmail();
                String name = vacancy.getVacancyName();
                //frg.

                JobDetailFragment fragment = new JobDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("id",id);
                bundle.putString("address",address);
                bundle.putString("email",email);
                fragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.main_fragment_container,fragment).addToBackStack(null).commit();
            }
        });
        recyclerView.setAdapter(_VacancyAdapter);

    }
    private void getData(){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:80/android_api/api/company",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list_perusahaan = response.getJSONArray("data");
                            for(int i = 0; i<list_perusahaan.length();i++){
                                JSONObject jsonObject = list_perusahaan.getJSONObject(i);

                                String perusahaanID = jsonObject.getString("PerusahaanID");
                                String name = jsonObject.getString("NamaPerusahaan");
                                String address = jsonObject.getString("PerusahaanAddress");
                                String email = jsonObject.getString("Email");
                                Vacancy vacancy = new Vacancy();
                                vacancy.setVacancyName(name);
                                vacancy.setVacancyID(perusahaanID);
                                vacancy.setVacancyEmail(email);
                                vacancy.setVacancyAddress(address);
                                VacancyList.add(vacancy);
                            }
                            setAdapter();
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
