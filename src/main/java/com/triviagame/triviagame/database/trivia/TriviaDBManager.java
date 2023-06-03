package com.triviagame.triviagame.database.trivia;

import com.triviagame.triviagame.database.trivia.exception.TriviaDBException;
import com.triviagame.triviagame.model.GameSetupParams;
import com.triviagame.triviagame.model.TriviaList;

import java.io.IOException;

public interface TriviaDBManager {
    TriviaList createNewTriviaList(GameSetupParams gameSetupParams) throws IOException;
}
