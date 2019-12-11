package com.okta.example.hookproject.controller;

import com.okta.example.hookproject.model.OktaEvents;
import com.okta.example.hookproject.model.VerificationResponse;
import com.okta.example.hookproject.utility.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;


@RestController
public class EventHookController {

   //Display events that begin with "user.account"
    @PostMapping("/event/user-account")
    public OktaEvents accountEvents(HttpServletRequest request) {

        JSONObject eventBody = RequestConverter.httpToJSON(request);
        JSONObject data = eventBody.getJSONObject("data");
        JSONArray events = (JSONArray) data.get("events");

        String eventType = events.getJSONObject(0).optString("eventType");
        String displayMessage=events.getJSONObject(0).optString("displayMessage");
        String eventTime = (String)eventBody.get("eventTime");

        OktaEvents oktaEvents = new OktaEvents(eventType,displayMessage,eventTime);

        System.out.println(oktaEvents.toString());

        return oktaEvents;

    }

    //Verify endpoint ownership
    @GetMapping("/event/*")
    public VerificationResponse endpointVerify(HttpServletRequest request) {

        final String headerName = "x-okta-verification-challenge";
        String verification = request.getHeader(headerName);
        VerificationResponse response = new VerificationResponse();
        response.setVerification(verification);

        System.out.println(verification);

        return response;

    }

}
