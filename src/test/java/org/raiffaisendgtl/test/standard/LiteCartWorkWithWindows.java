package org.raiffaisendgtl.test.standard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class LiteCartWorkWithWindows {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void workWithWindows() {
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector("a.button")).click();

        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        int countLinks = driver.findElements(By.cssSelector("td > a[target='_blank']")).size();
        for (int link = 0; link < countLinks; link++) {
            List<WebElement> links = driver.findElements(By.cssSelector("td > a[target='_blank']"));
            links.get(link).click();
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return webDriver -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(oldWindows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }
}
