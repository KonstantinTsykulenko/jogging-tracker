package com.tsykul.joggingtracker.model;

import java.util.List;

/**
 * @author KonstantinTsykulenko
 * @since 7/18/2014.
 */
public class ValidationErrors {
    private List<ValidationError> errors;

    public ValidationErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public static final class ValidationError {
        private String field;
        private String error;

        public ValidationError(String field, String error) {
            this.field = field;
            this.error = error;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
