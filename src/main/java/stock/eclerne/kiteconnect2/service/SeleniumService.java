package stock.eclerne.kiteconnect2.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SeleniumService {

    public String getRequestToken(String loginUrl, String userId, String password, String otp) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Documents\\Code\\kiteconnect2\\drivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run headless Chrome
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get(loginUrl);


            WebElement userIdField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='userid']")));
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']")));

           // WebElement userIdField = driver.findElement(By.xpath("//input[@id='userid']"));
           // WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
            WebElement loginButton = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div/div/div[2]/form/div[4]/button"));
            userIdField.sendKeys(userId);
            passwordField.sendKeys(password);
            loginButton.click();


            WebElement totpField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div[2]/div/div[2]/form/div[1]/input")));

            // Generated the TOTP using the secret key from AccessTokenService class
            totpField.sendKeys(otp);

            //WebElement totpSubmitButton = driver.findElement(By.xpath("//button[@type='submit']"));
            //totpSubmitButton.click();

            wait.until(ExpectedConditions.urlContains("request_token="));
            String currentUrl = driver.getCurrentUrl();

            String requestToken = currentUrl.split("request_token=")[1].split("&")[0];
            return requestToken;
        } finally {
            driver.quit();
        }
    }
}
