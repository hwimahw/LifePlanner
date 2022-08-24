package ru.nsd.services.user;

import org.springframework.jdbc.core.RowMapper;
import ru.nsd.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) {
        return getUser(resultSet);
    }

    private User getUser(ResultSet resultSet) {
        try {
            long id = resultSet.getInt(1);
            String log = resultSet.getString(2);
            String pass = resultSet.getString(3);
            return new User(id, log, pass);
        } catch (SQLException ex) {
            return null;
        }
    }

}
