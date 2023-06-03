package com.triviagame.triviagame.database.trivia.opentriviadb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIRequestHandler {
    /**
     Sends an API request to the specified URL and returns the response as a string.
     @param requestURL the URL to send the API request to
     @return the response from the API as a string
     @throws IOException if an error occurs while sending the request or reading the response
     */
    protected static String sendAPIRequest(String requestURL) throws IOException {
        try {
            URL url = new URL(requestURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                return response.toString();

            } else {
                throw new IOException("Response Code: " + responseCode);
            }

        } catch (IOException ex) {
            String errorMessage = String.format("API request failed due to: %s", ex.getMessage());
            throw new IOException(errorMessage);
        }
    }
}
