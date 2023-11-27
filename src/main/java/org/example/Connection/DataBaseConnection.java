package org.example.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static DataBaseConnection instance;
    private String url = "jdbc:mysql://localhost:3306/banca?serverTimezone=UTC&useSSL=true&characterEncoding=UTF-8";
    private String usuario = "root";
    private String password = "admin.123";
    private Connection conexion;

    private DataBaseConnection() throws SQLException {
        conexion = DriverManager.getConnection(url, usuario, password);
    }

    public static DataBaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    public void close() throws SQLException {

        if (conexion != null) {
            conexion.close();
        }

    }

    public Connection getConexion() {
        return conexion;
    }
}
