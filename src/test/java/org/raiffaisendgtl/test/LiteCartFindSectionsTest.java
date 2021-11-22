package org.raiffaisendgtl.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class LiteCartFindSectionsTest extends TestBase {

    @Test
    public void findAllSections() {
        ChromeDriver chromeDriver = getDriver();

        chromeDriver.navigate().to("http://localhost/litecart/admin/");
        chromeDriver.findElement(By.name("username")).sendKeys("admin");
        chromeDriver.findElement(By.name("password")).sendKeys("admin");
        chromeDriver.findElement(By.name("login")).click();

        int countSections = chromeDriver.findElements(By.cssSelector("li#app-")).size();
        for (int section = 0; section < countSections; section++) {
            List<WebElement> itemsSection = chromeDriver.findElements(By.cssSelector("li#app-"));
            itemsSection.get(section).click();
            int countTagH1 = 1;
            int countCategories = chromeDriver.findElements(By.cssSelector("ul.docs li,h1")).size() - countTagH1;
            for (int category = 1; category < countCategories; category++) {
                List<WebElement> itemsCategory = chromeDriver.findElements(By.cssSelector("ul.docs li"));
                itemsCategory.get(category).click();
                chromeDriver.findElement(By.cssSelector("h1"));
            }
        }
    }

}
