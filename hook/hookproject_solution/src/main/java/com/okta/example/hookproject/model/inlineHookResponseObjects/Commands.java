package com.okta.example.hookproject.model.inlineHookResponseObjects;

import java.util.HashMap;

public class Commands {
    String type;

    public HashMap<String, String> value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap getValue() {
        return value;
    }

    public void setValue(HashMap value) {
        this.value = value;
    }

}
