package com.triviagame.triviagame.database.trivia.opentriviadb;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OpenTriviaDBConstants {
    public static final int MIN_NUMBER_OF_QUESTIONS = 1;
    public static final int MAX_NUMBER_OF_QUESTIONS = 50;
    public static final int RANDOM_CATEGORY_ID = 0;
    public static final int MIN_CATEGORY_ID = 9;
    public static final int MAX_CATEGORY_ID = 32;

    public static final List<String> DIFFICULTY_LEVELS = Collections.unmodifiableList(
            Arrays.asList("random", "easy", "medium", "hard"));

    public static final List<String> TRIVIA_TYPES = Collections.unmodifiableList(
            Arrays.asList("random", "multiple", "boolean"));

    public static final List<String> ENCODING_TYPES = Collections.unmodifiableList(
            Arrays.asList("default", "url3986", "base64", "hard"));
}
