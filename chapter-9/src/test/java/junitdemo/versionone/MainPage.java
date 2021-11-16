package junitdemo.versionone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait webDriverWait;

    public MainPage(WebDriver driver, WebDriverWait webDriverWait) {
        this.driver = driver;
        this.webDriverWait = webDriverWait;
    }

    public void open(){
        driver.navigate().to("https://todomvc.com/");
    }

    public void openTechnologyApp(String name) {
        var technologyLink = waitAndFindElement(By.linkText(name));
        technologyLink.click();
    }

    private WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
