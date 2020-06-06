package com.example.aplikasimahasiswa.Resource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.ListMahasiswaApplyItem;
import com.example.aplikasimahasiswa.Model.Perusahaan;
import com.example.aplikasimahasiswa.R;
import com.example.aplikasimahasiswa.repository.ListMahasiswaApplyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddJobActivity extends AppCompatActivity {
    EditText jobQuota,jobDesc,jobTitle;
    Button btnInsert;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        jobTitle = findViewById(R.id.et_job_title);
        jobQuota = findViewById(R.id.et_job_quota);
        jobDesc = findViewById(R.id.et_job_description);
        btnInsert = findViewById(R.id.btn_insert);
        SharedPref sharedPref = new SharedPref(AddJobActivity.this);
        Perusahaan perusahaan = new Perusahaan();
        perusahaan = sharedPref.loadPerusahaan();
        id = perusahaan.getPerusahaanID();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String job_title = jobTitle.getText().toString();
                String job_quota = jobQuota.getText().toString();
                String job_desc =jobDesc.getText().toString();
                if(!job_quota.isEmpty()&& !job_quota.equals(" ")){
                    int jobQuota = Integer.parseInt(job_quota);
                    if(jobQuota>0 && !job_title.isEmpty() && !job_title.equals(" ") && !job_desc.isEmpty() && !job_desc.equals(" ") && !job_quota.isEmpty() && !job_quota.equals(" ")){
                        insertJob(job_title,job_desc,job_quota,id);
                    }
                }
                else{
                    Toast.makeText(AddJobActivity.this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void insertJob(final String JobTitle, final String JobDesc, final String JobQuota, final String PerusahaanID){
        JSONObject param = new JSONObject();
        try{
            param.put("PerusahaanID", PerusahaanID);
            param.put("JobName",JobTitle);
            param.put("JobDesc",JobDesc);
            param.put("JobQuota",JobQuota);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(AddJobActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/android_api/api/addjob",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if(message.equals("Insert Successful!")){
                                Toast.makeText(AddJobActivity.this, "Insert Job Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddJobActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddJobActivity.this, "Error"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddJobActivity.this, "Error : "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(AddJobActivity.this);
        queue.add(request);
    }
}
