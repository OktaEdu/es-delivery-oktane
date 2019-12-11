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
        JSONObject eventBody = RequestConverter.httpToJSON(request);
        JSONObject userProfile = eventBody.getJSONObject("data").getJSONObject("userProfile");
        String username = userProfile.getString("login");

        //Check if the input data is valid
        if (!userProfile.isNull("ssn")) {
            String ssn = userProfile.getString("ssn");
            EmployeeBasicInfo newEmployeeFromOkta = new EmployeeBasicInfo(username, ssn);

            //Call the 3rd party DB to get employee ssn based on the username.
            String employeeInfo = getEmployees(newEmployeeFromOkta.getUsername());
            EmployeeBasicInfo employeeFromDB = EmployeeConverter.parseEmployeeInfo(employeeInfo);

            //Compare the employee payloads
            //if the ssn is the same, allow the registration
            if (newEmployeeFromOkta.getSsn().equals(employeeFromDB.getSsn())) {
                HashMap<String, String> value = new HashMap<>();
                value.put("ssn", "");
                command1.setValue(value);
                command1.setType("com.okta.user.profile.update");
                commandsList.add(command1);
                response.setCommands(commandsList);

            //If SSN doesn't match, deny the registration with error messgae SSN is not matching
            } else {
                HashMap<String, String> value = new HashMap<>();
                value.put("registration", "DENY");
                command1.setValue(value);
                command1.setType("com.okta.action.update");
                commandsList.add(command1);

                errorCauses.setErrorSummary("SSN doesn't match. Please try again.");
                errorCauses.setReason("INVALID_PAYLOAD");
                error.setErrorSummary("Invalid request payload");
                causesList.add(errorCauses);
                error.setErrorCauses(causesList);

                response.setCommands(commandsList);
                response.setError(error);
            }

            // Compose the response body to okta with Deny action and error message that both username and SSN are required
        } else {
            HashMap<String, String> value = new HashMap<>();
            value.put("registration", "DENY");
            command1.setValue(value);
            command1.setType("com.okta.action.update");
            commandsList.add(command1);

            errorCauses.setErrorSummary("The request payload was not in the expected format. SSN is required.");
            errorCauses.setReason("INVALID_PAYLOAD");
            error.setErrorSummary("Invalid request payload");
            causesList.add(errorCauses);
            error.setErrorCauses(causesList);

            response.setCommands(commandsList);
            response.setError(error);
        }

        System.out.println(response.toString());

        return response;

    }

    private String getEmployees(String username) {
        final String uri = "http://localhost:8082/employees/search/findByUsername?username=" + username;
        RestTemplate restTemplate = new RestTemplate();

        String employee = restTemplate.getForObject(uri, String.class);

        return employee;

    }

}

