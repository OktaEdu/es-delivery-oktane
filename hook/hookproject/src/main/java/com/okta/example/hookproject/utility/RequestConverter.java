package com.okta.example.hookproject.utility;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class RequestConverter {

    public static JSONObject httpToJSON(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = request.getReader();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        String line = "";
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.append(line);
        }
        String requestData = buffer.toString();

        JSONObject jsonBody = new JSONObject(requestData);
        return jsonBody;
    }
}
