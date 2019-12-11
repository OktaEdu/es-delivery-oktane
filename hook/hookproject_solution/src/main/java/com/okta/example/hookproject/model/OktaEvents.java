package com.okta.example.hookproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OktaEvents {

    private String eventType;
    private String displayMessage;
    private String eventTime;

    public OktaEvents(String eventType, String displayMessage, String eventTime) {
        this.eventType = eventType;
        this.displayMessage = displayMessage;
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.eventTime= dateFormat.format(eventTime);
    }

    public String toString(){
         String eventDetail = "On "+eventTime +" , a "+ eventType + " event happened to your Org with the following description: "+ displayMessage;
         return eventDetail;
    }
}
