package data.repositories;

import data.entity.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import util.ConnectionManager;

public class EventRepository implements Repository<UUID, Event> {

    public static final String CREATE_SQL = """
            INSERT INTO events(id, name, description, admin_id, uid) 
            VALUES (?,?,?,?,?);
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, name, description, admin_id, uid
            FROM events
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, name, description, admin_id, uid
            FROM events
            WHERE uid =?
            """;

    public static final String UPDATE_SQL = """
            UPDATE events 
            SET name =?, 
                description =?, 
                admin_id =? 
            WHERE uid =?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM events
            WHERE uid =?
            """;

    public Event create(Event event) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.setLong(1, event.getId());
            preparedStatement.setString(2, event.getName());
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setObject(4, event.getAdminId());
            preparedStatement.setObject(5, event.getUid());
            preparedStatement.executeUpdate();
            return event;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event getById(UUID id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildEventEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> getAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Event> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildEventEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Event event) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setObject(3, event.getAdminId());
            preparedStatement.setObject(4, event.getUid());
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

    private Event buildEventEntity(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong("id"));
        event.setName(resultSet.getString("name"));
        event.setDescription(resultSet.getString("description"));
        event.setAdminId(UUID.fromString(resultSet.getString("admin_id")));
        event.setUid(UUID.fromString(resultSet.getString("uid")));
        return event;
    }
}