package data.repositories;

import data.entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.entity.UserRole;
import data.exceptions.DBException;
import org.apache.log4j.Logger;
import util.ConnectionManager;

public class UserRepository implements Repository<UUID, User> {
    private static final Logger logger = Logger.getLogger(UserRepository.class);
    public static final String CREATE_SQL = """
            INSERT INTO users(first_name, second_name, role, account_number, balance, login, password, uid) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, first_name, second_name, role, account_number, balance, login, password, uid
            FROM users
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, first_name, second_name, role, account_number, balance, login, password, uid
            FROM users
            WHERE id = ?
            """;

    public static final String FIND_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT id, first_name, second_name, role, account_number, balance, login, password, uid
            FROM users
            WHERE login = ? AND password = ?;
            """;

    public static final String FIND_BY_LOGIN_SQL = """
            SELECT id, first_name, second_name, role, account_number, balance, login, password, uid
            FROM users
            WHERE login = ?
            """;

    public static  final String UPDATE_SQL = """
            UPDATE users 
            SET first_name = ?, 
                second_name = ?, 
                role = ?, 
                account_number = ?, 
                balance = ?, 
                login = ?,
                password = ?,
            WHERE id = ?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    @Override
    public User create(User user) throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setObject(3, String.valueOf(user.getRole()));
            preparedStatement.setString(4, user.getAccountNumber());
            preparedStatement.setBigDecimal(5, user.getBalance());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setObject(8, user.getUid());
            preparedStatement.executeUpdate();

            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getObject(1, Long.class));
            }
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public User getById(UUID id) throws DBException {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public User getByLogin(String login) throws DBException {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_SQL)) {
            preparedStatement.setObject(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    public User getByLoginPassword(String login, String password) throws DBException {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setObject(1, login);
            preparedStatement.setObject(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildUserEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void update(User user) throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setObject(3, user.getRole());
            preparedStatement.setString(4, user.getAccountNumber());
            preparedStatement.setBigDecimal(5, user.getBalance());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.setObject(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public boolean delete(UUID id) throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DBException(e.getMessage());
        }
    }


    private User buildUserEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setRole(UserRole.find(resultSet.getString("role")).orElse(null));
        user.setAccountNumber(resultSet.getString("account_number"));
        user.setBalance(resultSet.getBigDecimal("balance"));
        user.setLogin(resultSet.getString("login"));
        return user;
    }
}
