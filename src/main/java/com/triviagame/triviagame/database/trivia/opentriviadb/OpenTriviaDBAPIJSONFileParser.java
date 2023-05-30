package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triviagame.triviagame.AppConfig;
import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception.*;
import com.triviagame.triviagame.model.TriviaList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OpenTriviaDBAPIJSONFileParser {
    public static TriviaList parseTriviaListFromJSONFile(String jsonFile) throws OpenTriviaDBAPIException {
        JSONResponseFromOpenTriviaDB jsonResponseFromOpenTriviaDB = parseJSONResponseFromTriviaDB(jsonFile);

        handleJSONResponseCode(jsonResponseFromOpenTriviaDB.getResponseCode());

        return new TriviaList(jsonResponseFromOpenTriviaDB.getResults());
    }

    private static JSONResponseFromOpenTriviaDB parseJSONResponseFromTriviaDB(String jsonFileName) {
        try {
            InputStream inputStream = OpenTriviaDBAPIJSONFileParser.class.getResourceAsStream("/" + jsonFileName);
            ObjectMapper objectMapper = new ObjectMapper();
            JSONResponseFromOpenTriviaDB jsonResponse = objectMapper.readValue(
                    inputStream, JSONResponseFromOpenTriviaDB.class);

            String loggerMessage = String.format("Parsing %s file successful", jsonFileName);
            AppLogger.debug(loggerMessage);

            return jsonResponse;

        } catch (IOException e) {
            String loggerMessage = String.format(
                    "Parsing %s file failed\n\tDescription:\t%s", jsonFileName, e.getMessage());
            AppLogger.error(loggerMessage);

            throw new RuntimeException(e.getMessage());
        }
    }

    private static void handleJSONResponseCode(int responseCode) throws OpenTriviaDBAPIException {
        String responseCodeDescription = JSONResponseFromOpenTriviaDB.getDescriptionForResponseCode(responseCode);

        switch (responseCode) {
            case 0 -> {
                String loggerMessage = String.format("Parsing trivia list: %s", responseCodeDescription);
                AppLogger.info(loggerMessage);
            }
            case 1 -> throw new OpenTriviaDBAPINoResultsException();
            case 2 -> throw new OpenTriviaDBAPIInvalidParameterException();
            case 3 -> throw new OpenTriviaDBAPITokenNotFoundException();
            case 4 -> throw new OpenTriviaDBAPITokenEmptyException();
            default -> throw new OpenTriviaDBAPIException();
        }
    }
}
