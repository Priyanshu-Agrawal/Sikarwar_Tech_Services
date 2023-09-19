package com.example.sikarwartechservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sikarwartechservices.Utilities.ConnectionHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connectionHelper.connection();
    }
}