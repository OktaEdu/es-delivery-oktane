package com.okta.example.hookproject.utility;

import com.okta.example.hookproject.model.EmployeeBasicInfo;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmployeeConverter {

    public static EmployeeBasicInfo parseEmployeeInfo (String requestbody){

        JSONObject jsonBody = new JSONObject(requestbody);
        JSONObject embedded = jsonBody.getJSONObject("_embedded");
        JSONArray employees = embedded.getJSONArray("employees");
        String username = employees.getJSONObject(0).optString("username");
        String ssn=employees.getJSONObject(0).optString("ssn");
        System.out.println("The payload is : "+username+ " and "+ssn);

        EmployeeBasicInfo employeeInfo = new EmployeeBasicInfo(username, ssn);
        return employeeInfo;
    }

}
