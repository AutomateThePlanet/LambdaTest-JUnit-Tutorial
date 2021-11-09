package junitdemo.parallel;

import org.openqa.selenium.By;

public class MainPage {
    private final DriverAdapter driver;

    public MainPage(DriverAdapter driver) {
        this.driver = driver;
    }

    public void open(){
        driver.goToUrl("https://todomvc.com/");
    }

    public void openTechnologyApp(String name) {
        var technologyLink = driver.findElement(By.linkText(name));
        technologyLink.click();
    }
}
