package org.raiffaisendgtl.test.standard;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class CheckTest {

    private static ChromeDriver driver;

    @BeforeAll
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void test() {
        driver.navigate().to("https://www.java.com/ru/");
    }

}
