import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BrokenLinks {
    public static void main(String[] args) throws IOException {
        WebDriver driver = new EdgeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");

        // Find all the links on the webpage that have a certain CSS selector
        List<WebElement> links = driver.findElements(By.cssSelector("li[class='gf-li'] a"));

        // Create a SoftAssert object to allow for multiple assertions in a single test
        SoftAssert a = new SoftAssert();

        // Iterate through each link and check its response code using HttpURLConnection
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();

            // Print the response code to the console
            System.out.println(respCode);

            // Assert that the response code is less than 400, indicating that the link is not broken
            // Print an error message if the assertion fails, including the link text and response code
            a.assertTrue(respCode < 400, "The link with Text '" + link.getText() + "' is broken with code " + respCode);
        }

        // Call the assertAll method to report any assertion failures at the end of the test
        a.assertAll();

    }
}
