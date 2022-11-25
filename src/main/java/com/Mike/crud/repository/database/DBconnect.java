package com.Mike.crud.repository.database;

import java.sql.*;

public class DBconnect {
    private static DBconnect dBconnect;
    private static String url = "jdbc:mysql://localhost:3306/test_schema";
    private static String userName = "Mike";
    private static String pwd = "5436";
    private static Connection connection;

    private DBconnect() {

    }

    public static synchronized Connection getCon() {
        if (dBconnect == null) {

            try {
                connection = DriverManager.getConnection(url, userName, pwd);
            } catch (SQLException e) {
                System.out.println("Connection ERROR!");
                System.out.println(e);
            }
            if (connection != null) {
                System.out.println("Connection is OK!");
            } else {
                System.out.println("ERROR");
            }
            return connection;
        }
        return connection;
    }

}
