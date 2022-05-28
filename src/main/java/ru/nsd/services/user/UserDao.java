package ru.nsd.services.user;

import ru.nsd.exceptions.UserBuildException;
import ru.nsd.models.Role;
import ru.nsd.models.User;
import ru.nsd.services.hikaripool.HikariPoolService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User get(User user){
        String sql = "SELECT * FROM USER WHERE login = ? and password = ?";
        try {
            Connection connection = HikariPoolService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                return null;
            }

            long id = resultSet.getInt(1);
            String log = resultSet.getString(2);
            String pass = resultSet.getString(3);

            return new User(id, log, pass);
        } catch (SQLException exception){
            throw new UserBuildException();
        }
    }

    public void add(User user){
        String sql = "INSERT INTO USER (login, password) VALUES (?, ?);";
        try {
            Connection connection = HikariPoolService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException exception){
            throw new UserBuildException();
        }
    }
}
