package com.triviagame.triviagame.database.trivia.opentriviadb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class OpenTriviaDBConstants {
    public static final int MIN_NUMBER_OF_QUESTIONS = 1;
    public static final int MAX_NUMBER_OF_QUESTIONS = 50;
    public static final Map<Integer, String> CATEGORY_ID_TO_NAME_MAP = new HashMap<>();
    public static final Map<String, Integer> CATEGORY_NAME_TO_ID_MAP = new HashMap<>();

    static {
        try {
            initializePossibleCategoriesMaps();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public enum DifficultyLevels {
        RANDOM, EASY, MEDIUM, HARD;

        public static List<String> valuesAsString() {
            return Stream.of(DifficultyLevels.values()).map(Enum::name).toList();
        }
    }

    public enum TriviaTypes {
        RANDOM, MULTIPLE, BOOLEAN;

        public static List<String> valuesAsString() {
            return Stream.of(TriviaTypes.values()).map(Enum::name).toList();
        }
    }

    public static final List<String> ENCODING_TYPES = Collections.unmodifiableList(
            Arrays.asList("default", "url3986", "base64"));

    private static void initializePossibleCategoriesMaps() throws IOException {
        String urlRequest = "https://opentdb.com/api_category.php";
        String response = APIRequestHandler.sendAPIRequest(urlRequest);

        // Parse the response JSON using Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response);
        JsonNode categoriesArray = jsonResponse.get("trivia_categories");

        // Add random category type to maps
        CATEGORY_ID_TO_NAME_MAP.put(0, "RANDOM");
        CATEGORY_NAME_TO_ID_MAP.put("RANDOM", 0);

        // Iterate over the categories array and populate the map
        for (JsonNode categoryNode : categoriesArray) {
            int categoryId = categoryNode.get("id").asInt();
            String categoryName = categoryNode.get("name").asText().toUpperCase();

            CATEGORY_ID_TO_NAME_MAP.put(categoryId, categoryName);
            CATEGORY_NAME_TO_ID_MAP.put(categoryName, categoryId);
        }
    }
}
