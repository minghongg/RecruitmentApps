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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasimahasiswa.Model.Mahasiswa;
import com.example.aplikasimahasiswa.R;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout _Drawer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Toolbar ApplicationToolbar = findViewById(R.id.main_toolbar);
        NavigationView navigationView = findViewById(R.id.main_navigation_view);
//
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(ApplicationToolbar);

        _Drawer = findViewById(R.id.drawer);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,_Drawer,ApplicationToolbar,
                R.string.nav_drawer_open,R.string.nav_drawer_close);
        _Drawer.addDrawerListener(toogle);
        toogle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    new VacancyActivity()).commit();
            navigationView.setCheckedItem(R.id.user_menu_vacancy);
        }
        View headerView  = navigationView.getHeaderView(0);
        ImageView ProfilePicture = headerView.findViewById(R.id.profilePicture);
        TextView Username = headerView.findViewById(R.id.tv_username);
        TextView Nim = headerView.findViewById(R.id.tv_nim);

        //load
        SharedPref sharedPref = new SharedPref(MainActivity.this);
        Mahasiswa mahasiswa = sharedPref.load();
        String name = mahasiswa.get_name();
        String nim = mahasiswa.get_nim();
        String photo = mahasiswa.get_photo();
        Username.setText(name);
        Nim.setText(nim);
        CircularImageView imageView = headerView.findViewById(R.id.profilePicture);
        Picasso.with(MainActivity.this).load(photo).into(imageView);

        ProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                        new ProfileActivity()).addToBackStack(null).commit();
                _Drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selected = null;
            switch (item.getItemId()){
            case R.id.user_menu_vacancy :
                selected = new VacancyActivity();
                break;
            case R.id.user_menu_status :
                selected = new StatusActivity();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,selected).commit();
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
//    public void checkData(){
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("id");
//        Bundle bundle=new Bundle();
//        bundle.putString("message", );
//        //set Fragmentclass Arguments
//        Fragmentclass fragobj=new Fragmentclass();
//        fragobj.setArguments(bundle)
//    }
}
