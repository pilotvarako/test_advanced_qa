package org.raiffaisendgtl.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.time.Duration;

public class LiteCartWorkWithBasket {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void workWithBasket() {
        String urlMainPage = "http://localhost/litecart/en/";

        int countProducts = 3;
        for (int product = 1; product <= countProducts; product++) {
            driver.navigate().to(urlMainPage);
            driver.findElement(By.cssSelector(".product")).click();
            if (isElementPresent(By.cssSelector("[name='options[Size]']"))) {
                Select elementSelect = new Select(driver.findElement(By.cssSelector("[name='options[Size]']")));
                elementSelect.selectByIndex(1);
            }
            driver.findElement(By.cssSelector("[name='add_cart_product']")).click();
            wait.until(textToBePresentInElementLocated(By.cssSelector(".quantity"), String.valueOf(product)));
        }

        driver.findElement(By.cssSelector("a.link")).click();

        for (int product = 1; product <= countProducts; product++) {
            WebElement table = driver.findElement(By.cssSelector(".dataTable"));
            driver.findElement(By.cssSelector("[name='remove_cart_item']")).click();
            wait.until(stalenessOf(table));
            if (!isElementPresent(By.cssSelector(".dataTable"))) {
                break;
            }
        }

        driver.navigate().to(urlMainPage);
        wait.until(textToBePresentInElementLocated(By.cssSelector(".quantity"), "0"));
    }

    public boolean isElementPresent(By locator) {
        return wait.until((WebDriver d) -> d.findElements(locator).size()) > 0;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

}
