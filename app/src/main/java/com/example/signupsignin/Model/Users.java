package com.example.signupsignin.Model;

public
class Users {
    String username,userEmail,profilePic;

    public  Users(){}

    public
    Users(String username, String userEmail, String profilePic) {
        this.username = username;
        this.userEmail = userEmail;
        this.profilePic = profilePic;
    }

    public
    String getUsername() {
        return username;
    }

    public
    void setUsername(String username) {
        this.username = username;
    }

    public
    String getUserEmail() {
        return userEmail;
    }

    public
    void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public
    String getProfilePic() {
        return profilePic;
    }

    public
    void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
