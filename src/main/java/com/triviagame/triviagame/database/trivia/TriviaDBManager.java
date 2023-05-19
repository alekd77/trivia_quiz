package com.triviagame.triviagame.database.trivia;

import com.triviagame.triviagame.model.GameSetupParams;
import com.triviagame.triviagame.model.TriviaList;

public interface TriviaDBManager {
    TriviaList createNewTriviaList(GameSetupParams gameSetupParams) throws TriviaDBException;
}
