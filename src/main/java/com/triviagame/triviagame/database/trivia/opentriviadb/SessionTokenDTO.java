package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionTokenDTO {
    @JsonProperty("response_code")
    private int responseCode;

    @JsonProperty("response_message")
    private String responseMessage;

    @JsonProperty("token")
    private String token;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void displayDebugInfo() {
        System.out.print("\nSessionTokenDTO:\n");
        System.out.printf("Response code:\t%d\n", responseCode);
        System.out.printf("Response message:\t%s\n", responseMessage);
        System.out.printf("Token:\t%s\n\n", token);
    }
}
