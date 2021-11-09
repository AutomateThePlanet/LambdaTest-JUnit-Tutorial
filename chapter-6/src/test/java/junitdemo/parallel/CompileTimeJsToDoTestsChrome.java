package junitdemo.parallel;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CompileTimeJsToDoTestsChrome extends WebTest {
    private ToDoFacade toDoFacade;

    @Override
    protected void initializeDriver() {
        getDriver().start(Browser.CHROME);
        toDoFacade = new ToDoFacade(new MainPage(getDriver()), new ToDoAppPage(getDriver()));
    }

    @ParameterizedTest(name = "{index}. verify todo list created successfully when technology = {0}")
    @ValueSource(strings = {
            "Angular 2.0",
            "Dart",
            "Elm",
            "Closure",
            "Spine",
            "Mithril",
            "Kotlin + React",
            "Firebase + AngularJS",
            "Vanilla ES6"
    })
    public void verifyToDoListCreatedSuccessfully_withParams(String technology){
        var itemsToAdd = List.of("Clean the car", "Clean the house", "Buy milk", "Buy Ketchup");
        var itemsToCheck = List.of("Clean the house", "Buy milk");

        toDoFacade.verifyTodoListCreatedSuccessfully(technology, itemsToAdd, itemsToCheck, 2);
    }
}
