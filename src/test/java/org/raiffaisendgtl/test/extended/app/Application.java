package org.raiffaisendgtl.test.extended.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.raiffaisendgtl.test.extended.pages.BasketPage;
import org.raiffaisendgtl.test.extended.pages.MainPage;
import org.raiffaisendgtl.test.extended.pages.ProductPage;

public class Application {

    private WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    private BasketPage basketPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        basketPage = new BasketPage(driver);
    }

    public void addProductsToBasket(int countProducts) {
        for (int product = 1; product <= countProducts; product++) {
            addProductToBasket(product);
        }
    }

    public void addProductToBasket(int quantity) {
        mainPage.open();
        productPage.open(mainPage.product).addToBasket();
        mainPage.updatingQuantity(String.valueOf(quantity));
    }

    public void deleteProductsFromTheBasket() {
        int quantity = Integer.parseInt(mainPage.getQuantity());
        basketPage.open().removeProducts(quantity);
        mainPage.open().updatingQuantity("0");
    }

    public void quit() {
        driver.quit();
    }

}
