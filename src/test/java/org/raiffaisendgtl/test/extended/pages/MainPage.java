package org.raiffaisendgtl.test.extended.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class MainPage extends Page {

    @FindBy(className = "product")
    public WebElement product;

    @FindBy(className = "quantity")
    public WebElement quantityProductInBasket;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage open() {
        driver.navigate().to("http://localhost/litecart/en/");
        return this;
    }

    public String getQuantity() {
        return quantityProductInBasket.getText();
    }

    public void updatingQuantity(String quantity) {
        wait.until(textToBePresentInElement(quantityProductInBasket, quantity));
    }

}
