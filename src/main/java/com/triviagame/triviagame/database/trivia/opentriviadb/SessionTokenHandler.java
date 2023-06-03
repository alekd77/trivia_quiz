package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triviagame.triviagame.AppLogger;

import java.io.IOException;

public class SessionTokenHandler {
    private String sessionToken;
    private boolean isSessionTokenValid;

    public SessionTokenHandler() throws IOException {
        this.isSessionTokenValid = false;
        this.sessionToken = retrieveSessionToken();

        if (!isSessionTokenValid) {
            throw new IOException("Failed to initialize session token");
        }
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public boolean isSessionTokenValid() {
        return isSessionTokenValid;
    }

    protected String retrieveSessionToken() throws IOException {
        String requestURL = "https://opentdb.com/api_token.php?command=request";

        try {
            return handleSessionTokenRequest(requestURL).getToken();

        } catch (IOException ex) {
            String errorMessage = String.format("Failed to retrieve session token due to: %s", ex.getMessage());
            throw new IOException(errorMessage);
        }
    }

    protected void resetSessionToken() throws IOException {
        try {
            if (sessionToken == null) {
                throw new IOException("Session token does not exist");
            }

            String requestURL = "https://opentdb.com/api_token.php?command=reset&token=" + sessionToken;
            handleSessionTokenRequest(requestURL);

        } catch (IOException ex) {
            String errorMessage = String.format("Failed to reset session token due to: %s", ex.getMessage());
            throw new IOException(errorMessage);
        }
    }

    private SessionTokenDTO handleSessionTokenRequest(String requestURL) throws IOException {
        try {
            String apiRequestResponse = APIRequestHandler.sendAPIRequest(requestURL);

            ObjectMapper mapper = new ObjectMapper();
            SessionTokenDTO sessionTokenDTO = mapper.readValue(
                    apiRequestResponse, SessionTokenDTO.class);

            validateRetrievedSessionToken(sessionTokenDTO);

            AppLogger.debug("Session token retrieved successfully");
            sessionTokenDTO.displayDebugInfo();
            isSessionTokenValid = true;
            return sessionTokenDTO;

        } catch (IOException ex) {
            String errorMessage = String.format("Failed to retrieve session token due to: %s", ex.getMessage());
            throw new IOException(errorMessage);
        }

    }

    private void validateRetrievedSessionToken(SessionTokenDTO sessionTokenDTO) throws IOException {
        if (sessionTokenDTO.getResponseCode() != 0) {
            throw new IOException(sessionTokenDTO.getResponseMessage());
        }

        if (sessionTokenDTO.getToken() == null) {
            throw new IOException("Unidentified error. Session token is null.");
        }
    }
}
