package com.okta.example.hookproject.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeBasicInfo {

    private String username;
    private String ssn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public EmployeeBasicInfo(String username, String ssn) {
        this.username = username;
        this.ssn = ssn;
    }

}
