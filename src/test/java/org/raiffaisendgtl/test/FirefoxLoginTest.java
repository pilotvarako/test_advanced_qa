package org.raiffaisendgtl.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class FirefoxLoginTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\programming\\other\\geckodriver.exe");
    }

    @AfterEach
    public void tearDown() {
        driver.close();
        driver = null;
    }

    @Test
    public void standardFirefoxTest() {
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void nightlyFirefoxTest() {
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Firefox Nightly\\firefox.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

}
