package junitdemo.parallel;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class PureJsToDoTestsChrome extends WebTest {
    private ToDoFacade toDoFacade;

    @Override
    protected void initializeDriver() {
        getDriver().start(Browser.CHROME);
        toDoFacade = new ToDoFacade(new MainPage(getDriver()), new ToDoAppPage(getDriver()));
    }

    @ParameterizedTest(name = "{index}. verify todo list created successfully when technology = {0}")
    @ValueSource(strings = {
            "Backbone.js",
            "AngularJS",
            "React",
            "Vue.js",
            "CanJS",
            "Ember.js",
            "KnockoutJS",
            "Marionette.js",
            "Polymer",
            "Vanilla JS",
            "jQuery"
    })
    public void verifyToDoListCreatedSuccessfully_withParams(String technology){
        var itemsToAdd = List.of("Clean the car", "Clean the house", "Buy milk", "Buy Ketchup");
        var itemsToCheck = List.of("Clean the house", "Buy milk");

        toDoFacade.verifyTodoListCreatedSuccessfully(technology, itemsToAdd, itemsToCheck, 2);
    }
}
