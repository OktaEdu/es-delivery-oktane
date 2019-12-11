package com.okta.example.hookproject.controller;

import com.okta.example.hookproject.model.EmployeeBasicInfo;
import com.okta.example.hookproject.model.OktaHookResponse;
import com.okta.example.hookproject.model.inlineHookResponseObjects.*;
import com.okta.example.hookproject.model.inlineHookResponseObjects.Error;
import com.okta.example.hookproject.utility.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class RegistrationInlineHookController {

    //Look up database before user can register
    @PostMapping("/registration/dblookup")
    public OktaHookResponse accountEvents(HttpServletRequest request) {

        //Setup the OktaHookResponse structure
        Commands command1 = new Commands();
        List<Commands> commandsList = new ArrayList<>();
        Error error = new Error();
        ErrorCauses errorCauses = new ErrorCauses();
        List<ErrorCauses> causesList = new ArrayList<>();
        OktaHookResponse response = new OktaHookResponse();

        //Parse username and SSN from request payload

        //Check if the input data is valid

            //Call the 3rd party DB to get employee ssn based on the username.

            //Compare the employee payloads
            //if the ssn is the same, allow the registration

            //If SSN doesn't match, deny the registration with error messgae SSN is not matching

            //Compose the response body to okta with Deny action and error message that SSN is required

        System.out.println(response.toString());

        return response;

    }

    private String getEmployees(String username) {

        String employee = "";

        return employee;

    }

}

