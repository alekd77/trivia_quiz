package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.triviagame.triviagame.model.Trivia;

import java.util.List;

public class JSONResponseFromOpenTriviaDB {
    @JsonProperty("response_code")
    private int responseCode;

    @JsonProperty("results")
    private List<Trivia> results;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<Trivia> getResults() {
        return results;
    }

    public void setResults(List<Trivia> results) {
        this.results = results;
    }

    public static String getDescriptionForResponseCode(int responseCode) {
        return switch(responseCode) {
            case 0 -> "Success\tReturned results successfully";
            case 1 -> "No Results\tCould not return results. The API doesn't have enough questions for your query. (Ex. Asking for 50 Questions in a Category that only has 20.)";
            case 2 -> "Invalid Parameter\tContains an invalid parameter. Arguments passed in aren't valid. (Ex. Amount = Five)";
            case 3 -> "Token Not Found\tSession Token does not exist.";
            case 4 -> "Token Empty\tSession Token has returned all possible questions for the specified query. Resetting the Token is necessary.";
            default -> String.format("Unknown Response Code:\t%d", responseCode);
        };
    }

}
