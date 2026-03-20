package com.eventfeedback.model;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    public User() {}

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters
    public int getId()           { return id; }
    public String getUsername()  { return username; }
    public String getPassword()  { return password; }
    public String getEmail()     { return email; }
    public String getRole()      { return role; }

    // Setters
    public void setId(int id)            { this.id = id; }
    public void setUsername(String u)    { this.username = u; }
    public void setPassword(String p)    { this.password = p; }
    public void setEmail(String e)       { this.email = e; }
    public void setRole(String r)        { this.role = r; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "', role='" + role + "'}";
    }
}