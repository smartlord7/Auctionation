package Helpers.config;

public class ErrorResponse {
    public String errorMessage;
    public int error;

    public ErrorResponse() {
    }

    public ErrorResponse(int error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return "Error code:" + error + "\nMessage: " + errorMessage;
    }
}
