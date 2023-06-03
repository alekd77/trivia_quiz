package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.triviagame.triviagame.AppConfig;
import com.triviagame.triviagame.database.trivia.TriviaDBManager;
import com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception.*;
import com.triviagame.triviagame.model.GameSetupParams;
import com.triviagame.triviagame.model.TriviaList;

import java.io.IOException;


public class OpenTriviaDBManager implements TriviaDBManager {
    private final SessionTokenHandler sessionTokenHandler;
    private final TriviaListHandler triviaListHandler;

    public OpenTriviaDBManager() throws IOException {
        this.sessionTokenHandler = new SessionTokenHandler();
        this.triviaListHandler = new TriviaListHandler();
    }


    @Override
    public TriviaList createNewTriviaList(GameSetupParams gameSetupParams) throws IOException {
        try {
            if (AppConfig.getInstance().isDebugModeEnabled()) {
                return triviaListHandler.retrieveTestTriviaList();
            }

            return triviaListHandler.retrieveTriviaList(gameSetupParams);

        } catch (OpenTriviaDBAPINoResultsException ex) {
            throw new IOException("The API doesn't have enough questions for your query." +
                    " (Ex. Asking for 50 Questions in a Category that only has 20.)");
        } catch (OpenTriviaDBAPIInvalidParameterException ex) {
            throw new IOException("Contains an invalid parameter." +
                    " Arguments passed in aren't valid. (Ex. Amount = Five)");
        } catch (OpenTriviaDBAPITokenNotFoundException ex) {
            throw new IOException("Session Token does not exist.");
        } catch (OpenTriviaDBAPITokenEmptyException ex) {
            throw new IOException("Session Token has returned all possible questions for the specified query." +
                    " Resetting the Token is necessary.");
        } catch (OpenTriviaDBAPIException ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
