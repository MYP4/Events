package repositories;

import entity.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import util.ConnectionManager;

public class EventRepository implements Repository<UUID, Event> {

    public static final String CREATE_SQL = """
            INSERT INTO events(id, name, description, price, address, type, rating, admin_id, uid) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, name, description, price, address, type, rating, admin_id, uid
            FROM events
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, name, description, price, address, type, rating, admin_id, uid
            FROM events
            WHERE uid = ?
            """;

    public static final String UPDATE_SQL = """
            UPDATE events 
            SET name = ?, 
                description = ?, 
                price = ?, 
                address = ?, 
                type = ?, 
                rating = ?, 
                admin_id = ? 
            WHERE uid = ?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM events
            WHERE uid = ?
            """;

    public Event create(Event event) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)) {
            preparedStatement.setLong(1, event.getId());
            preparedStatement.setString(2, event.getName());
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setBigDecimal(4, event.getPrice());
            preparedStatement.setString(5, event.getAddress());
            preparedStatement.setDouble(7, event.getRating());
            preparedStatement.setObject(8, event.getAdminId());
            preparedStatement.setObject(9, event.getUid());
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
    public List<Event> findAll() {
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
            preparedStatement.setBigDecimal(3, event.getPrice());
            preparedStatement.setString(4, event.getAddress());
            preparedStatement.setDouble(6, event.getRating());
            preparedStatement.setObject(7, event.getAdminId());
            preparedStatement.setObject(8, event.getUid());
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
        event.setId(resultSet.getInt("id"));
        event.setName(resultSet.getString("name"));
        event.setDescription(resultSet.getString("description"));
        event.setPrice(resultSet.getBigDecimal("price"));
        event.setAddress(resultSet.getString("address"));
        event.setRating(resultSet.getFloat("rating"));
        event.setAdminId(resultSet.getObject("admin_id", UUID.class));
        event.setUid(resultSet.getObject("uid", UUID.class));
        return event;
    }
}