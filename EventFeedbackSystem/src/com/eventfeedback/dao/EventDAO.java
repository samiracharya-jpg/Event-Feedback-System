package com.eventfeedback.dao;

import com.eventfeedback.db.DatabaseConnection;
import com.eventfeedback.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // CREATE
    public boolean addEvent(Event event) {
        String sql = "INSERT INTO events (title, description, event_date, location, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, event.getTitle());
            pst.setString(2, event.getDescription());
            pst.setString(3, event.getEventDate());
            pst.setString(4, event.getLocation());
            pst.setInt(5, event.getCreatedBy());
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Add event failed: " + e.getMessage());
            return false;
        }
    }

    // READ ALL
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setEventDate(rs.getString("event_date"));
                e.setLocation(rs.getString("location"));
                e.setCreatedBy(rs.getInt("created_by"));
                events.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Fetch events failed: " + e.getMessage());
        }
        return events;
    }

    // READ BY ID
    public Event getEventById(int id) {
        String sql = "SELECT * FROM events WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setEventDate(rs.getString("event_date"));
                e.setLocation(rs.getString("location"));
                e.setCreatedBy(rs.getInt("created_by"));
                return e;
            }

        } catch (SQLException e) {
            System.out.println("Fetch event failed: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET title=?, description=?, event_date=?, location=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, event.getTitle());
            pst.setString(2, event.getDescription());
            pst.setString(3, event.getEventDate());
            pst.setString(4, event.getLocation());
            pst.setInt(5, event.getId());
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Update event failed: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Delete event failed: " + e.getMessage());
            return false;
        }
    }
}