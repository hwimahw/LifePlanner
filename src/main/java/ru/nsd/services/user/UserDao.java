package ru.nsd.services.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nsd.exceptions.UserBuildException;
import ru.nsd.models.User;
import ru.nsd.services.hikaripool.HikariPoolService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User get(User user) {
        return jdbcTemplate.queryForObject("SELECT * FROM USER WHERE login = ? and password = ?", new UserRowMapper(), user.getLogin(), user.getPassword());
    }

    public void add(User user) {
        jdbcTemplate.update("INSERT INTO USER (login, password) VALUES (?, ?)", user.getLogin(), user.getPassword());
    }
}
