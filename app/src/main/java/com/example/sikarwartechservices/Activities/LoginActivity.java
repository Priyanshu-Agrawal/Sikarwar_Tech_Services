package com.example.sikarwartechservices.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sikarwartechservices.Constants.Constants;
import com.example.sikarwartechservices.Constants.Secrets;
import com.example.sikarwartechservices.R;
import com.example.sikarwartechservices.Utilities.ConnectionHelper;
import com.example.sikarwartechservices.Utilities.LayoutUtils;
import com.example.sikarwartechservices.Utilities.NetworkUtils;
import com.example.sikarwartechservices.Utilities.PreferenceManager;
import com.example.sikarwartechservices.Utilities.SQLUtils;
import com.example.sikarwartechservices.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(this);
//        forceLogin(); //force Login For Development
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void forceLogin() {
        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
        preferenceManager.putString(Constants.KEY_USER_FULL_NAME, "Developer");

    }

    private void setListeners() {
        binding.buttonSignIn.setOnClickListener(view -> signIn());
    }

    private void loading (Boolean isLoading){
        if(isLoading) {
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignIn.setVisibility(View.VISIBLE);

        }
    }

    private void signIn() {
        if (!validateInputs()){
            return;
        }
        if (checkConnection()){
            ConnectionHelper connectionHelper = new ConnectionHelper(null);
            Connection connection= connectionHelper.createConnection();
            if (connection == null){
                Snackbar.make(binding.getRoot(), "Connection with Database Failed", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", view -> signIn())
                        .setActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error))
                        .show();
            }else {
                if(statAuthentication()){
                    if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
                        startActivity(new Intent(this, DashboardActivity.class));
                        finish();
                    }
                }
            }
        }
    }

    private Boolean statAuthentication() {
        loading(true);
        String database = Secrets.productionDatabaseName;
        String procedureName = Secrets.loginProcedureName;
        Object[] params ={binding.editTextUserID.getText().toString(), binding.editTextPassword.getText().toString()};
        try (ResultSet resultSet = SQLUtils.QueryExecutor.executeStoredProcedureByCall(database, SQLUtils.QueryBuilder.storedProcedure(procedureName, params))){
            if (resultSet!=null && resultSet.next()){
                LayoutUtils.snackBarCreator(binding.getRoot(),"Authentication Successful", Snackbar.LENGTH_LONG,ContextCompat.getColor(this, R.color.white), R.color.success);
                /*Snackbar.make(binding.getRoot(), "Authentication Successful", Snackbar.LENGTH_LONG)
                        .setActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.success))
                        .show();*/

                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                preferenceManager.putString(Constants.KEY_USER_FULL_NAME, resultSet.getString("fullname"));

                loading(false);
                return true;
            }else{
                LayoutUtils.snackBarCreator(binding.getRoot(),"Authentication Failed", 1000,ContextCompat.getColor(this, R.color.white), R.color.error);
                /*Snackbar.make(binding.buttonSignIn, "Authentication Failed", Snackbar.LENGTH_LONG)
                        .setActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error))
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .show();*/
                LayoutUtils.shakeView(binding.buttonSignIn);
            }
        } catch (SQLException e) {
            Log.e("Tag LoginActivity", "SQL Exception: "+ e.getMessage());
        }
        loading(false);
        return false;
    }

    private Boolean validateInputs() {
        LayoutUtils.hideKeyboardAndClearFocus(binding.getRoot());
        return LayoutUtils.validateInputs(binding.editTextUserID, binding.editTextPassword);
    }

    private boolean checkConnection() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            LayoutUtils.snackBarCreator(binding.getRoot(),"Internet Not Connected", Snackbar.LENGTH_INDEFINITE,ContextCompat.getColor(this, R.color.white), R.color.error, "Retry", view -> checkConnection());
                /*Snackbar.make(binding.getRoot(), "Internet Not Connected", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", view -> checkConnection())
                        .setActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error))
                        .show();*/
                return false;
        }
        if (!NetworkUtils.isServerReachable()) {
            LayoutUtils.snackBarCreator(binding.getRoot(),"Server Not Reachable", Snackbar.LENGTH_INDEFINITE,ContextCompat.getColor(this, R.color.white), R.color.error, "Retry", view -> checkConnection());
            return false;
        }
        return true;
    }
}