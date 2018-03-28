package com.codedose.oag.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class Jdbc {

    static Connection createConnection(DB2Properties db2Properties)  {
        try {
            DriverManager.registerDriver((Driver) Class.forName(db2Properties.DB_DRIVER).newInstance());
            String url = db2Properties.DB_URL;
            Properties connectionProps = new Properties();
            connectionProps.put("user", db2Properties.DB_USER);
            connectionProps.put("password", db2Properties.DB_PASSWORD);

            return DriverManager.getConnection(url, connectionProps);
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException("Can't create connection to database", e);
        }
    }

    static void closeConnection(Connection sqlConnection) {
        try {
            sqlConnection.rollback();
            sqlConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Can't close connection", e);
        }
    }
}
