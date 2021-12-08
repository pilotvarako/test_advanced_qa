package org.raiffaisendgtl.test.standard;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class RemoteTest {

    private WebDriver driver;

    @Test
    public void test() throws MalformedURLException {
        driver = new RemoteWebDriver(new URL("http://192.168.0.101:4444"), new InternetExplorerOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.navigate().to("https://www.google.com/");

        driver.quit();
        driver = null;
    }

    @Test
    public void cloudyTest() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Windows 10");
        options.setBrowserVersion("latest");

        URL url = new URL("https://oauth-pilotvarako-3692f:8495a360-e325-4985-9c22-9f43f9c714cb@ondemand.eu-central-1.saucelabs.com:443/wd/hub");

        driver = new RemoteWebDriver(url, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.navigate().to("https://github.com/");

        driver.quit();
        driver = null;
    }

}
