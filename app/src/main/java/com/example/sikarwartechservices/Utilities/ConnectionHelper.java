package com.example.sikarwartechservices.Utilities;

import android.util.Log;

import com.example.sikarwartechservices.Secrets.Constants;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper implements Constants {
    String userName,password,DBServerIP,portNo,databaseName;
    public ConnectionHelper() {
        userName = Constants.userName;
        password = Constants.password;
        DBServerIP = Constants.DBServerIP;
        portNo = Constants.portNo;
        databaseName = Constants.databaseName;
    }
    public Connection connection() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionURL = "jdbc:jtds:sqlserver://" + DBServerIP + ":" + portNo + "/" + databaseName, userName, password;
            return DriverManager.getConnection(connectionURL);
        }catch (Exception e) {
            Log.e("ConnectionHelper", e.getMessage());
            return null;
        }
    }
}
