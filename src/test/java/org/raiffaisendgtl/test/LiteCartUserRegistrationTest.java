package org.raiffaisendgtl.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.*;

public class LiteCartUserRegistrationTest extends TestBase {

    @Test
    public void userRegistration() {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        String urlRegistryPage = "http://localhost/litecart/en/create_account";

        Map<String, String> infoUser = new HashMap<>();
        String[] nameInfoInputs = new String[] {
                "firstname", "lastname", "address1", "postcode", "city",
                "email", "phone" , "password", "confirmed_password"
        };
        initializeContentInputs(infoUser, nameInfoInputs);

        driver.navigate().to(urlRegistryPage);

        Map<String, WebElement> mapFields = new HashMap<>();
        findInputElements(mapFields, nameInfoInputs);
        fillInputElements(mapFields, infoUser, nameInfoInputs);

        String[] nameInfoSelects = new String[] { "country_code", "zone_code" };
        initializeContentSelects(infoUser, nameInfoSelects);

        findSelectElements(mapFields, nameInfoSelects);
        fillSelectElements(mapFields, infoUser, nameInfoSelects);

        createUser();

        logoutUser();

        String[] nameLoginInputs = new String[] { "email", "password" };
        findInputElements(mapFields, nameLoginInputs);
        fillInputElements(mapFields, infoUser, nameLoginInputs);

        loginUser();

        logoutUser();
    }

    public void initializeContentInputs(Map<String, String> infoUser, String[] nameInfoInputs) {
        String emailUser = generateEmailUser();
        String[] contentInfoInputs = new String[] {
                "Vladislav", "Varako", "addressTest", "11555", "cityTest",
                emailUser, "13337770000", "1t2e3s4t5", "1t2e3s4t5"
        };
        setInfoUser(infoUser, nameInfoInputs, contentInfoInputs);
    }

    public void initializeContentSelects(Map<String, String> infoUser, String[] nameInfoSelects) {
        String[] contentInfoSelects = new String[] { "United States", "Kansas" };
        setInfoUser(infoUser, nameInfoSelects, contentInfoSelects);
    }

    public String generateEmailUser() {
        return UUID.randomUUID().toString().replaceAll("-", "") + "@example.com";
    }

    public void setInfoUser(Map<String, String> infoUser, String[] nameFields, String[] contentFields) {
        for (int element = 0; element < nameFields.length; element++) {
            infoUser.put(nameFields[element], contentFields[element]);
        }
    }

    public void findInputElements(Map<String, WebElement> mapFields, String[] nameInfoInputs) {
        WebDriver driver = getDriver();
        for (String nameInfoInput : nameInfoInputs) {
            mapFields.put(nameInfoInput, driver.findElement(By.cssSelector("input[name=" + nameInfoInput + "]")));
        }
    }

    public void fillInputElements(Map<String, WebElement> mapFields, Map<String, String> infoUser,
                                   String[] nameInfoFields) {
        for (String nameInfoField : nameInfoFields) {
            mapFields.get(nameInfoField).sendKeys(infoUser.get(nameInfoField));
        }
    }

    public void findSelectElements(Map<String, WebElement> mapFields, String[] nameInfoInputs) {
        WebDriver driver = getDriver();
        for (String nameInfoInput : nameInfoInputs) {
            mapFields.put(nameInfoInput, driver.findElement(By.cssSelector("select[name=" + nameInfoInput + "]")));
        }
    }

    public void fillSelectElements(Map<String, WebElement> mapFields, Map<String, String> infoUser,
                                   String[] nameInfoFields) {
        for (String nameInfoField : nameInfoFields) {
            Select fieldSelect = new Select(mapFields.get(nameInfoField));
            fieldSelect.selectByVisibleText(infoUser.get(nameInfoField));
        }
    }

    public void createUser() {
        getDriver().findElement(By.cssSelector("button[name=create_account]")).click();
    }

    public void logoutUser() {
        getDriver().findElement(By.xpath("//div[@id='box-account']//a[contains(., 'Logout')]")).click();
    }

    public void loginUser() {
        getDriver().findElement(By.cssSelector("button[name=login]")).click();
    }

}
