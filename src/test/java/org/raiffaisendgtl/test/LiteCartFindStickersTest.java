package org.raiffaisendgtl.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class LiteCartFindStickersTest extends TestBase {

    @Test
    public void findAllProducts() {
        ChromeDriver chromeDriver = getDriver();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        chromeDriver.navigate().to("http://localhost/litecart/en/");

        List<WebElement> products = chromeDriver.findElements(By.cssSelector("ul.products > li.product"));
        for (WebElement product : products) {
            assertTrue(isFindOnlyOneSticker(product));
        }
    }

    public boolean isFindOnlyOneSticker(WebElement product) {
        return product.findElements(By.cssSelector("div.sticker")).size() == 1;
    }

}
