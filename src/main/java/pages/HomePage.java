package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private By checkBoxZeroKm = By.id("anunciosNovos");
    private By selecionarMarca = By.xpath("//*[@id=\"buscaForm\"]/div[2]/div[1]/div/div/div/div/button");
    private By selecionarModelo = By.xpath("//*[@id=\"buscaForm\"]/div[2]/div[2]/div/div/div/div/div/div/input");
    private By selecionarAnoMinimo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[1]/div/div/div/div/div/div/input");
    private By selecionarAnoMaximo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[2]/div/div/div/div/div/div/input");
    private By btAnoMaximo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[2]/div/div/div/div/button");
    private By btAnoMinimo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[1]/div/div/div/div/button");
    private By btValorMinimo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[3]/div/div/div/div/button");
    private By btValorMaximo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[4]/div/div/div/div/button");
    private By selecionarValorMinimo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[3]/div/div/div/div/div/div/input");
    private By selecionarValorMaximo = By.xpath("//*[@id=\"buscaForm\"]/div[3]/div[4]/div/div/div/div/div/div/input");
    private By btBuscar = By.xpath("//*[@id=\"buscaForm\"]/div[4]/div[2]/button");
    private By nomeCarro1 = By.cssSelector("#ac29506180 .esquerda");
    private By anuncios = By.cssSelector("div.badges-container");
    List<WebElement> listaProdutos = new ArrayList();
    private By nomeCarro2 = By.xpath("//*[@id=\"ac29562888\"]/div/a/h2");
    private By precoCarro1AVista = By.cssSelector("#ac29506180 .direita");
    private By precoCarro2AVista = By.cssSelector("#ac29562888 .direita");
    List<WebElement> dadosVeiculos = new ArrayList<>();
    List<String>dados = new ArrayList<>();

    public void obterListaAnoVeiculo(){
        dadosVeiculos = driver.findElements(By.xpath("//ul[@class='listahorizontal']//p"));

        for (WebElement e : dadosVeiculos){
            dados.add(e.getText());

        } System.out.println(dados);

    }


    public void desabilitarCheckBoxZeroKm() {
        if (driver.findElement(checkBoxZeroKm).isSelected()) driver.findElement(checkBoxZeroKm).click();
    }

    public void preencherMarca(String marca) {
        driver.findElement(selecionarMarca).sendKeys(marca);
        driver.findElement(By.xpath("//span[contains(.,'" + marca + "')]")).click();
    }
    public void preencherModelo(String modelo) {
        driver.findElement(selecionarModelo).sendKeys(modelo);
        driver.findElement(selecionarModelo).sendKeys(Keys.ENTER);
    }
    public void preencherAnoMinimo(String anoMinimo){
        driver.findElement(btAnoMinimo).click();
        driver.findElement(selecionarAnoMinimo).sendKeys(anoMinimo);
        driver.findElement(selecionarAnoMinimo).sendKeys(Keys.ENTER);
    }
    public void preencherAnoMaximo(String anoMaximo){
        driver.findElement(btAnoMaximo).click();
        driver.findElement(selecionarAnoMaximo).sendKeys(anoMaximo);
        driver.findElement(selecionarAnoMaximo).sendKeys(Keys.ENTER);
    }
    public void preencherValorMaximo(String valorMaximo){
        driver.findElement(btValorMaximo).click();
        driver.findElement(selecionarValorMaximo).sendKeys(valorMaximo);
        driver.findElement(selecionarValorMaximo).sendKeys(Keys.ENTER);
    }
    public void preencherValorMinimo(String valorMinimo){
        driver.findElement(btValorMinimo).click();
        driver.findElement(selecionarValorMinimo).sendKeys(valorMinimo);
        driver.findElement(selecionarValorMinimo).sendKeys(Keys.ENTER);
    }
    public void iniciarBusca(){
        driver.findElement(btBuscar).click();
    }
    private void carregarListaDeCarros() {
        listaProdutos= driver.findElements(anuncios);

    }
    public int contarProdutos() {
        carregarListaDeCarros();
        return listaProdutos.size();
    }
    public String obterNomeCarro1(){
        return driver.findElement(nomeCarro1).getText();
    }
    public String obterNomeCarro2(){
        return driver.findElement(nomeCarro2).getText();
    }
    public String obterPrecoCarro1(){
        String precoCarro1 = driver.findElement(precoCarro1AVista).getText();
        precoCarro1 = precoCarro1.replace("preço à vista","");
        precoCarro1 = precoCarro1.replace("R$","");
        return precoCarro1;
    }
    public String obterPrecoCarro2(){
       String precoCarro2 = driver.findElement(precoCarro2AVista).getText();
       precoCarro2 = precoCarro2.replace("preço à vista","");
       precoCarro2 = precoCarro2.replace("R$","");
       return precoCarro2;
    }

    public CarroUmPage clicarCarroUm() {
        driver.findElement(nomeCarro1).click();
        return new CarroUmPage(driver);
    }
    public CarroDoisPage clicarCarroDois() {
        driver.findElement(nomeCarro2).click();
        return new CarroDoisPage(driver);
    }

}
