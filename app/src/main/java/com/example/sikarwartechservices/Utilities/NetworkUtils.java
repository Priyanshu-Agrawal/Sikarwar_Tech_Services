package com.example.sikarwartechservices.Utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.StrictMode;
import android.util.Log;

import com.example.sikarwartechservices.Constants.Secrets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkUtils {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        return false;
    }


    private static final int DEFAULT_PORT = 80; // Default HTTP port
    private static final int timeout = 5000;
    private static final String serverAddress = Secrets.DBServerIP;

    public static boolean isServerReachable() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(serverAddress, DEFAULT_PORT), timeout);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e("ConnectionHelper", "Connection Close Failed: " + e.getMessage());
            }
        }
    }
}
