package com.example.sikarwartechservices.Utilities;

import android.util.Log;

import com.example.sikarwartechservices.Constants.Secrets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {
    public static class QueryBuilder{
        public static String storedProcedure(String procedureName, Object... params){
            String call = null;
            call = "{call " + procedureName + "(";
            for (int i = 0; i < params.length; i++) {
                call += (params[i] instanceof String || params[i] instanceof Date) ? ("'" + params[i] + "'") : (params[i]);
                if (i < params.length - 1) {
                    call += ", ";
                }
            }
            call += ")}";
            return call;
        }
    }

    public static class QueryExecutor{
        private static Connection connection = null;

        public static void setConnectionDatabase(String databaseName){
            ConnectionHelper connectionHelper = new ConnectionHelper(databaseName);
            connection = connectionHelper.createConnection();
        }
        public static ResultSet executeStoredProcedureByCall(String call) {
            if (connection == null) {
                setConnectionDatabase(Secrets.testDatabaseName);
            }
            try {
                CallableStatement callableStatement = QueryExecutor.prepareCall(call);
                if (callableStatement != null) {
                    return callableStatement.executeQuery();
                } else {
                    Log.e("QueryExecutor", "CallableStatement is null");
                }
            } catch (SQLException e) {
                Log.e("QueryExecutor", "executeStoredProcedureCall: " + e.getMessage());
            }
            return null;
        }

        public static ResultSet executeStoredProcedureByCall(String database,String call) {
            setConnectionDatabase(database);
            try {
                CallableStatement callableStatement = QueryExecutor.prepareCall(call);
                if (callableStatement != null) {
                    return callableStatement.executeQuery();
                } else {
                    Log.e("QueryExecutor", "CallableStatement is null");
                }
            } catch (SQLException e) {
                Log.e("QueryExecutor", "executeStoredProcedureCall: " + e.getMessage());
            }
            return null;
        }
        public static CallableStatement prepareCall(String call) {
            if (connection != null) {
                try {
                    return connection.prepareCall(call);
                } catch (SQLException e) {
                    Log.e("TAG Error QueryExecutor", "Error: \n" + e.getMessage());
                }
            }
            return null;
        }
    }
}
