package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private By checkBoxCarrosNovos = By.id("anunciosNovos");
    private By checkBoxCarrosUsados = By.id("anunciosUsados");
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
    private By anuncios = By.cssSelector("h2.esquerda.titulo_anuncio");
    List<WebElement> listaProdutos = new ArrayList();
    private By precoCarroAVista = By.cssSelector("div.false h3");
    private By campoCidade = By.id("cidade");
    private By btSelecionarCidade = By.xpath("//div/span/a");
    List<WebElement> dadosVeiculos = new ArrayList<>();
    List<String> dados = new ArrayList<>();

    public void obterListaAnoVeiculo() {
        dadosVeiculos = driver.findElements(By.xpath("//ul[@class='listahorizontal']//p"));
        List<String> marcaModelo = listaCarros();

        for (WebElement e : dadosVeiculos) {
            dados.add(e.getText());

        }
        System.out.println(dados);
        for (String s : listaCarros()){
            System.out.println(s);
        }
    }


    public void preencherCidade(String cidade) {
        driver.findElement(btSelecionarCidade).click();
        driver.findElement(campoCidade).click();
        driver.findElement(campoCidade).sendKeys(cidade);
        FluentWait wait = new FluentWait(driver)
                .withTimeout(ofSeconds(5))
                .pollingEvery(ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(.,'" + cidade + "')]")));
        driver.findElement(By.xpath("//span[contains(.,'" + cidade + "')]")).click();
    }

    public void desabilitarCheckBoxZeroKm() {
        if (driver.findElement(checkBoxCarrosNovos).isSelected()) driver.findElement(checkBoxCarrosNovos).click();
    }
    public Boolean verificarCheckBoxCarrosUsados(){
        Boolean carroUsado = driver.findElement(checkBoxCarrosUsados).isSelected();
        return carroUsado;
    }
    public Boolean verificarCheckBoxCarrosNovos(){
        Boolean carroNovo = driver.findElement(checkBoxCarrosNovos).isSelected();
        return carroNovo;
    }

    public void preencherMarca(String marca) {
        driver.findElement(selecionarMarca).sendKeys(marca);
        driver.findElement(By.xpath("//span[contains(.,'" + marca + "')]")).click();
    }

    public void preencherModelo(String modelo) {
        driver.findElement(selecionarModelo).sendKeys(modelo);
        driver.findElement(selecionarModelo).sendKeys(Keys.ENTER);
    }

    public void preencherAnoMinimo(String anoMinimo) {
        driver.findElement(btAnoMinimo).click();
        driver.findElement(selecionarAnoMinimo).sendKeys(anoMinimo);
        driver.findElement(selecionarAnoMinimo).sendKeys(Keys.ENTER);
    }

    public void preencherAnoMaximo(String anoMaximo) {
        driver.findElement(btAnoMaximo).click();
        driver.findElement(selecionarAnoMaximo).sendKeys(anoMaximo);
        driver.findElement(selecionarAnoMaximo).sendKeys(Keys.ENTER);
    }

    public void preencherValorMaximo(String valorMaximo) {
        driver.findElement(btValorMaximo).click();
        driver.findElement(selecionarValorMaximo).sendKeys(valorMaximo);
        driver.findElement(selecionarValorMaximo).sendKeys(Keys.ENTER);
    }

    public void preencherValorMinimo(String valorMinimo) {
        driver.findElement(btValorMinimo).click();
        driver.findElement(selecionarValorMinimo).sendKeys(valorMinimo);
        driver.findElement(selecionarValorMinimo).sendKeys(Keys.ENTER);
    }

    public void iniciarBusca() {
        driver.findElement(btBuscar).click();
    }

    private void carregarListaDeCarros()  {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        listaProdutos = driver.findElements(anuncios);

    }

    public Boolean contarProdutos()  {
        Boolean qtdeListaVeiculos = false;
        carregarListaDeCarros();
        if (listaProdutos.size() >=3 ) qtdeListaVeiculos = true;
        return qtdeListaVeiculos;
    }
    public List<String> listaCarros(){
        carregarListaDeCarros();
        List<String> carros = new ArrayList<>();
        for(WebElement e : listaProdutos){
            carros.add(e.getText());
        }
        return carros;
    }

    public String obterNomeCarro1() {
        return listaCarros().get(0);
    }

    public String obterNomeCarro2() {
         return listaCarros().get(1);
    }
    public String obterNomeCarro3() {
        return listaCarros().get(2);
    }

    public String obterPrecoCarro1(int indice) {
        String precoCarro1 = driver.findElements(precoCarroAVista).get(indice).getText();
        precoCarro1 = precoCarro1.replace("preço à vista", "");
        precoCarro1 = precoCarro1.replace("R$", "");
        return precoCarro1;
    }

    public String obterPrecoCarro2(int indice) {
        String precoCarro2 = driver.findElements(precoCarroAVista).get(indice).getText();
        precoCarro2 = precoCarro2.replace("preço à vista", "");
        precoCarro2 = precoCarro2.replace("R$", "");
        return precoCarro2;
    }

    public CarroUmPage clicarCarroUm(int indice) {
        driver.findElements(anuncios).get(indice).click();
        return new CarroUmPage(driver);
    }

    public CarroDoisPage clicarCarroDois(int indice) {
        driver.findElements(anuncios).get(indice).click();
        return new CarroDoisPage(driver);
    }

}
