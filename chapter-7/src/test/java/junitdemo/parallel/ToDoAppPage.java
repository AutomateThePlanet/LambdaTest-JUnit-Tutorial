package junitdemo.parallel;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ToDoAppPage {
    private final DriverAdapter driver;

    public ToDoAppPage(DriverAdapter driver) {
        this.driver = driver;
    }

    public void open(){
        driver.goToUrl("https://todomvc.com/");
    }

    public void assertLeftItems(int expectedCount){
        var resultSpan = driver.findElement(By.xpath("//footer/*/span | //footer/span"));
        if (expectedCount == 1){
            var expectedText = String.format("%d item left", expectedCount);
            driver.validateInnerTextIs(resultSpan, expectedText);
        } else {
            var expectedText = String.format("%d items left", expectedCount);
            driver.validateInnerTextIs(resultSpan, expectedText);
        }
    }

    public WebElement getItemCheckbox(String todoItem){
        var xpathLocator = String.format("//label[text()='%s']/preceding-sibling::input", todoItem);
        return driver.findElement(By.xpath(xpathLocator));
    }

    public void openTechnologyApp(String technologyName){
        var technologyLink = driver.findElement(By.linkText(technologyName));
        technologyLink.click();
    }

    public void addNewToDoItem(String todoItem){
        var todoInput = driver.findElement(By.xpath("//input[@placeholder='What needs to be done?']"));
        todoInput.sendKeys(todoItem);
        driver.getActions().click(todoInput).sendKeys(Keys.ENTER).perform();
    }
}
