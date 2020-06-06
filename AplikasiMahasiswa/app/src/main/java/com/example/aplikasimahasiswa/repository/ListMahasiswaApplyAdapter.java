package com.example.aplikasimahasiswa.repository;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.ListMahasiswaApplyItem;
import com.example.aplikasimahasiswa.Model.Vacancy;
import com.example.aplikasimahasiswa.R;
import com.example.aplikasimahasiswa.Resource.JobDetailFragment;
import com.example.aplikasimahasiswa.Resource.ProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;

public class ListMahasiswaApplyAdapter extends RecyclerView.Adapter<ListMahasiswaApplyAdapter.ListMahasiswaApplyViewHolder> {


    public Context context;
    private ArrayList<ListMahasiswaApplyItem> ItemList;
    private boolean itsWork;

    public interface OnItemClickListener {
        void onItemClick(ListMahasiswaApplyItem item);
    }
    public interface CheckAccepted{
        void onCheck(Boolean bool);
    }
    private final OnItemClickListener listener;
    public static class ListMahasiswaApplyViewHolder extends RecyclerView.ViewHolder{
        public TextView NameApplyment,JobApplyment;
        public Button Button_Profile,Button_Accept,Button_Reject;
        public LinearLayout linearLayout;
        public ListMahasiswaApplyViewHolder(@NonNull View itemView) {
            super(itemView);
            NameApplyment =itemView.findViewById(R.id.listMahasiswa_TextView_name);
            JobApplyment =itemView.findViewById(R.id.listMahasiswa_TextView_job);
            Button_Profile = itemView.findViewById(R.id.listMahasiswa_Button_Profile);
            Button_Accept = itemView.findViewById(R.id.listMahasiswa_Button_Accept);
            Button_Reject = itemView.findViewById(R.id.listMahasiswa_Button_Reject);
            linearLayout = itemView.findViewById(R.id.listMahasiswa_RecyclerViewDesign);

        }
    }

    public ListMahasiswaApplyAdapter(ArrayList<ListMahasiswaApplyItem> listMahasiswaApplyItems, Context context, OnItemClickListener listener) {
        ItemList = listMahasiswaApplyItems;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListMahasiswaApplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmahasiswa_carditem, parent, false);
        ListMahasiswaApplyViewHolder listMahasiswaApplyViewHolder = new ListMahasiswaApplyViewHolder(view);
        return listMahasiswaApplyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListMahasiswaApplyViewHolder holder, int position) {
        final ListMahasiswaApplyItem currentItem = ItemList.get(position);
        holder.NameApplyment.setText(currentItem.nameApplyment());
        holder.JobApplyment.setText(currentItem.JobApplyment());

        if(currentItem.getStatus().equals("Accepted")){
            holder.Button_Accept.setVisibility(GONE);
            holder.Button_Reject.setVisibility(GONE);
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.light_green));
        }
        else if(currentItem.getStatus().equals("Rejected")){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.light_red));
            holder.Button_Accept.setVisibility(GONE);
            holder.Button_Reject.setVisibility(GONE);
        }
        else{
            holder.Button_Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String appliment_id = currentItem.getApplimentID();
                    String job_id = currentItem.getJob_ID();
                    String status = "Accepted";

                    updateTrAppliment(appliment_id, job_id, status, new CheckAccepted() {
                        @Override
                        public void onCheck(Boolean bool) {
                            itsWork = bool;
                        }
                    });
                    if(itsWork){
                        holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                        holder.Button_Accept.setVisibility(GONE);
                        holder.Button_Reject.setVisibility(GONE);
                    }
                }
            });
            holder.Button_Reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String appliment_id = currentItem.getApplimentID();
                    String job_id = currentItem.getJob_ID();
                    String statusReject = "Rejected";
                    updateTrAppliment(appliment_id, job_id, statusReject, new CheckAccepted() {
                        @Override
                        public void onCheck(Boolean bool) {
                            itsWork = bool;
                        }
                    });
                    if(itsWork){
                        holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.light_red));
                        holder.Button_Accept.setVisibility(GONE);
                        holder.Button_Reject.setVisibility(GONE);
                    }

                }
            });
        }

        holder.Button_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(currentItem);
            }
        });

}

    @Override
    public int getItemCount() {
        return ItemList.size();
    }
    private void updateTrAppliment(final String appliment_id, final String job_id, final String status,final CheckAccepted checkAccepted){

        JSONObject param = new JSONObject();
        try{
            param.put("ApplimentID", appliment_id);
            param.put("JobID",job_id);
            param.put("ApplimentStatus",status);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/android_api/api/updateappliment",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if(message.equals("Quota habis!")){
                                Toast.makeText(context, "Quota pekerjaan habis!", Toast.LENGTH_SHORT).show();

                            }else{
                                checkAccepted.onCheck(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error"+e, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error :"+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
