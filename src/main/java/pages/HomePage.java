package pages;

import com.opencsv.CSVWriter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    List<WebElement> listaCarros = new ArrayList();
    List<WebElement> listaPrecos = new ArrayList();
    private By precoCarroAVista = By.cssSelector("div.false h3");
    private By precoCarro = By.xpath("//*[@id=\"anunciosForm\"]/ul/li/div/a/h3");
    private By campoCidade = By.id("cidade");
    private By btSelecionarCidade = By.xpath("//div/span/a");
    List<WebElement> dadosVeiculos = new ArrayList<>();
    List<String> dados = new ArrayList<>();

    public void obterListaAnoVeiculo() throws IOException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        dadosVeiculos = driver.findElements(By.xpath("//ul[@class='listahorizontal']//p"));

        for (WebElement e : dadosVeiculos)
            dados.add(e.getText());

        List<String[]> linhas = new ArrayList<>();
        String[] cabecalho = {"MARCA/MODELO", "PREÇO", "Ano", "KM", "COR", "Cambio"};
        List<String> marcaModelo = new ArrayList<>();
        System.out.println(listaPrecoCarros().size());
        System.out.println(listaCarros().size());
        int j = 0;
        for (int i = 0; i < (dados.size() - 2); i += 4) {
            if (j < listaCarros().size()) {
                marcaModelo.add(listaCarros().get(j));
                marcaModelo.add(listaPrecoCarros().get(j));
            }
            marcaModelo.add(dados.get(i));
            marcaModelo.add(dados.get(i + 1));
            marcaModelo.add(dados.get(i + 2));
            marcaModelo.add(dados.get(i + 3));
            j++;
        }
        for (String s : marcaModelo) {
            System.out.println(s);
        }

        for (int i = 0; i < (marcaModelo.size() - 2); i = i + 6) {
            linhas.add(new String[]{marcaModelo.get(i), marcaModelo.get(i + 1),
                    marcaModelo.get(i + 2), marcaModelo.get(i + 3), marcaModelo.get(i + 4),marcaModelo.get(i + 5)});

        }

        Writer writer = Files.newBufferedWriter(Paths.get("carros.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeNext(cabecalho);
        csvWriter.writeAll(linhas);

        csvWriter.flush();
        writer.close();

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

    public Boolean verificarCheckBoxCarrosUsados() {
        Boolean carroUsado = driver.findElement(checkBoxCarrosUsados).isSelected();
        return carroUsado;
    }

    public Boolean verificarCheckBoxCarrosNovos() {
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

    private void carregarListaDeCarros() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        listaCarros = driver.findElements(anuncios);

    }

    private void carregarListaDePrecoCarros() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        listaPrecos = driver.findElements(precoCarro);

    }

    public Boolean contarProdutos() {
        Boolean qtdeListaVeiculos = false;
        carregarListaDeCarros();
        if (listaCarros.size() >= 3) qtdeListaVeiculos = true;
        return qtdeListaVeiculos;
    }

    public List<String> listaCarros() {
        carregarListaDeCarros();
        List<String> carros = new ArrayList<>();
        for (WebElement e : listaCarros) {
            carros.add(e.getText());
        }
        return carros;
    }

    public List<String> listaPrecoCarros() {
        carregarListaDePrecoCarros();
        List<String> precoCarros = new ArrayList<>();
        String texto = "";
        for (WebElement e : listaPrecos) {
            texto = (e.getText().replace("preço à vista", ""));
            precoCarros.add(texto);

        }
        return precoCarros;
    }

    public String obterNomeCarro(int indice) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.findElements(anuncios).get(indice).getText();
    }

    public String obterPrecoCarro(int indice) {
        String precoCarro = driver.findElements(precoCarroAVista).get(indice).getText();
        precoCarro = precoCarro.replace("preço à vista", "");
        precoCarro = precoCarro.replace("R$", "");
        return precoCarro;
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
