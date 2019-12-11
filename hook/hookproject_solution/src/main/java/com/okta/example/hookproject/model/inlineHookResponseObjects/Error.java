package com.okta.example.hookproject.model.inlineHookResponseObjects;

import java.util.List;

public class Error {

        private String errorSummary;
        private List<ErrorCauses> errorCauses;

        public String getErrorSummary() {
            return errorSummary;
        }

        public void setErrorSummary(String errorSummary) {
            this.errorSummary = errorSummary;
        }

    public List<ErrorCauses> getErrorCauses() {
        return errorCauses;
    }

    public void setErrorCauses(List<ErrorCauses> errorCauses) {
        this.errorCauses = errorCauses;
    }
}
