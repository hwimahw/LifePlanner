package ru.nsd.services.lifeDirection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nsd.models.LifeDirection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LifeDirectionDao {

    private final JdbcTemplate jdbcTemplate;

    public LifeDirectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(List<LifeDirection> lifeDirections) {
        for (LifeDirection lifeDirection : lifeDirections) {
            jdbcTemplate.update("INSERT INTO LifeDirection (userId, level, name, number, parentNumber) VALUES (?, ?, ?, ?, ?)",
                    lifeDirection.getUserId(), lifeDirection.getLevel(), lifeDirection.getName(), lifeDirection.getNumber(), lifeDirection.getParentNumber());
        }
    }

    public List<LifeDirection> get(Long userId) {
        return jdbcTemplate.query("SELECT ID, USERID, LEVEL, NAME, NUMBER, PARENTNUMBER from LifeDirection WHERE userId = ?", new LifeDirectionExtractor(), userId);
    }

    public void delete(Long userID) {
        jdbcTemplate.update("DELETE FROM LIFEDIRECTION WHERE USERID = ?", userID);
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        System.out.println(map.hashCode());
        map.put(new String("ddjndjcndjcndjcndjcnjdc"), "2dcjdncdncjdcndc");
        map.put(new String("ddjndjcndjjdcjndcjdcndccndjcndjcnjdc"), "2dcjdncddnnc dc dncjdcndc");
        map.put(new String("ddjndjcndjjdcjndcjdcndccndjcndjcnjdc"), "2dcjdncddnnc dc dncjdcndc");
        map.put(new String("dsss"), "2dcjdncddnnc dc dncjdcndc");
        map.put(new String("sdsdsdsd"), "2dcjdncddnnc dc dncjdcndc");

        System.out.println(map);
        System.out.println(map.hashCode());

        System.out.println(new String("1").hashCode());
        System.out.println("1".hashCode());

    }
}
