package pages;

import base.BaseTests;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTests extends BaseTests {

    String marca = "Chevrolet";
    String modelo = "Celta";
    String anoMinimo = "2010";
    String anoMaximo = "2015";
    String valorMinimo = "10";
    String valorMaximo = "20";
    String cidade = "Campinas - SP";

    @Test
    public void testValidarListaBuscaDeCarrosUsados() {
        //--Pré-condição
        carregarPaginaInicial();

        //Preencher campos para efetuar a busca de carros usados
        homePage.desabilitarCheckBoxZeroKm();
        homePage.preencherMarca(marca);
        homePage.preencherModelo(modelo);

        homePage.preencherAnoMinimo(anoMinimo);
        homePage.preencherAnoMaximo(anoMaximo);

        homePage.preencherValorMinimo(valorMinimo);
        homePage.preencherValorMaximo(valorMaximo);
        homePage.preencherCidade(cidade);

        // Validar que esta sendo feito a busca apenas para carros usados
        assertTrue(homePage.verificarCheckBoxCarrosUsados());
        assertFalse(homePage.verificarCheckBoxCarrosNovos());

        //Clicar para iniciar busca
        homePage.iniciarBusca();
    }

    @Test
    public void testValidarBuscaComTresOuMaisResultados() {

        //--pré-condição
        testValidarListaBuscaDeCarrosUsados();

        //verificando quantidade de carros encontrados na busca
        homePage.contarProdutos();
        assertTrue(homePage.contarProdutos());


//        verificando se os primeiros 3 carros são da mesma marca e modelo
        assertThat(homePage.obterNomeCarro(0), Matchers.containsString(marca +" "+ modelo));
        assertThat(homePage.obterNomeCarro(1), Matchers.containsString(marca +" "+ modelo));
        assertThat(homePage.obterNomeCarro(2), Matchers.containsString(marca +" "+ modelo));
    }

    @Test
    public void testValidarDetalhesCarroUm_ModeloEValorIguais() {
        //--pré-condição
        testValidarListaBuscaDeCarrosUsados();

        String nomeCarro1_HomePage = homePage.obterNomeCarro(0);
        String precoCarro1_HomePage = homePage.obterPrecoCarro(0);
        CarroUmPage carroUmPage = homePage.clicarCarroUm(0);

        String nomeCarro_CarroUmPage = carroUmPage.obterNomeCarro1();
        String precoCarro_CarroUmPage = carroUmPage.obterPrecoCarro1();
        precoCarro_CarroUmPage = precoCarro_CarroUmPage.replace("R$", "");


        //validação do Primeiro Carro Modelo e Valor
        assertThat(nomeCarro_CarroUmPage, Matchers.containsString(nomeCarro1_HomePage));
        assertThat(precoCarro1_HomePage, Matchers.containsString(precoCarro_CarroUmPage));
    }

    @Test
    public void testValidarDetalhesCarroDois_ModeloEValorIguais() {

        //--pré-condição
        testValidarListaBuscaDeCarrosUsados();

        String nomeCarro2_HomePage = homePage.obterNomeCarro(1);
        String precoCarro2_HomePage = homePage.obterPrecoCarro(1);
        CarroDoisPage carroDoisPage = homePage.clicarCarroDois(1);

        String nomeCarro_CarroDoisPage = carroDoisPage.obterNomeCarro2();
        String precoCarro_CarroDoisPage = carroDoisPage.obterPrecoCarro2();
        precoCarro_CarroDoisPage = precoCarro_CarroDoisPage.replace("R$", "");

        //validação do Segundo Carro Modelo e Valor
        assertThat(nomeCarro_CarroDoisPage, Matchers.containsString(nomeCarro2_HomePage));
        assertThat(precoCarro2_HomePage, Matchers.containsString(precoCarro_CarroDoisPage));
    }

    @Test
    public void testeListaDadosVeiculos() throws Exception {
        //--pré-condição
        testValidarListaBuscaDeCarrosUsados();
        //Gerando Lista de MARCA/MODELO, ANO , KM, COR, CAMBIO
        homePage.obterListaAnoVeiculo();
        File arquivo = new File("carros.csv");
        //Verificando se arquivo foi criado
        assertTrue(arquivo.exists());
    }
}

