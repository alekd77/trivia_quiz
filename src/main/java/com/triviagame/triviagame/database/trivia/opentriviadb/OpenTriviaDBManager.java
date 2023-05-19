package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.triviagame.triviagame.AppConfig;
import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.database.trivia.TriviaDBException;
import com.triviagame.triviagame.database.trivia.TriviaDBManager;
import com.triviagame.triviagame.database.trivia.opentriviadb.exception.*;
import com.triviagame.triviagame.model.GameSetupParams;
import com.triviagame.triviagame.model.TriviaList;

public class OpenTriviaDBManager implements TriviaDBManager {
    @Override
    public TriviaList createNewTriviaList(GameSetupParams gameSetupParams) throws TriviaDBException {
        TriviaList triviaList = null;

        try {
            if (AppConfig.getInstance().isDebugModeEnabled()) {
                triviaList = createOpenTriviaDBTestTriviaList();

            } else {
                triviaList = createOpenTriviaDBTriviaList();
            }

        } catch (OpenTriviaDBAPINoResultsException | OpenTriviaDBAPIInvalidParameterException ex) {
            AppLogger.error(ex.getMessage());
            throw new TriviaDBException(ex.getMessage());

        } catch (OpenTriviaDBAPITokenNotFoundException ex) {
            AppLogger.error(ex.getMessage());

        } catch (OpenTriviaDBAPITokenEmptyException ex) {
            AppLogger.error(ex.getMessage());

        }

        if (triviaList == null) {
            throw new TriviaDBException("Creating new trivia list failed for an unknown reason");
        }

        String loggerMessage = String.format(
                "Creating new trivia list successful [Trivia count: %d]",
                triviaList.getInitialNumberOfTrivia());
        AppLogger.info(loggerMessage);

        return triviaList;
    }

    private TriviaList createOpenTriviaDBTestTriviaList() throws OpenTriviaDBAPIException {
        return OpenTriviaDBAPIJSONFileParser.parseTriviaListFromJSONFile(
                AppConfig.getInstance().getTestTriviaFile());
    }

    private TriviaList createOpenTriviaDBTriviaList() throws OpenTriviaDBAPIException {
        return null;
    }

    private String prepareOpenTriviaDBAPIURL() {
        return null;
    }
}
