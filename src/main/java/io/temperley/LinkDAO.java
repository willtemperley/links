package io.temperley;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 16/04/2017.
 */
public class LinkDAO extends PGAccess {


    public void persist(Link link) {

        Connection connection = getConnection();
        if (link.getId() == null) {


            String insert =
                    "INSERT INTO links (url, description) VALUES (?, ?)";
            System.out.println(insert);
            try {
                PreparedStatement statement = connection.prepareStatement(insert);
                statement.setString(1, link.getUrl());
                statement.setString(2, link.getDescription());
                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            try {
                String update = "UPDATE links SET url = ?, description = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(update);
                statement.setString(1, link.getUrl());
                statement.setString(2, link.getDescription());
                statement.setLong(3, link.getId());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public List<Link> list() {

        List<Link> links = new ArrayList<>();
        String query = "SELECT id, url, description FROM links";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Link link = new Link();
                link.setId(resultSet.getLong(1));
                link.setUrl(resultSet.getString(2));
                link.setDescription(resultSet.getString(3));
                links.add(link);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return links;
    }
}
