package com.example.laba3;

public class User {
    private static int currentUserID = 0;
    private String password;
    private String email;
    public static void setCurrentUserID(int userID) {
        currentUserID = userID;
    }

    public static int getCurrentUserID() {
        return currentUserID;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
