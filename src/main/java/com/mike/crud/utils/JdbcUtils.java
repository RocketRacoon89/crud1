package com.mike.crud.utils;

import java.sql.*;

public class JdbcUtils {
    private static Connection connection = null;

    private static String url = "jdbc:mysql://localhost:3306/test_schema";
    private static String userName = "Mike";
    private static String pwd = "5436";

    private JdbcUtils() {
    }

    public static synchronized Connection getCon() {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection(url, userName, pwd);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                return connection;
            }
        return connection;
    }

    public static synchronized PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getCon().prepareStatement(sql);
    }

    public static synchronized PreparedStatement getPreparedStatementWithGeneratedKeys(String sql) throws SQLException {
        return getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public static synchronized void closeCon() throws SQLException {
        if(connection!=null) {
            connection.close();
            connection = null;
        }
    }
}
