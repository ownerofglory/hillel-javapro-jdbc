package ua.ithillel.jdbcdemo.dao;

import lombok.RequiredArgsConstructor;
import ua.ithillel.jdbcdemo.exception.UserAppException;
import ua.ithillel.jdbcdemo.model.HotelBooking;
import ua.ithillel.jdbcdemo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@RequiredArgsConstructor
public class UserMysqlDao implements UserDao {
    private final Connection connection;

    @Override
    public User findById(Integer id) throws UserAppException {
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT u.*, b.* FROM t_user AS u " +
                    "JOIN t_hotel_booking AS b " +
                    "ON u.id = b.user_id " +
                    "WHERE u.id = ?");
            statement.setInt(1, id);

            final boolean dataRead = statement.execute();

            User user = null;
            if (dataRead) {
                final ResultSet resultSet = statement.getResultSet();

                while(resultSet.next()) {
                    if (user == null) {
                        final String name = resultSet.getString("u.name");
                        final String email = resultSet.getString("u.email");
                        final Date birthDat = resultSet.getDate("u.birth_date");

                        user = User.builder().id(id)
                                .birthDate(birthDat)
                                .name(name)
                                .bookings(new ArrayList<>())
                                .email(email).build();
                    }

                    final Integer bookingId = resultSet.getInt("b.id");
                    final Date checkinDate = resultSet.getDate("b.checkin_date");
                    final Date checkoutDate = resultSet.getDate("b.checkout_date");


                    user.getBookings().add(HotelBooking.builder()
                            .checkOutDate(checkoutDate)
                            .checkInDate(checkinDate)
                            .id(bookingId)
                            .user(user)
                            .build());
                }

                return user;

            }
        } catch (SQLException e) {
            throw new UserAppException("undable to read user by id" + e.getMessage());
        }

        return null;
    }

    @Override
    public User findByEmail(String email) throws UserAppException {
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT u.* FROM t_user AS u  " +
                    "WHERE u.email = ?");
            statement.setString(1, email);

            final boolean dataRead = statement.execute();

            User user = null;
            if (dataRead) {

                final ResultSet resultSet = statement.getResultSet();

                if (resultSet.next()) {
                    final Integer id = resultSet.getInt("u.id");
                    final String name = resultSet.getString("u.name");
                    final Date birthDat = resultSet.getDate("u.birth_date");

                    return User.builder()
                            .id(id)
                            .birthDate(birthDat)
                            .name(name)
                            .bookings(new ArrayList<>())
                            .email(email).build();
                }

            }
        } catch (SQLException e) {
            throw new UserAppException("undable to read user by id" + e.getMessage());
        }

        return null;
    }

    @Override
    public List<User> findAll() throws UserAppException {
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_user");

            final boolean dataRead = statement.execute();

            if (dataRead) {
                final ResultSet resultSet = statement.getResultSet();

                List<User> result = new ArrayList<>();
                while(resultSet.next()) {

                    final Integer id = resultSet.getInt("id");
                    final String name = resultSet.getString("name");
                    final String email = resultSet.getString("email");
                    final Date birthDat = resultSet.getDate("birth_date");

                     result.add(User.builder().id(id)
                            .birthDate(birthDat)
                            .name(name)
                            .email(email).build());
                }
                return result;
            }
        } catch (SQLException e) {
            throw new UserAppException("undable to read user by id" + e.getMessage());
        }


        return null;
    }

    @Override
    public User save(User user) throws UserAppException {
        try {

            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_user (name, email, birth_date) " +
                    "VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setDate(3, user.getBirthDate());

            final int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next())  {
                    final int generatedId = generatedKeys.getInt(1);

                    user.setId(generatedId);

                    return user;
                }


            }




        } catch (SQLException e) {
            throw new UserAppException("undable to read user by id" + e.getMessage());
        }
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }
}
