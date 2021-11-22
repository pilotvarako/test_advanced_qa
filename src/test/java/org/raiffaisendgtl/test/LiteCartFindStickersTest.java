package org.raiffaisendgtl.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class LiteCartFindStickersTest extends TestBase {

    @Test
    public void findAllProducts() {
        ChromeDriver chromeDriver = getDriver();

        chromeDriver.navigate().to("http://localhost/litecart/en/");

        List<WebElement> categories = chromeDriver.findElements(By.cssSelector("div#box-most-popular," +
                "div#box-campaigns," +
                "div#box-latest-products"));

        for (WebElement category : categories) {
            List<WebElement> products = category.findElements(By.cssSelector("div.content li"));
            for (WebElement product : products) {
                assertTrue(isFindOnlyOneSticker(product));
            }
        }
    }

    public boolean isFindOnlyOneSticker(WebElement product) {
        return product.findElements(By.xpath(".//a[@class = 'link']" +
                "//div[contains(text(), 'New') or contains(text(), 'Sale')]"))
                .size() == 1;
    }

}
