package ru.nsd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String URL = "jdbc:h2:~/test1;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";
    public static final String USER = "sa";
    public static final String PASS = "";

    private static Connection connection;

    static{
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
