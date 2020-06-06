package com.example.aplikasimahasiswa.Resource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText _etEmail,_etPassword;
    private TextView _tvErrorMessage;
    private Button _btnLogin,_btnEye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        _tvErrorMessage = findViewById(R.id.login_TextView_errorMessage);
        _btnEye=findViewById(R.id.login_button_eye);
        _etEmail = findViewById(R.id.login_email);
        _etPassword =findViewById(R.id.login_password);
        _btnLogin = findViewById(R.id.login_btnlogin);
        hideAndShow();
        //shared pref
        SharedPref sharedPref = new SharedPref(LoginActivity.this);
        Mahasiswa mahasiswa = sharedPref.load();

        Perusahaan perusahaan = sharedPref.loadPerusahaan();

        if(!mahasiswa.get_email().equals("") && !mahasiswa.get_password().equals("")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if(!perusahaan.getPerusahaanEmail().equals("") && !perusahaan.getPerusahaanPassword().equals("")){
            Intent intent = new Intent(LoginActivity.this,PerusahaanMainActivity.class);
            startActivity(intent);
            finish();
        }
        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = _etEmail.getText().toString().trim();
                String Password = _etPassword.getText().toString().trim();
                if(Password.isEmpty() || Password.equals(" ") || Email.isEmpty() || Email.equals(" ")){
                    Toast.makeText(LoginActivity.this, "Please Insert your email and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    login(Email, Password);
                }
            }
        });
    }
    private void login(final String Email, final String Password) {
        JSONObject param = new JSONObject();
        try{
            param.put("Email", Email);
            param.put("Password", Password);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/android_api/api/login",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response = response.getJSONObject("data");
                            String UserEmail = response.getString("Email");
                            String nim = response.getString("NIM");
                            String photo = response.getString("Photo");
                            String name = response.getString("Nama");
                            String role = response.getString("Role");
                            if(UserEmail.equals(Email)){
                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                Mahasiswa mahasiswa = new Mahasiswa();
                                mahasiswa.set_nim(nim);
                                mahasiswa.set_email(Email);
                                mahasiswa.set_password(Password);
                                mahasiswa.set_photo(photo);
                                mahasiswa.set_name(name);
                                mahasiswa.set_role(role);

                                //saved the user data in shared preferences
                                SharedPref sharedPref = new SharedPref(LoginActivity.this);
                                sharedPref.save(mahasiswa);

                                Intent homeIntent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(homeIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error :"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginPerusahaan(Email,Password);
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(request);
    }
    private void loginPerusahaan(final String Email, final String Password) {
        JSONObject param = new JSONObject();
        try{
            param.put("Email", Email);
            param.put("Password", Password);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:80/android_api/api/loginperusahaan",
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response = response.getJSONObject("data");
                            String PerusahaanEmail = response.getString("Email");
                            String PerusahaanID = response.getString("PerusahaanID");
                            String PerusahaanName = response.getString("NamaPerusahaan");
                            String PerusahaanPassword = response.getString("Password");
                            String PerusahaanAddress = response.getString("PerusahaanAddress");
                            if(PerusahaanEmail.equals(Email)){
                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                Perusahaan perusahaan = new Perusahaan();
                                perusahaan.setPerusahaanID(PerusahaanID);
                                perusahaan.setPerusahaanName(PerusahaanName);
                                perusahaan.setPerusahaanEmail(PerusahaanEmail);
                                perusahaan.setPerusahaanPassword(PerusahaanPassword);
                                perusahaan.setPerusahaanAddress(PerusahaanAddress);
                                //saved the user data in shared preferences
                                SharedPref sharedPref = new SharedPref(LoginActivity.this);
                                sharedPref.savePerusahaan(perusahaan);

                                Intent intent = new Intent(LoginActivity.this,PerusahaanMainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            //loading.setVisibility(View.GONE);
                            //btnLogin.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            //loading.setVisibility(View.GONE);
                            //btnLogin.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error :"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //loading.setVisibility(View.GONE);
                        //btnLogin.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(LoginActivity.this, "Error : "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // don't forget to add the request to queue here
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(request);
    }

    public void hideAndShow(){
        _btnEye.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch ( event.getAction() ) {
                            case MotionEvent.ACTION_DOWN:
                                _etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                                break;
                            case MotionEvent.ACTION_UP:
                                _etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {

    }
}
