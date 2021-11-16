package junitdemo.versionthree.todopage;

import junitdemo.versionthree.WebPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToDoAppPage extends WebPage {
    public ToDoAppPage(WebDriver driver, WebDriverWait webDriverWait, Actions actions) {
        super(driver, webDriverWait, actions);
    }

    @Override
    protected String getUrl() {
        return "https://todomvc.com/";
    }

    public Map map() {
        return new Map(getWebDriverWait());
    }

    public Assertions assertions() {
        return new Assertions(getWebDriverWait(), map());
    }

    public void addNewToDoItem(String todoItem){
        map().toDoInput().sendKeys(todoItem);
        getActions().click(map().toDoInput()).sendKeys(Keys.ENTER).perform();
    }
}
