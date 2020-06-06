package com.example.aplikasimahasiswa.Resource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.Model.Perusahaan;
import com.example.aplikasimahasiswa.R;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class PerusahaanMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout _Drawer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_perusahaan_main);

        Toolbar ApplicationToolbar = findViewById(R.id.main_toolbar_company);
        NavigationView navigationView = findViewById(R.id.company_navigation_view);
        Button btnLogout = findViewById(R.id.btn_logout);

        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(ApplicationToolbar);

        _Drawer = findViewById(R.id.drawer);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,_Drawer,ApplicationToolbar,
                R.string.nav_drawer_open,R.string.nav_drawer_close);
        _Drawer.addDrawerListener(toogle);
        toogle.syncState();
        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = PerusahaanMainActivity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container,new JobDetailFragment());
            fragmentTransaction.addToBackStack(null).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    new ApplimentFragment()).commit();
            navigationView.setCheckedItem(R.id.company_menu_list_mahasiswa);
        }
        View headerView  = navigationView.getHeaderView(0);
        TextView Username = headerView.findViewById(R.id.header_company_name);

        //load
        final SharedPref sharedPref = new SharedPref(PerusahaanMainActivity.this);
        Perusahaan perusahaan = sharedPref.loadPerusahaan();
        String name = perusahaan.getPerusahaanName();
        Username.setText(name);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref.logout();
                Intent intent = new Intent(PerusahaanMainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selected = null;
        switch (item.getItemId()){
            case R.id.company_menu_profile:
                selected = new JobDetailFragment();
                break;
            case R.id.company_menu_list_mahasiswa :
                selected = new ApplimentFragment();
                break;

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,selected).addToBackStack(null).commit();
        _Drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(_Drawer.isDrawerOpen(GravityCompat.START)){
            _Drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}
