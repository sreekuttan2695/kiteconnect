package stock.eclerne.kiteconnect2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Documents\\Code\\kiteconnect2\\drivers\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        // Open a website
        driver.get("https://www.google.com");

        // Perform actions on the website
        System.out.println("Title: " + driver.getTitle());

        // Close the browser
        driver.quit();
    }
}
