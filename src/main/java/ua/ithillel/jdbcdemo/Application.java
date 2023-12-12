package ua.ithillel.jdbcdemo;

import ua.ithillel.jdbcdemo.dao.UserDao;
import ua.ithillel.jdbcdemo.dao.UserMysqlDao;
import ua.ithillel.jdbcdemo.exception.UserAppException;
import ua.ithillel.jdbcdemo.model.User;

import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting program...");

        // DAO - data access objects

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            final String mysqlUrl = System.getenv("MYSQL_URL");
            final String mysqlUser = System.getenv("MYSQL_USER");
            final String mysqlPassword = System.getenv("MYSQL_PASSWORD");

            try (final Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword)) {

                UserDao userDao = new UserMysqlDao(connection);

                final User byId = userDao.findById(1);
                final User byId1 = userDao.findById(4);
                final List<User> all = userDao.findAll();
                final User byEmail = userDao.findByEmail("dmytroivanov@example.com");

                final User testUser = User.builder()
                        .name("Test2")
                        .email("test1@tes.com")
                        .birthDate(Date.valueOf("1990-01-01")).build();

                final User saved = userDao.save(testUser);

                System.out.println();

//                final Statement statement = connection.createStatement();
//                final boolean dataRead = statement.execute("SELECT * FROM t_user");
//
//                if (dataRead) {
//                    final ResultSet resultSet = statement.getResultSet();
//                    while (resultSet.next()) {
//                        // id, name, email, birth_date
//                        final int id = resultSet.getInt("id");
//                        final String name = resultSet.getString("name");
//                        final String email = resultSet.getString("email");
//                        final Date birthDat = resultSet.getDate("birth_date");
//
//                        System.out.printf("{id: %d; name: %s, email: %s, born:%s%n", id, name, email, birthDat.toString());
//                    }
//                }
//
//
//                System.out.println();
            } catch (UserAppException e) {
                throw new RuntimeException(e);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver is not present on classpath. Consider adding dependency");
        }
    }
}
