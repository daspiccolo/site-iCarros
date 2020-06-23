package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;

public class BaseTests {
    private static WebDriver driver;

    protected HomePage homePage;


    @BeforeAll
    public static void inicializar() {
//        WebDriverManager.firefoxdriver().operatingSystem(OperatingSystem.LINUX).setup();
        WebDriverManager.chromedriver().operatingSystem(OperatingSystem.LINUX).setup();
//        driver = new FirefoxDriver();
//        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver" );
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void carregarPaginaInicial() {
        driver.get("https://www.icarros.com.br");
        homePage = new HomePage(driver);

    }

   @After
    public void finalizar() {
        driver.quit();
    }
}
