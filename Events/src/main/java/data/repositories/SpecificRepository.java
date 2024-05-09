package data.repositories;

import data.entity.Specific;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.exceptions.DBException;
import org.apache.log4j.Logger;
import util.ConnectionManager;

public class SpecificRepository {
    private static final Logger logger = Logger.getLogger(SpecificRepository.class);
    public static final String CREATE_SQL = """
            INSERT INTO specifics(event_id, description, ticket_count, price, address, uid) 
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, event_id, description, ticket_count, price, address, uid
            FROM specifics
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, event_id, description, ticket_count, price, address, uid
            FROM specifics
            WHERE uid = ?
            """;

    public static final String UPDATE_SQL = """
            UPDATE specifics 
            SET event_id = ?, 
                description = ?, 
                ticket_count = ?, 
                price = ?, 
                address = ? 
            WHERE uid = ?
            """;

    public static final String DELETE_SQL = """
            DELETE FROM specifics
            WHERE uid = ?
            """;

    public Specific create(Specific specific) throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {

            preparedStatement.setObject(1, specific.getEventId());
            preparedStatement.setString(2, specific.getDescription());
            preparedStatement.setInt(3, specific.getTicketCount());
            preparedStatement.setObject(4, specific.getPrice());
            preparedStatement.setString(5, specific.getAddress());
            preparedStatement.setObject(6, specific.getUid());
            preparedStatement.executeUpdate();

            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                specific.setId(generatedKeys.getObject(1, Long.class));
            }

            return specific;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public Specific getById(UUID id) throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildSpecificEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Specific> getAll() throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Specific> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildSpecificEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(Specific specific) throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, specific.getEventId());
            preparedStatement.setString(2, specific.getDescription());
            preparedStatement.setInt(3, specific.getTicketCount());
            preparedStatement.setObject(4, specific.getPrice());
            preparedStatement.setString(5, specific.getAddress());
            preparedStatement.setObject(6, specific.getUid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean delete(UUID id) throws DBException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Specific buildSpecificEntity(ResultSet resultSet) throws SQLException {
        Specific specific = new Specific();
        specific.setId(resultSet.getLong("id"));
        specific.setEventId((UUID) resultSet.getObject("event_id"));
        specific.setDescription(resultSet.getString("description"));
        specific.setTicketCount(resultSet.getInt("ticket_count"));
        specific.setPrice((BigDecimal)resultSet.getObject("price"));
        specific.setAddress(resultSet.getString("address"));
        specific.setUid(resultSet.getObject("uid", UUID.class));
        return specific;
    }
}