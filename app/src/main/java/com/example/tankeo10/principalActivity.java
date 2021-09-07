package com.example.tankeo10;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class principalActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        bottomNavigationView = findViewById(R.id.BottomNavigationView);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView,new PerfilFragment()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_perfil:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new PerfilFragment()).commit();
                        return true;
                    case R.id.nav_tankeo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new TankeoFragment()).commit();
                        return true;
                    case R.id.nav_recarga:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new RecargaFragment()).commit();
                        return true;
                    case R.id.nav_compartir:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new CompartirFragment()).commit();
                        return true;
                }
                return false;
            }
        });



    }

}


