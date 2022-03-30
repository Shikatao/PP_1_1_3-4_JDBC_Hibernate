package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String DB_URl = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static Connection connection = null;


    private static void openConnection() {
        try {
            connection = DriverManager.getConnection(DB_URl, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // реализуйте настройку соеденения с БД
    }

    public static Connection getConnection() {
        openConnection();
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {

            }
        }
    }
}
