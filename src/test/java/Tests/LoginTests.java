package Tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTests {

    public WebDriver Driver;
    public final String Email = "nafornita.ioana@gmail.com";
    public final String Password = "nimic";

    @Test
    public void loginMethod()
    {
        //Setam driverul de chrome
        System.setProperty("webdriver.chrome.driver","C:\\Automation\\chromedriver.exe");

        //Deschidem un browser de chrome
        Driver=new ChromeDriver();

        //Accesam pagina Emag
        Driver.get("https://emag.ro");

        //Gasim butonul de "My account"
        WebElement myAccountLink = Driver.findElement(By.id("my_account"));

        Actions actions = new Actions(Driver);
        //Gasim butonul de intrare in cont care ne duce la pagina de login
        WebElement loginLinkElement = Driver
                .findElement(By.xpath("//a[@href='/user/login?ref=hdr_login_btn']"));

        //Dam hover mai intai pe butonul de "My account" iar apoi dam click pe butonul de intrare in cont
        actions.moveToElement(myAccountLink).moveToElement(loginLinkElement)
                .click().build().perform();

        //Gasim input box-ul pentru email
        WebElement emailInput = Driver.findElement(By.id("user_login_email"));
        //Inseram email-ul declarat ca si constanta
        emailInput.sendKeys(Email);

        //Apasam butonul de continuare
        WebElement loginContinueButton = Driver.findElement(By.id("user_login_continue"));
        loginContinueButton.click();

        //Adaugam timeout sa avem timp sa completam Captcha
        WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user_login_password")));
        //Gasim input box-ul pentru parola
        WebElement passwordInput = Driver.findElement(By.id("user_login_password"));
        //Inseram parola
        passwordInput.sendKeys(Password);

        //Apasam butonul de login
        WebElement loginFinishButton = Driver.findElement(By.id("user_login_continue"));
        loginFinishButton.click();

        //Timeout pentru a completa capcha-ul final de logare
        //Verificam ca butonul de logout este prezent
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/user/logout?ref=ua_logout']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("my_account")));
        //Verificam ca suntem inapoi pe pagina principala dupa o logare cu succes
        String expectedPage = "eMAG.ro - Căutarea nu se oprește niciodată";
        String actualPage = Driver.getTitle();
        Assert.assertEquals("The expected page was not displayed",expectedPage,actualPage);
    }
}
