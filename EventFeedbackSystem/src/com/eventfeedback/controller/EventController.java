package com.eventfeedback.controller;

import com.eventfeedback.dao.EventDAO;
import com.eventfeedback.model.Event;

import java.util.List;

public class EventController {

    private final EventDAO eventDAO;

    public EventController() {
        this.eventDAO = new EventDAO();
    }

    // ---- ADD EVENT ----
    public boolean addEvent(String title, String description, String eventDate, String location, int createdBy) {

        // Validate title
        if (title == null || title.trim().isEmpty()) {
            System.out.println("[ERROR] Title cannot be empty.");
            return false;
        }

        // Validate date format
        if (eventDate == null || !eventDate.trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("[ERROR] Date format must be YYYY-MM-DD.");
            return false;
        }

        // Validate location
        if (location == null || location.trim().isEmpty()) {
            System.out.println("[ERROR] Location cannot be empty.");
            return false;
        }

        // Create and save event
        Event event = new Event(
                title.trim(),
                description.trim(),
                eventDate.trim(),
                location.trim(),
                createdBy
        );

        boolean success = eventDAO.addEvent(event);
        if (success) {
            System.out.println("[SUCCESS] Event added successfully.");
        } else {
            System.out.println("[ERROR] Failed to add event.");
        }
        return success;
    }

    // ---- GET ALL EVENTS ----
    public List<Event> getAllEvents() {
        List<Event> events = eventDAO.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events found.");
        }
        return events;
    }

    // ---- GET EVENT BY ID ----
    public Event getEventById(int id) {
        Event event = eventDAO.getEventById(id);
        if (event == null) {
            System.out.println("[ERROR] Event with ID " + id + " not found.");
        }
        return event;
    }

    // ---- UPDATE EVENT ----
    public boolean updateEvent(int id, String title, String description, String eventDate, String location) {

        // Check event exists
        Event event = eventDAO.getEventById(id);
        if (event == null) {
            System.out.println("[ERROR] Event not found.");
            return false;
        }

        // Only update fields that are not empty (keep old value if blank)
        if (title != null && !title.trim().isEmpty()) {
            event.setTitle(title.trim());
        }
        if (description != null && !description.trim().isEmpty()) {
            event.setDescription(description.trim());
        }
        if (eventDate != null && !eventDate.trim().isEmpty()) {
            if (!eventDate.trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("[ERROR] Date format must be YYYY-MM-DD.");
                return false;
            }
            event.setEventDate(eventDate.trim());
        }
        if (location != null && !location.trim().isEmpty()) {
            event.setLocation(location.trim());
        }

        boolean success = eventDAO.updateEvent(event);
        if (success) {
            System.out.println("[SUCCESS] Event updated successfully.");
        } else {
            System.out.println("[ERROR] Failed to update event.");
        }
        return success;
    }

    // ---- DELETE EVENT ----
    public boolean deleteEvent(int id) {

        // Check event exists
        Event event = eventDAO.getEventById(id);
        if (event == null) {
            System.out.println("[ERROR] Event not found.");
            return false;
        }

        boolean success = eventDAO.deleteEvent(id);
        if (success) {
            System.out.println("[SUCCESS] Event deleted successfully.");
        } else {
            System.out.println("[ERROR] Failed to delete event.");
        }
        return success;
    }
}