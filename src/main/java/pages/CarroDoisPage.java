package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarroDoisPage {
    private WebDriver driver;

    private By nomeCarro = By.xpath("//*[@id=\"ctdoTopo\"]/h1");
    private By precoCarro = By.cssSelector("#cardProposta > div > h2");

    public String obterNomeCarro2(){
        return driver.findElement(nomeCarro).getText();
    }
    public String obterPrecoCarro2(){
        return driver.findElement(precoCarro).getText();
    }

    public CarroDoisPage(WebDriver driver) {
        this.driver = driver;
    }
}
