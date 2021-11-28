package org.raiffaisendgtl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class LiteCartFindSectionsTest extends TestBase {

    @Test
    public void findAllSections() {
        WebDriver chromeDriver = getDriver();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        chromeDriver.navigate().to("http://localhost/litecart/admin/");
        chromeDriver.findElement(By.name("username")).sendKeys("admin");
        chromeDriver.findElement(By.name("password")).sendKeys("admin");
        chromeDriver.findElement(By.name("login")).click();

        int countSections = chromeDriver.findElements(By.cssSelector("li#app-")).size();
        for (int section = 0; section < countSections; section++) {
            List<WebElement> itemsSection = chromeDriver.findElements(By.cssSelector("li#app-"));
            itemsSection.get(section).click();
            assertTrue(isFindOnlyOneTagH1(chromeDriver));
            int countCategories = chromeDriver.findElements(By.cssSelector("ul.docs li")).size();
            for (int category = 1; category < countCategories; category++) {
                List<WebElement> itemsCategory = chromeDriver.findElements(By.cssSelector("ul.docs li"));
                itemsCategory.get(category).click();
                assertTrue(isFindOnlyOneTagH1(chromeDriver));
            }
        }
    }

    public boolean isFindOnlyOneTagH1(WebDriver driver) {
        return driver.findElements(By.cssSelector("h1")).size() == 1;
    }

}
