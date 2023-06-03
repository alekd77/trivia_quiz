package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TriviaListDTO {
    @JsonProperty("response_code")
    private int responseCode;

    @JsonProperty("results")
    private List<TriviaDTO> results;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<TriviaDTO> getResults() {
        return results;
    }

    public void setResults(List<TriviaDTO> results) {
        this.results = results;
    }

    public void displayDebugInfo() {
        System.out.print("\nTriviaListDTO:\n");
        System.out.printf("Response code:\t%d\n", responseCode);
        System.out.printf("Results count:\t%s\n\n", results.size());
    }
}
