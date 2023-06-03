package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triviagame.triviagame.AppConfig;
import com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception.*;
import com.triviagame.triviagame.model.GameSetupParams;
import com.triviagame.triviagame.model.TriviaList;

import java.io.IOException;
import java.io.InputStream;

public class TriviaListHandler {
    private static final String TRIVIA_LIST_API_BASE_URL = "https://opentdb.com/api.php";

    public TriviaListHandler() {}

    protected TriviaList retrieveTestTriviaList() throws IOException {
        String testTriviaFile = AppConfig.getInstance().getTestTriviaFile();
        TriviaListDTO triviaListDTO = parseTestTriviaListFile(testTriviaFile);

        triviaListDTO.displayDebugInfo();

        return new TriviaList(triviaListDTO.getResults());
    }

    protected TriviaList retrieveTriviaList(GameSetupParams gameSetupParams)
            throws IOException, OpenTriviaDBAPIException {
        String requestURL = prepareRequestURL(gameSetupParams);
        TriviaListDTO triviaListDTO = handleTriviaListRequest(requestURL);

        validateRetrievedTriviaList(triviaListDTO);

        return new TriviaList(triviaListDTO.getResults());
    }

    private String prepareRequestURL(GameSetupParams gameSetupParams) {
        StringBuilder apiUrlBuilder = new StringBuilder(TRIVIA_LIST_API_BASE_URL);

        apiUrlBuilder.append("?amount=").append(gameSetupParams.numberOfQuestions());

        if (gameSetupParams.categoryID() != 0) {
            apiUrlBuilder.append("&category=").append(gameSetupParams.categoryID());
        }

        if (!"random".equals(gameSetupParams.difficulty())) {
            apiUrlBuilder.append("&difficulty=").append(gameSetupParams.difficulty());
        }

        if (!"random".equals(gameSetupParams.triviaType())) {
            apiUrlBuilder.append("&type=").append(gameSetupParams.triviaType());
        }

//        if (gameSetupParams.categoryID() != OpenTriviaDBConstants.RANDOM_CATEGORY_ID) {
//            apiUrlBuilder.append("&category=").append(gameSetupParams.categoryID());
//        }
//
//        if (!gameSetupParams.difficulty().equalsIgnoreCase(
//                OpenTriviaDBConstants.DifficultyLevels.RANDOM.toString())) {
//            apiUrlBuilder.append("&difficulty=").append(gameSetupParams.difficulty());
//        }
//
//        if (!gameSetupParams.triviaType().equalsIgnoreCase(
//                OpenTriviaDBConstants.TriviaTypes.RANDOM.toString())) {
//            apiUrlBuilder.append("&type=").append(gameSetupParams.triviaType());
//        }

        return apiUrlBuilder.toString();
    }

    private TriviaListDTO handleTriviaListRequest(String requestURL) throws IOException {
        String response = APIRequestHandler.sendAPIRequest(requestURL);
        return parseTriviaListAPIResponse(response);
    }

    private TriviaListDTO parseTestTriviaListFile(String testFile) throws IOException {
        InputStream inputStream = TriviaListHandler.class.getResourceAsStream("/" + testFile);
        return new ObjectMapper().readValue(inputStream, TriviaListDTO.class);
    }

    private TriviaListDTO parseTriviaListAPIResponse(String response) throws IOException {
        return new ObjectMapper().readValue(response, TriviaListDTO.class);
    }

    private void validateRetrievedTriviaList(TriviaListDTO triviaListDTO) throws OpenTriviaDBAPIException {
        if (triviaListDTO == null || triviaListDTO.getResults() == null) {
            throw new OpenTriviaDBAPIException();
        }

        switch (triviaListDTO.getResponseCode()) {
            case 1 -> throw new OpenTriviaDBAPINoResultsException();
            case 2 -> throw new OpenTriviaDBAPIInvalidParameterException();
            case 3 -> throw new OpenTriviaDBAPITokenNotFoundException();
            case 4 -> throw new OpenTriviaDBAPITokenEmptyException();
        }
    }
}
