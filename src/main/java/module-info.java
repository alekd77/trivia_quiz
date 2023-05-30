module com.triviagame.triviagame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.text;
    requires org.apache.logging.log4j;

    opens com.triviagame.triviagame to javafx.fxml;
    opens com.triviagame.triviagame.model to javafx.fxml;
    opens com.triviagame.triviagame.controller to javafx.fxml;
    opens com.triviagame.triviagame.controller.impl to javafx.fxml;

    exports com.triviagame.triviagame.controller.exception;
    exports com.triviagame.triviagame to javafx.graphics;
    exports com.triviagame.triviagame.model to com.fasterxml.jackson.databind;
    exports com.triviagame.triviagame.database.trivia.opentriviadb to com.fasterxml.jackson.databind;
    exports com.triviagame.triviagame.database.trivia to com.fasterxml.jackson.databind;
    exports com.triviagame.triviagame.controller to javafx.graphics;
    exports com.triviagame.triviagame.controller.impl to javafx.fxml, javafx.graphics;
    exports com.triviagame.triviagame.database.trivia.exception to com.fasterxml.jackson.databind;
    exports com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception;
}