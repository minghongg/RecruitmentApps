package com.example.aplikasimahasiswa.Resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class ProfileActivity extends Fragment {

    Bundle bundle;

    private TextView tvName;
    private TextView tvNIM;
    private TextView tvJurusan;
    private TextView tvPlaceOfBirth;
    private TextView tvGPA;
    private CircularImageView profilePicture;
    private TextView tvCV;
    private Button btnLogout;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_profile,
                container,false);

        tvName = view.findViewById(R.id.name);
        tvNIM = view.findViewById(R.id.nim);
        tvJurusan = view.findViewById(R.id.jurusan);
        tvPlaceOfBirth = view.findViewById(R.id.birthPlace);
        tvGPA = view.findViewById(R.id.gpa);
        profilePicture = view.findViewById(R.id.profilePicture);
        tvCV = view.findViewById(R.id.cv);
        btnLogout = view.findViewById(R.id.btnLogout);

        final SharedPref sharedPref = new SharedPref(getContext());


        Mahasiswa mahasiswa = sharedPref.load();
        String nim = "";
        String role = mahasiswa.get_role();
        CircularImageView imageView = view.findViewById(R.id.profilePicture);
        if(role==""){
            btnLogout.setVisibility(View.GONE);
            bundle = getArguments();
            if(bundle!=null){
                nim = bundle.getString("NIM");
            }
        }else{
            nim = mahasiswa.get_nim();
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPref.logout();
                    Intent intent = new Intent(getContext(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            });
        }
        getData(nim,view);


        return view;
    }
    private void getData(final String nim,final View view) {

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
                "http://10.0.2.2:80/android_api/api/getProfile",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response = response.getJSONObject("data");
                            String Nim = response.getString("NIM");
                            if(Nim.equals(nim)){
                                String name = response.getString("Nama");
                                String jurusan = response.getString("Jurusan");
                                String placeOfBirth = response.getString("PlaceofBirth");
                                String dateOfBirth = response.getString("DOB");
                                Double gpa = response.getDouble("IPK");
                                String profilePicture = response.getString("Photo");
                                String url = response.getString("Photo");
                                String cv = response.getString("CurriculumVitae");
                                CircularImageView imageView = view.findViewById(R.id.profilePicture);
                                Picasso.with(getContext()).load(url).into(imageView);
                                tvName.setText("Name : "+name);
                                tvNIM.setText("NIM : "+nim);
                                tvJurusan.setText("Jurusan : "+jurusan);
                                tvPlaceOfBirth.setText("Tempat Tanggal Lahir : "+placeOfBirth+", "+dateOfBirth);
                                tvGPA.setText("IPK : "+df2.format(gpa));
                                tvCV.setText("Curriculum Vitae: "+cv);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),"Error :"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

}
