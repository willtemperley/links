package io.temperley.domain;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by willtemperley@gmail.com on 2/25/17.
 */
public abstract class PGAccess {

    private Connection connection;

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PGAccess() {

        InputStream resource = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        try {
            properties.load(resource);

            String url = properties.getProperty("pg.url");
            String uname = properties.getProperty("pg.u");
            String passw = properties.getProperty("pg.p");

            connection = DriverManager.getConnection(url, uname, passw);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    Connection getConnection() {
        return connection;
    }
}
