package junitdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;

public class CalculateDistancesTests extends WebTest {
    @Override
    protected void initializeDriver(String testName) {
        ///getDriver().start(Browser.CHROME);
    }

    @ParameterizedTest(name = "{index}. verify distance from = {0} to {2}")
    @CsvSource(value = {
            "Germany,DE,Berlin,1320.41 km / 820.47 mi",
            "Argentina,AR,Buenos Aires,11951.47 km / 7426.3 mi",
            "Australia,AU,Canberra,15355.98 km / 9541.76 mi",
            "Canada,CA,Ottawa,7379.06 km / 4585.14 mi",
            "Japan,JP,Tokyo,9188.49 km / 5709.46 mi",
            "Taiwan,TW,Taipei City,8789.28 km / 5461.41 mi",
            "Norway,NO,Oslo,2098.7 km / 1304.07 mi",
            "South Africa,ZA,Cape Town,8544.51 km / 5309.32 mi"
    })
    public void verifyDistance(String country, String countryCode, String capital, String expectedDistance){
        getDriver().start(Browser.CHROME, "verify distance from Bulgaria to " + country, countryCode);

        getDriver().goToUrl("https://www.gps-coordinates.net/");
        getDriver().addCookie("cookieconsent_dismissed", "yes");

        var map = getDriver().findElement(By.xpath("//*[@id='map_canvas']"));
        Assertions.assertTrue(map.isDisplayed());

        var addressTitle = getDriver().findElement(By.xpath("//*[@id='iwtitle']"));
        String address = addressTitle.getText();
        var addressParts = address.split(",");
        var lastPartAddress = addressParts[addressParts.length - 1];
        String currentAddress = String.format("%s, %s", capital, lastPartAddress);

        getDriver().goToUrl("https://www.gps-coordinates.net/distance");

        var firstDistanceAddressInput = getDriver().findElement(By.id("address1"));
        firstDistanceAddressInput.sendKeys(currentAddress);

        var secondDistanceAddressInput = getDriver().findElement(By.id("address2"));
        secondDistanceAddressInput.sendKeys("Berlin, Germany");

        var calculateDistanceButton = getDriver().findElement(By.xpath("//button[text()='Calculate the distance']"));
        getDriver().getActions().moveToElement(calculateDistanceButton).click().perform();

        var distanceSpan = getDriver().findElement(By.id("distance"));

        getDriver().validateInnerTextIs(distanceSpan, expectedDistance);
    }

    @Test
    public void verifyCalculatedDistance_when_countryGermany(){
        getDriver().start(Browser.CHROME);
        getDriver().goToUrl("https://www.gps-coordinates.net/");
        getDriver().addCookie("cookieconsent_dismissed", "yes");

        var map = getDriver().findElement(By.xpath("//*[@id='map_canvas']"));
        Assertions.assertTrue(map.isDisplayed());

        var addressTitle = getDriver().findElement(By.xpath("//*[@id='iwtitle']"));
        String address = addressTitle.getText();
        var addressParts = address.split(",");
        var lastPartAddress = addressParts[addressParts.length - 1];
        String currentAddress = String.format("%s, %s", lastPartAddress, "Sofia");

        getDriver().goToUrl("https://www.gps-coordinates.net/distance");

        var firstDistanceAddressInput = getDriver().findElement(By.id("address1"));
        firstDistanceAddressInput.sendKeys(currentAddress);

        var secondDistanceAddressInput = getDriver().findElement(By.id("address2"));
        secondDistanceAddressInput.sendKeys("Berlin, Germany");

        var calculateDistanceButton = getDriver().findElement(By.xpath("//button[text()='Calculate the distance']"));
        getDriver().getActions().moveToElement(calculateDistanceButton).click().perform();

        var distanceSpan = getDriver().findElement(By.id("distance"));

        getDriver().validateInnerTextIs(distanceSpan, "1320.41 km / 820.47 mi");
    }
}
