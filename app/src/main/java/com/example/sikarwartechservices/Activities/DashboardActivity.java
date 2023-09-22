package com.example.sikarwartechservices.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sikarwartechservices.Constants.Constants;
import com.example.sikarwartechservices.Utilities.PreferenceManager;
import com.example.sikarwartechservices.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(!preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeTextValues();

        setListeners();
    }

    private void initializeTextValues() {
        binding.textName.setText(preferenceManager.getString(Constants.KEY_USER_FULL_NAME));
    }

    private void setListeners() {
        binding.imageSignOut.setOnClickListener(v ->{
            preferenceManager.clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}