import Cucumber.SelectClickListener;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestSite {

        @Test
        public void openSite() {
            driver.get("https://otus.ru/");
        }

        public static EventFiringWebDriver driver;
        protected WebDriverWait wait;

        @BeforeTest
        public void setUp(ITestContext context) {
            driver = new EventFiringWebDriver(setupDriver("CHROME"));
            driver.register(new SelectClickListener());

            context.setAttribute("driver", driver);
            wait = new WebDriverWait(driver, 5);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);// Пауза на 5 секунд, для явного ожидания элемента
            driver.manage().window().maximize();//Открытие сайта на весь экран

        }

        @AfterTest
        public void teamDown() {
            driver.quit();// Закрытие браузера
        }

        public static WebDriver setupDriver(String driverType) {
            switch (driverType) {
                case "CHROME":
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver();
                case "FIREFOX":
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
                case "OPERA":
                    WebDriverManager.operadriver().setup();
                    return new OperaDriver();
                default:
                    throw new WebDriverException("тип драйвера не верный");
            }
        }

}
