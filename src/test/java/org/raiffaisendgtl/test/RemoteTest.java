package org.raiffaisendgtl.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class RemoteTest {

    @Test
    public void test() throws MalformedURLException {
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.101:4444"), new InternetExplorerOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.navigate().to("https://www.google.com/");

        driver.quit();
        driver = null;
    }

}
