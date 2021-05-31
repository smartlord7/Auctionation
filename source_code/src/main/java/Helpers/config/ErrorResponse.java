package Helpers.config;

public class ErrorResponse {
    public String errorMessage;
    public String error;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return "Error code:" + error + "\nMessage: " + errorMessage;
    }
}
