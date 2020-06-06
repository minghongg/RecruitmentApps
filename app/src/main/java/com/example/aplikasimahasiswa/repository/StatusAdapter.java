package com.example.aplikasimahasiswa.repository;

import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.example.aplikasimahasiswa.Resource.SharedPref;
import com.example.aplikasimahasiswa.Resource.StatusActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    public Context context;
    private ArrayList<StatusItem> ItemList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }



    public static class StatusViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public Button Status_Button_unapply;
        public TextView TV_Status_CompanyName,TV_Status_Status;
        public StatusViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            TV_Status_CompanyName = itemView.findViewById(R.id.status_TextView_CompanyTitle);
            TV_Status_Status = itemView.findViewById(R.id.status_TextView_Status);
            relativeLayout = itemView.findViewById(R.id.status_RecyclerViewDesign);
            Status_Button_unapply = itemView.findViewById(R.id.status_button_unapply);

//            Status_Button_unapply.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(listener!=null){
//                        int position = getAdapterPosition();
//                        if(position!= RecyclerView.NO_POSITION){
//                            listener.onDeleteClick(position);
//                        }
//
//                    }
//                }
//            });
        }
    }
    public StatusAdapter(ArrayList<StatusItem> statusList, Context context){
        ItemList = statusList;
        this.context = context;
    }
    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_carditem,parent,false);
        StatusViewHolder statusViewHolder = new StatusViewHolder(view,mListener);
        return  statusViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusViewHolder holder, final int position) {
        final StatusItem currentItem = ItemList.get(position);
        holder.TV_Status_CompanyName.setText(currentItem.CompanyName()+" ("+currentItem.getJobName()+")");
        holder.TV_Status_Status.setText(currentItem.Status());
        if(currentItem.Status().equals("Accepted")){
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.light_green));
            holder.Status_Button_unapply.setVisibility(View.GONE);
        }else if(currentItem.Status().equals("Rejected")){
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.light_red));
            holder.Status_Button_unapply.setVisibility(View.GONE);
        }else{
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.Status_Button_unapply.setVisibility(View.VISIBLE);
        }
        holder.Status_Button_unapply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String jobid= currentItem.getJobID();
                    SharedPref sharedpref = new SharedPref(context);
                    Mahasiswa mahasiswa = sharedpref.load();
                    String nim = mahasiswa.get_nim();
                    delete(nim,jobid);
                    holder.relativeLayout.setVisibility(View.GONE);
                }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public void delete(String nim,final String id){
        JSONObject param = new JSONObject();
        try{
            param.put("JobID", id);
            param.put("NIM", nim);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/android_api/api/unapplyjob",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("message");
                            if(res.equals("Successfully unapplied job!")){
                                Toast.makeText(context, "Successfully unapplied job!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Error : "+error, Toast.LENGTH_SHORT).show();
                    }

                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

}
