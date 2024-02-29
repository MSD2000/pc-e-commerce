package com.ru.mag.db.jdbc.database;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:oracle:thin:@172.16.251.135:1521:orcl";
    private static final String USER = "c##ex23_martin_coursetask";
    private static final String PASSWORD = "Msd959595msD";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Execute the query and return the result set
        return preparedStatement.executeQuery();
    }

    public static ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set parameters for the prepared statement
        for (int i = 0; i < parameters.length; i++)
            preparedStatement.setObject(i + 1, parameters[i]);

        // Execute the query and return the result set
        return preparedStatement.executeQuery();

    }

}
