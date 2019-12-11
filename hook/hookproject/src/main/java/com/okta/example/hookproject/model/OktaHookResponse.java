package com.okta.example.hookproject.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okta.example.hookproject.model.inlineHookResponseObjects.Commands;
import com.okta.example.hookproject.model.inlineHookResponseObjects.Error;

import java.util.List;

public class OktaHookResponse {

    private List<Commands> commands;
    private Error error;

    public List<Commands> getCommands() {
        return commands;
    }

    public void setCommands(List<Commands> commands) {
        this.commands = commands;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}



