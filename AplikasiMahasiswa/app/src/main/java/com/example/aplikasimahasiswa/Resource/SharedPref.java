package com.example.aplikasimahasiswa.Resource;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.Model.Perusahaan;

public class SharedPref {
    private SharedPreferences sharedPreferences;

    public SharedPref(Context context){
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);

    }

    public void save(Mahasiswa mahasiswa){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email",mahasiswa.get_email());
        editor.putString("Password", mahasiswa.get_password());
        editor.putString("NIM",mahasiswa.get_nim());
        editor.putString("Photo",mahasiswa.get_photo());
        editor.putString("Name",mahasiswa.get_name());
        editor.putString("Role",mahasiswa.get_role());
        // you can use apply() or commit() to saving data to shared preferences xml
        editor.apply();
    }

    public void savePerusahaan(Perusahaan perusahaan){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PerusahaanID",perusahaan.getPerusahaanID());
        editor.putString("PerusahaanEmail",perusahaan.getPerusahaanEmail());
        editor.putString("PerusahaanName",perusahaan.getPerusahaanName());
        editor.putString("PerusahaanAddress",perusahaan.getPerusahaanAddress());
        editor.putString("PerusahaanPassword",perusahaan.getPerusahaanPassword());
        editor.apply();
    }
    public Perusahaan loadPerusahaan(){
        Perusahaan perusahaan = new Perusahaan();
        perusahaan.setPerusahaanEmail(sharedPreferences.getString("PerusahaanEmail",""));
        perusahaan.setPerusahaanAddress(sharedPreferences.getString("PerusahaanAddress",""));
        perusahaan.setPerusahaanID(sharedPreferences.getString("PerusahaanID",""));
        perusahaan.setPerusahaanName(sharedPreferences.getString("PerusahaanName",""));
        perusahaan.setPerusahaanPassword(sharedPreferences.getString("PerusahaanPassword",""));
        return perusahaan;
    }
    public Mahasiswa load(){
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.set_email(sharedPreferences.getString("Email", ""));
        mahasiswa.set_password(sharedPreferences.getString("Password", ""));
        mahasiswa.set_nim(sharedPreferences.getString("NIM",""));
        mahasiswa.set_photo(sharedPreferences.getString("Photo",""));
        mahasiswa.set_name(sharedPreferences.getString("Name",""));
        mahasiswa.set_role(sharedPreferences.getString("Role",""));
        return mahasiswa;
    }
    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
