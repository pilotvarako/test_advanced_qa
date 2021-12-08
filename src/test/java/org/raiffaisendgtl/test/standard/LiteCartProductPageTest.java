package org.raiffaisendgtl.test.standard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LiteCartProductPageTest {

    private WebDriver driver;

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void pagesAreMatchingInChromeBrowser() {
        driver = initializationBrowser("Chrome");

        pagesAreMatchingInBrowser(driver);
    }

    @Test
    public void pagesAreMatchingInFirefoxBrowser() {
        driver = initializationBrowser("Firefox");

        pagesAreMatchingInBrowser(driver);
    }

    @Test
    public void pagesAreMatchingInInternetExplorerBrowser() {
        driver = initializationBrowser("Internet Explorer");

        pagesAreMatchingInBrowser(driver);
    }

    public WebDriver initializationBrowser(String nameBrowser) {
        WebDriver driver = null;
        if (nameBrowser.equals("Chrome")) {
            driver = new ChromeDriver();
        }
        if (nameBrowser.equals("Firefox")) {
            driver = new FirefoxDriver();
        }
        if (nameBrowser.equals("Internet Explorer")) {
            driver = new InternetExplorerDriver();
        }
        assertNotNull(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        return driver;
    }

    public void pagesAreMatchingInBrowser(WebDriver driver) {
        String urlMainPage = "http://localhost/litecart/en/";

        WebElement productOnMainPage = getOrUpdateProductOnMainPage(driver, urlMainPage);
        assertTrue(textPropertiesProductOnMainPageEqualsTextPropertiesProductOnLocal(driver, productOnMainPage));

        productOnMainPage = getOrUpdateProductOnMainPage(driver, urlMainPage);
        assertTrue(isCorrectCssPropertiesProductOnPage(productOnMainPage));

        productOnMainPage.findElement(By.cssSelector(".link")).click();

        WebElement productOnLocalPage = driver.findElement(By.cssSelector("div#box-product"));
        assertTrue(isCorrectCssPropertiesProductOnPage(productOnLocalPage));
    }

    public boolean textPropertiesProductOnMainPageEqualsTextPropertiesProductOnLocal(WebDriver driver,
                                                                                     WebElement productOnMainPage) {
        Map<String, String> textPropertiesProductOnMainPage = new HashMap<>();

        String[] namePropertiesForProduct= new String[] { "name", "regularPrice", "campaignPrice" };
        By[] locatorsForProduct = new By[] {
                By.cssSelector(".name"),
                By.cssSelector(".regular-price"),
                By.cssSelector(".campaign-price")
        };

        setTextPropertiesProduct(textPropertiesProductOnMainPage, productOnMainPage,
                namePropertiesForProduct, locatorsForProduct);

        productOnMainPage.findElement(By.cssSelector(".link")).click();

        WebElement productOnLocalPage = driver.findElement(By.cssSelector("div#box-product"));
        Map<String, String> textPropertiesProductOnLocalPage = new HashMap<>();

        locatorsForProduct[0] = By.cssSelector(".title");

        setTextPropertiesProduct(textPropertiesProductOnLocalPage, productOnLocalPage,
                namePropertiesForProduct, locatorsForProduct);

        return textPropertiesProductOnMainPage.equals(textPropertiesProductOnLocalPage);
    }

    public WebElement getOrUpdateProductOnMainPage(WebDriver driver, String urlMainPage) {
        driver.navigate().to(urlMainPage);
        return driver.findElement(By.cssSelector("div#box-campaigns li.product:first-child"));
    }

    public void setTextPropertiesProduct(Map<String, String> textPropertiesProduct, WebElement product,
                                             String[] nameProperties, By[] locator) {
        for (int element = 0; element < nameProperties.length; element++) {
            textPropertiesProduct.put(nameProperties[element], product.findElement(locator[element]).getText());
        }
    }

    public boolean isCorrectCssPropertiesProductOnPage(WebElement product) {
        Map<String, String> cssPropertiesProductOnMainPage = new HashMap<>();

        String[] namePropertiesForProduct= new String[] { "colorOfRegularPrice", "colorOfCampaignPrice" };
        By[] locatorsForProduct = new By[] { By.cssSelector(".regular-price"), By.cssSelector(".campaign-price") };

        setCssPropertiesProduct(cssPropertiesProductOnMainPage, product,
                namePropertiesForProduct, locatorsForProduct, "color");
        namePropertiesForProduct = new String[] { "fontSizeOfRegularPrice", "fontSizeOfCampaignPrice" };
        setCssPropertiesProduct(cssPropertiesProductOnMainPage, product, namePropertiesForProduct,
                locatorsForProduct, "font-size");

        boolean colorOfRegularPrice = colorElementIsGray(cssPropertiesProductOnMainPage.get("colorOfRegularPrice"));
        boolean colorOfCampaignPrice = colorElementIsRed(cssPropertiesProductOnMainPage.get("colorOfCampaignPrice"));
        boolean sizeOfCampaignPrice = isFontSizeOfCampaignPriceMoreFontSizeOfRegularPrice(
                cssPropertiesProductOnMainPage.get("fontSizeOfRegularPrice"),
                cssPropertiesProductOnMainPage.get("fontSizeOfCampaignPrice")
        );

        return colorOfRegularPrice && colorOfCampaignPrice && sizeOfCampaignPrice;
    }

    public void setCssPropertiesProduct(Map<String, String> cssPropertiesProduct, WebElement product,
                                         String[] nameProperties, By[] locator, String nameProperty) {
        for (int element = 0; element < nameProperties.length; element++) {
            cssPropertiesProduct.put(nameProperties[element], product.findElement(locator[element])
                    .getCssValue(nameProperty));
        }
    }

    public boolean colorElementIsGray(String color) {
        int[] rgba = convertColorElementToIntArray(color);
        return areElementsEqualToEachOther(Arrays.copyOf(rgba, 3));
    }

    public boolean colorElementIsRed(String color) {
        int[] rgba = convertColorElementToIntArray(color);
        return rgba[1] == 0 && rgba[2] == 0;
    }

    public int[] convertColorElementToIntArray(String color) {
        String[] rgba = color.replaceAll("[^\\d ]", "").split(" ");
        return Arrays.stream(rgba).mapToInt(Integer::parseInt).toArray();
    }

    public boolean areElementsEqualToEachOther(int[] array) {
        for (int each = 0, other = 1; other < array.length; other++) {
            if (array[each] != array[other]) {
                return false;
            }
        }
        return true;
    }

    public boolean isFontSizeOfCampaignPriceMoreFontSizeOfRegularPrice(String fontSizeOfRegularPrice,
                                                                       String fontSizeOfCampaignPrice) {
        double fontSizeOfRegularPriceToDouble = convertFontSizeToDouble(fontSizeOfRegularPrice);
        double fontSizeOfCampaignPriceToDouble = convertFontSizeToDouble(fontSizeOfCampaignPrice);
        return fontSizeOfCampaignPriceToDouble > fontSizeOfRegularPriceToDouble;
    }

    public double convertFontSizeToDouble(String fontSize) {
        String formatFontSize = fontSize.replaceAll("[^\\d.]", "");
        return Double.parseDouble(formatFontSize);
    }

}
