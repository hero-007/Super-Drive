package com.udacity.jwdnd.course1.cloudstorage.models;

public class DisplayCredentials {

    private String displayPassword;
    private Credentials credentials;

    public DisplayCredentials(String displayPassword, Credentials credentials) {
        this.displayPassword = displayPassword;
        this.credentials = credentials;
    }

    public String getDisplayPassword() {
        return displayPassword;
    }

    public void setDisplayPassword(String displayPassword) {
        this.displayPassword = displayPassword;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
