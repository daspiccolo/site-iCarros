package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarroUmPage {
    private WebDriver driver;
    private By nomeCarro = By.xpath("//*[@id=\"ctdoTopo\"]/h1");
    private By precoCarro = By.cssSelector("#cardProposta > div > h2");

    public String obterNomeCarro1(){
        return driver.findElement(nomeCarro).getText();
    }
    public String obterPrecoCarro1(){
        return driver.findElement(precoCarro).getText();
    }

    public CarroUmPage(WebDriver driver) {
        this.driver = driver;
    }
}
