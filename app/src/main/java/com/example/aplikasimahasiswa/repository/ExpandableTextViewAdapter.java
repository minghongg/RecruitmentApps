package com.example.aplikasimahasiswa.repository;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.Model.TrAppliment;
import com.example.aplikasimahasiswa.Model.Vacancy;
import com.example.aplikasimahasiswa.R;
import com.example.aplikasimahasiswa.Resource.JobDetailFragment;
import com.example.aplikasimahasiswa.Resource.LoginActivity;
import com.example.aplikasimahasiswa.Resource.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;

public class ExpandableTextViewAdapter extends BaseExpandableListAdapter {
    Button vacSubtitle2;
    TextView jobName,jobDesc,jobQuota;
    Context context;
    Dialog mDialog;
    String nim;
    ArrayList<String> arrayList;
    ArrayList<String> jobID;
    ArrayList<TrAppliment> applimentList;



    public ExpandableTextViewAdapter(Context context, ArrayList<String> arrayList, ArrayList<String> jobID) {
        this.context = context;
//        this.activity = activity;
        this.arrayList = arrayList;
        this.jobID = jobID;


        SharedPref sharedpref = new SharedPref(context);
        Mahasiswa mahasiswa = sharedpref.load();
        nim = mahasiswa.get_nim();
        checkJob(nim,new Callback(){
            @Override
            public void onSuccess(ArrayList<TrAppliment> tempTrAppliment) {
                applimentList = tempTrAppliment;
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return arrayList.size(); //berapa banyak data
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String vacTitle = (String)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vac_title, null);
        }
        TextView vacTitle2 = convertView.findViewById(R.id.tv_vac_title);
        vacTitle2.setTypeface(null, Typeface.BOLD);
        vacTitle2.setText(vacTitle);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vac_subtitle, null);
        }
        Button vacSubtitle = convertView.findViewById(R.id.btn_vac_description);
        vacSubtitle2 = convertView.findViewById(R.id.btn_vac_apply);
        Mahasiswa mahasiswa = new Mahasiswa();
        SharedPref sharedPref = new SharedPref(context);
        mahasiswa = sharedPref.load();
        String role = mahasiswa.get_role();
        final String jobid = jobID.get(groupPosition);
        if(role==""){
            vacSubtitle2.setVisibility(GONE);
        }
        else {
            boolean flag = false;
            if (applimentList != null) {
                for (int i = 0; i < applimentList.size(); i++) {
                    if (jobID.get(groupPosition).equals(applimentList.get(i).getJobID())) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                vacSubtitle2.setVisibility(View.VISIBLE);
                vacSubtitle2.setText("Apply");
                vacSubtitle2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        applyJob(nim, jobid);
                    }
                });
            } else {
                vacSubtitle2.setVisibility(View.INVISIBLE);
            }
        }
        mDialog = new Dialog(context);
        vacSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.popup);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                jobName = mDialog.findViewById(R.id.tv_jobName);
                jobDesc = mDialog.findViewById(R.id.tv_jobDesc);
                jobQuota = mDialog.findViewById(R.id.tv_jobQuota);
                jobDetail(jobid);
                mDialog.show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private void jobDetail(final String jobID){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:80/android_api/api/jobdetail?JobID="+jobID,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray job_detail = response.getJSONArray("data");
                            for(int i = 0; i<job_detail.length();i++){
                                JSONObject jsonObject = job_detail.getJSONObject(i);

                                String job_name = jsonObject.getString("JobName");
                                String job_desc = jsonObject.getString("JobDesc");
                                String job_quota = jsonObject.getString("JobQuota");
                                jobName.setText(job_name);
                                jobDesc.setText("Job Desc: "+job_desc);
                                jobQuota.setText("Job Quota: "+job_quota);
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

    private void applyJob(final String nim,final String id){
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
                "http://10.0.2.2:80/android_api/api/applyjob",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("message");
                            if(!res.equals("Already Applied!")){
                                Toast.makeText(context, "Successfully applied!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, "Already applied!", Toast.LENGTH_SHORT).show();
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
    private void checkJob(String nim,final Callback onCallback){
        JSONObject param = new JSONObject();
        try{
            param.put("NIM", nim);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2/android_api/api/checkjob",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("data")&&!response.isNull("data")) {
                                JSONArray detailAppliment = response.getJSONArray("data");
                                applimentList = new ArrayList<>();
                                for (int i = 0; i < detailAppliment.length(); i++) {
                                    JSONObject jsonObject = detailAppliment.getJSONObject(i);
                                    String getJobID = jsonObject.getString("JobID");
                                    TrAppliment tempAppliment = new TrAppliment();
                                    tempAppliment.setJobID(getJobID);
                                    applimentList.add(tempAppliment);
                                }
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
    public interface Callback{
        void onSuccess (ArrayList<TrAppliment> tempTrAppliment);

    }
}
