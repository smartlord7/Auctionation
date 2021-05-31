package Helpers.config;

public class ErrorResponse {
    public String error, errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }
}
