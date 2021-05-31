package Helpers.config;

public class ErrorResponse {
    public String errorMessage;
    public String errorCode;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return "Error code:" + errorCode + "\nMessage: " + errorMessage;
    }
}
