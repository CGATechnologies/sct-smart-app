package app.sctp.core.net.api;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private int code;
    private String message;
    private Map<String, List<String>> fieldErrors;

    public ErrorResponse(int code) {
        this(code, "n/a");
    }

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.fieldErrors = new LinkedHashMap<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String field, String error){
        fieldErrors.computeIfAbsent(field, s -> new LinkedList<>()).add(error);
    }
}