package io.temperley.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by will on 01/05/2017.
 */
public class TokenDAO extends PGAccess implements DAO<Token> {

    @Override
    public Token create(Token entity) {
        String insert =
                "INSERT INTO token (value) VALUES (?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(insert);
            statement.setString(1, entity.getValue());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long aLong = generatedKeys.getLong(1);
                entity.setIdentifier(aLong);
            }
            return entity;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Token update(Token entity) {
        try {
            String update = "UPDATE token SET value = ? WHERE id = ?";
            PreparedStatement statement = getConnection().prepareStatement(update);
            statement.setString(1, entity.getValue());
            statement.setLong(2, entity.getIdentifier());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public Token delete(Token entity) {
        return null;
    }

    @Override
    public Collection<Token> all() {
        List<Token> entities = new ArrayList<>();
        String query = "SELECT id, value FROM token";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Token token = new Token();
                token.setIdentifier(resultSet.getLong(1));
                token.setValue(resultSet.getString(2));
                entities.add(token);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return entities;
    }
}
