package data.repositories;

import data.entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.entity.UserRole;
import util.ConnectionManager;

public class UserRepository implements Repository<UUID, User> {

    public static final String CREATE_SQL = """
            INSERT INTO users(id, first_name, second_name, role, account_number, balance, email) 
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, first_name, second_name, role, account_number, balance, email
            FROM users
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, first_name, second_name, role, account_number, balance, email
            FROM users
            WHERE id = ?
            """;

    public static  final String UPDATE_SQL = """
            UPDATE users 
            SET first_name = ?, 
                second_name = ?, 
                role = ?, 
                account_number = ?, 
                balance = ?, 
                email = ? 
            WHERE id = ?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    public User create(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.setObject(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setObject(4, user.getRole());
            preparedStatement.setString(5, user.getAccountNumber());
            preparedStatement.setBigDecimal(6, user.getBalance());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildUserEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildUserEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setObject(3, user.getRole());
            preparedStatement.setString(4, user.getAccountNumber());
            preparedStatement.setBigDecimal(5, user.getBalance());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setObject(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private User buildUserEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setRole(UserRole.find(resultSet.getString("user_role")).orElse(null));
        user.setAccountNumber(resultSet.getString("account_number"));
        user.setBalance(resultSet.getBigDecimal("balance"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}
