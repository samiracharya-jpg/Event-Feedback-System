package com.eventfeedback.model;

public class Event {

    private int id;
    private String title;
    private String description;
    private String eventDate;
    private String location;
    private int createdBy;

    public Event() {}

    public Event(String title, String description, String eventDate, String location, int createdBy) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.createdBy = createdBy;
    }

    // Getters
    public int getId()             { return id; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public String getEventDate()   { return eventDate; }
    public String getLocation()    { return location; }
    public int getCreatedBy()      { return createdBy; }

    // Setters
    public void setId(int id)              { this.id = id; }
    public void setTitle(String t)         { this.title = t; }
    public void setDescription(String d)   { this.description = d; }
    public void setEventDate(String d)     { this.eventDate = d; }
    public void setLocation(String l)      { this.location = l; }
    public void setCreatedBy(int c)        { this.createdBy = c; }

    @Override
    public String toString() {
        return String.format("Event{id=%d, title='%s', date='%s', location='%s'}",
                id, title, eventDate, location);
    }
}