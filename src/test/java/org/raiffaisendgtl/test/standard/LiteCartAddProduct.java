package org.raiffaisendgtl.test.standard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class LiteCartAddProduct extends TestBase {

    @BeforeEach
    public void setUp() {
        super.setUp();
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void addNewProduct() {
        String urlPageCatalog = "http://localhost/litecart/admin/?app=catalog&doc=catalog";
        WebDriver driver = getDriver();
        driver.navigate().to(urlPageCatalog);

        driver.findElement(By.cssSelector(".button:last-child")).click();
        String nameProduct = "Sofa Black";
        String codeProduct = generateCodeProduct();
        fillFieldsGeneralProduct(nameProduct, codeProduct);

        driver.findElement(By.xpath("//*[contains(@class, 'index')]//a[contains(text(),'Information')]")).click();
        fillFieldsInformationProduct();

        driver.findElement(By.xpath("//*[contains(@class, 'index')]//a[contains(text(),'Prices')]")).click();
        fillFieldsPricesProduct();

        driver.findElement(By.cssSelector("[name='save']")).click();

        assertTrue(findAddedItem(urlPageCatalog, nameProduct, codeProduct));
    }

    public String generateCodeProduct() {
        int code = (int) (Math.random() * 1000);
        return "Sofa" + code;
    }

    public void fillFieldsGeneralProduct(String nameProduct, String codeProduct) {
        WebDriver driver = getDriver();

        driver.findElement(By.cssSelector("[name='status'][value='1']")).click();
        driver.findElement(By.cssSelector("[name='name[en]']")).sendKeys(nameProduct);
        driver.findElement(By.cssSelector("[name='code']")).sendKeys(codeProduct);
        driver.findElement(By.cssSelector("[name='quantity']")).clear();
        driver.findElement(By.cssSelector("[name='quantity']")).sendKeys("10");

        Select fieldSelect = new Select(driver.findElement(By.cssSelector("[name='sold_out_status_id']")));
        fieldSelect.selectByVisibleText("Temporary sold out");

        File file = new File("src/test/resources/products/sofa1.png");
        driver.findElement(By.cssSelector("[name='new_images[]']")).sendKeys(file.getAbsolutePath());
    }

    public void fillFieldsInformationProduct() {
        WebDriver driver = getDriver();

        Select fieldSelect = new Select(driver.findElement(By.cssSelector("[name='manufacturer_id']")));
        fieldSelect.selectByVisibleText("ACME Corp.");

        driver.findElement(By.cssSelector("[name='short_description[en]']")).sendKeys("test short input");
        driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys("test textarea");
    }

    public void fillFieldsPricesProduct() {
        WebDriver driver = getDriver();
        WebElement number;

        number = driver.findElement(By.cssSelector("[name='purchase_price']"));
        number.clear();
        number.sendKeys("15");

        number = driver.findElement(By.cssSelector("[name='prices[USD]']"));
        number.clear();
        number.sendKeys("25");
    }

    public boolean findAddedItem(String urlPageCatalog, String nameProduct, String codeProduct) {
        WebDriver driver = getDriver();
        int countProducts = driver.findElements(By.xpath("//a[contains(text(),'" + nameProduct + "')]")).size();
        for (int product = 0; product < countProducts; product++) {
            List<WebElement> products = driver.findElements(By.xpath("//a[contains(text(),'" + nameProduct + "')]"));
            products.get(product).click();
            List<WebElement> element = driver.findElements(By.cssSelector("[name='code'][value='" + codeProduct + "']"));
            if (element.size() == 1) {
                return true;
            }
            driver.navigate().to(urlPageCatalog);
        }
        return false;
    }

}
