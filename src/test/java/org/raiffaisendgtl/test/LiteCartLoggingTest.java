package org.raiffaisendgtl.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

public class LiteCartLoggingTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void checkBrowserLogs() {
        String urlPageCategories = "http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1";
        driver.navigate().to(urlPageCategories);

        int positionNameProductInTable = findPositionColumn("Name");
        By locator = By.cssSelector("td:nth-child(" + positionNameProductInTable + ") > a[href*='category_id=1&']");

        int countProducts = driver.findElements(locator).size();
        for (int product = 0; product < countProducts; product++) {
            List<WebElement> products = driver.findElements(locator);
            products.get(product).click();
            assertTrue(isEmptyLogs());
            driver.navigate().to(urlPageCategories);
        }
    }

    public int findPositionColumn(String nameColumn) {
        int position = -1;
        List<WebElement> columnTable = driver.findElements(By.cssSelector("table.dataTable tr.header > th"));
        for (int index = 0; index < columnTable.size(); index++) {
            if (columnTable.get(index).getText().equals(nameColumn)) {
                position = index + 1;
                return position;
            }
        }
        return position;
    }

    public boolean isEmptyLogs() {
        return driver.manage().logs().get("browser").getAll().isEmpty();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

}
