package com.ipc2.api.apirest.reportes.Conexion;



import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConexionDatos {
    private final String DB = "ESPECIALIDADMEDICO";
    private final String URL = "jdbc:mysql://localhost:3306/clinica_database";
    private final String USER = "newuser";
    private final String PASS = "password";
    private static ConexionDatos dataSource;
    private BasicDataSource basicDataSource = null;

    private ConexionDatos() {
        this.basicDataSource = new BasicDataSource();
        this.basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.basicDataSource.setUsername("newuser");
        this.basicDataSource.setPassword("password");
        this.basicDataSource.setUrl("jdbc:mysql://localhost:3306/clinica_database");
        this.basicDataSource.setMinIdle(5);
        this.basicDataSource.setMaxIdle(20);
        this.basicDataSource.setMaxTotal(50);
        this.basicDataSource.setMaxWaitMillis(-1L);
    }

    public static ConexionDatos getInstance() {
        if (dataSource == null) {
            dataSource = new ConexionDatos();
            return dataSource;
        } else {
            return dataSource;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.basicDataSource.getConnection();
    }

    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}

