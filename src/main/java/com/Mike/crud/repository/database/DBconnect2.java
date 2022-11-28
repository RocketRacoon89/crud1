package com.Mike.crud.repository.database;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect2 {
    private static Connection connection = null;
    private static DBconnect2 dBconnect;

    private static String url = "jdbc:mysql://localhost:3306/test_schema";
    private static String userName = "Mike";
    private static String pwd = "5436";

    private DBconnect2() {
    }

    public static synchronized Connection getCon() throws SQLException {
        if(connection==null) {
            connection = DriverManager.getConnection(url, userName, pwd);
            return connection;
        }
        return connection;
    }

    public static synchronized void closeCon() throws SQLException {
        if(connection!=null) {
            connection.close();
            connection = null;
        }
    }
}
