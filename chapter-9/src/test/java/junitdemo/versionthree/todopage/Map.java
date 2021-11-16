package junitdemo.versionthree.todopage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Map {
    private WebDriverWait webDriverWait;

    public Map(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }

    public WebElement resultSpan() {
        return waitAndFindElement(By.xpath("//footer/*/span | //footer/span"));
    }

    public WebElement itemCheckbox(String todoItem){
        var xpathLocator = String.format("//label[text()='%s']/preceding-sibling::input", todoItem);
        return waitAndFindElement(By.xpath(xpathLocator));
    }

    public WebElement technologyApp(String technologyName){
        var technologyLink = waitAndFindElement(By.linkText(technologyName));
        return technologyLink;
    }

    public WebElement toDoInput(){
        return waitAndFindElement(By.xpath("//input[@placeholder='What needs to be done?']"));
    }

    protected WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
