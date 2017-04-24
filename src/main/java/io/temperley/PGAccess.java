package io.temperley;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by willtemperley@gmail.com on 2/25/17.
 */
public abstract class PGAccess {

    private Connection connection;

    public PGAccess() {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/links", "will", "tolm[44]");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
