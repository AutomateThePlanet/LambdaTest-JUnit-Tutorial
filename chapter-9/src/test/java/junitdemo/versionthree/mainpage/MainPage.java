package junitdemo.versionthree.mainpage;

import junitdemo.versiontwo.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends WebPage {
    public MainPage(WebDriver driver, WebDriverWait webDriverWait, Actions actions) {
        super(driver, webDriverWait, actions);
    }

    @Override
    protected String getUrl() {
        return "https://todomvc.com/";
    }

    public void openTechnologyApp(String name) {
        var technologyLink = waitAndFindElement(By.linkText(name));
        technologyLink.click();
    }
}
