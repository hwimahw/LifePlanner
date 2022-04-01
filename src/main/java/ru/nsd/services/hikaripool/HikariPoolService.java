package ru.nsd.services.hikaripool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariPoolService {

    private static DataSource dataSource;

//    public static void main(String[] args) {
//        String query = "SELECT COUNT(*) FROM employee";
//        //Using try-with-resources for auto closing connection, pstmt, and rs.
//        try (Connection connection = getConnection();
//             PreparedStatement pstmt = connection.prepareStatement(query);
//             ResultSet rs = pstmt.executeQuery();
//        ) {
//            if (rs.next()) {
//                System.out.println("Total employees are " + rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private static DataSource getDataSource() {
        if (dataSource == null) {
            createDataSource();
        }
        return dataSource;
    }

    private static void createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setJdbcUrl( "jdbc:h2:~/test1;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE" );
        hikariConfig.setUsername( "sa" );
        hikariConfig.setPassword( "" );
        hikariConfig.addDataSourceProperty( "cachePrepStmts" , "true" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        dataSource = new HikariDataSource(hikariConfig);
    }
}
