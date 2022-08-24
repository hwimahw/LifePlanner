package ru.nsd.services.lifeDirection;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.nsd.exceptions.UserBuildException;
import ru.nsd.models.LifeDirection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LifeDirectionExtractor implements ResultSetExtractor<List<LifeDirection>> {

    @Override
    public List<LifeDirection> extractData(ResultSet rs) throws SQLException, DataAccessException {
        return getLifeDirections(rs);
    }

    private List<LifeDirection> getLifeDirections(ResultSet resultSet) {
        List<LifeDirection> lifeDirections = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                Long userId = resultSet.getLong(2);
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
