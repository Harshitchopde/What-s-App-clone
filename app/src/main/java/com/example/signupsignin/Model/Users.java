package com.example.signupsignin.Model;

public
class Users {
    String username,userEmail,profilePic,token;

    public
    Users(String username, String userEmail, String profilePic, String token) {
        this.username = username;
        this.userEmail = userEmail;
        this.profilePic = profilePic;
        this.token = token;
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

    public
    String getToken() {
        return token;
    }

    public
    void setToken(String token) {
        this.token = token;
    }

    public  Users(){}

}
