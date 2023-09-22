package com.example.sikarwartechservices.Utilities;

import android.os.StrictMode;
import android.util.Log;

import com.example.sikarwartechservices.Constants.Secrets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper implements Secrets {
    String userName,password,DBServerIP,portNo,databaseName;
    public ConnectionHelper(String database) {
        this.userName = Secrets.userName;
        this.password = Secrets.password;
        this.DBServerIP = Secrets.DBServerIP;
        this.portNo = Secrets.portNo;
        this.databaseName = (database != null) ? database : Secrets.testDatabaseName;
    }
    public  Connection createConnection() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionURL = "jdbc:jtds:sqlserver://"+ DBServerIP + ":" + portNo +";" + "databasename=" + databaseName +";user="+userName+";password="+password+";";
            return DriverManager.getConnection(connectionURL);
        }catch (SQLException | ClassNotFoundException E) {
            Log.e("ConnectionHelper", "Connection Failed: " + E.getMessage());
            return null;
        }
    }
}
