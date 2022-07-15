package ru.nsd.services.lifeDirection;

import org.springframework.stereotype.Component;
import ru.nsd.exceptions.UserBuildException;
import ru.nsd.models.LifeDirection;
import ru.nsd.services.hikaripool.HikariPoolService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LifeDirectionDao {

    public void add(List<LifeDirection> lifeDirections) {
        String sql = "INSERT INTO LifeDirection (userId, level, name, number, parentNumber) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = HikariPoolService.getConnection();
            for (LifeDirection lifeDirection : lifeDirections) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setObject(1, lifeDirection.getUserId());
                preparedStatement.setObject(2, lifeDirection.getLevel());
                preparedStatement.setString(3, lifeDirection.getName());
                preparedStatement.setObject(4, lifeDirection.getNumber());
                preparedStatement.setObject(5, lifeDirection.getParentNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new UserBuildException();
        }
    }

    public List<LifeDirection> get(Long userId) {
        String sql = "SELECT ID, USERID, LEVEL, NAME, NUMBER, PARENTNUMBER from LifeDirection WHERE userId = ?";
        List<LifeDirection> lifeDirections = new ArrayList<>();
        try {
            Connection connection = HikariPoolService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                userId = resultSet.getLong(2);
                Integer level = resultSet.getInt(3);
                String name = resultSet.getString(4);
                Integer number = resultSet.getInt(5);
                Integer parentNumber = resultSet.getInt(6);

                LifeDirection lifeDirection = new LifeDirection(id, userId, level, name, number, parentNumber);
                lifeDirections.add(lifeDirection);
            }
            return lifeDirections;
        } catch (SQLException exception) {
            throw new UserBuildException();
        }
    }

}
