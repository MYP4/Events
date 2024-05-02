package data.repositories;

import data.entity.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import util.ConnectionManager;

public class TicketRepository {

    public static final String CREATE_SQL = """
            INSERT INTO tickets(user_id, specific_id, status, feedback, rating, uid) 
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, user_id, specific_id, status, feedback, rating, uid
            FROM tickets
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, user_id, specific_id, status, feedback, rating, uid
            FROM tickets
            WHERE uid = ?
            """;

    public static final String UPDATE_SQL = """
            UPDATE tickets 
            SET user_id = ?, 
                specific_id = ?, 
                status = ?, 
                feedback = ?, 
                rating = ? 
            WHERE uid = ?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM tickets
            WHERE uid = ?
            """;

    public Ticket create(Ticket ticket) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.setObject(1, ticket.getUserId());
            preparedStatement.setObject(2, ticket.getSpecificId());
            preparedStatement.setInt(3, ticket.getStatus());
            preparedStatement.setObject(4, ticket.getUid());
            preparedStatement.executeUpdate();
            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ticket getById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildTicketEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> getAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildTicketEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Ticket ticket) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, ticket.getUserId());
            preparedStatement.setObject(2, ticket.getSpecificId());
            preparedStatement.setInt(3, ticket.getStatus());
            preparedStatement.setObject(4, ticket.getUid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    private Ticket buildTicketEntity(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getInt("id"));
        ticket.setUserId((UUID) resultSet.getObject("user_id"));
        ticket.setSpecificId((UUID)resultSet.getObject("specific_id"));
        ticket.setStatus(resultSet.getInt("status"));
        ticket.setUid(resultSet.getObject("uid", UUID.class));
        return ticket;
    }
}
