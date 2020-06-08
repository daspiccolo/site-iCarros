package pages;

import base.BaseTests;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class HomePageTests extends BaseTests {

    @Test
    public void testValidarListaDeCarrosMesmaMarcaEModelo() {
        //--Pré-condição
        carregarPaginaInicial();

        //Preencher campos para efetuar a busca de carros usados
        homePage.desabilitarCheckBoxZeroKm();
        homePage.preencherMarca("Chevrolet");
        homePage.preencherModelo("Celta");

        homePage.preencherAnoMinimo("2010");
        homePage.preencherAnoMaximo("2015");

        homePage.preencherValorMinimo("10");
        homePage.preencherValorMaximo("16");

        //Clicar para iniciar busca
        homePage.iniciarBusca();
    }

    @Test
    public void testValidarBuscaComTresOuMaisResultados() {
        //--pré-condição
        testValidarListaDeCarrosMesmaMarcaEModelo();
        //verificando quantidade de carros encontrados na busca
        assertThat(homePage.contarProdutos(), Matchers.is(9));
    }

    @Test
    public void testValidarDetalhesCarroUm_ModeloEValorIguais() {
        //--pré-condição
        testValidarBuscaComTresOuMaisResultados();

        String nomeCarro1_HomePage = homePage.obterNomeCarro1();
        String precoCarro1_HomePage = homePage.obterPrecoCarro1();
        CarroUmPage carroUmPage = homePage.clicarCarroUm();

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
        testValidarBuscaComTresOuMaisResultados();

        String nomeCarro2_HomePage = homePage.obterNomeCarro2();
        String precoCarro2_HomePage = homePage.obterPrecoCarro2();
        CarroDoisPage carroDoisPage = homePage.clicarCarroDois();

        String nomeCarro_CarroDoisPage = carroDoisPage.obterNomeCarro2();
        String precoCarro_CarroDoisPage = carroDoisPage.obterPrecoCarro2();
        precoCarro_CarroDoisPage = precoCarro_CarroDoisPage.replace("R$", "");

        //validação do Segundo Carro Modelo e Valor
        assertThat(nomeCarro_CarroDoisPage, Matchers.containsString(nomeCarro2_HomePage));
        assertThat(precoCarro2_HomePage, Matchers.containsString(precoCarro_CarroDoisPage));
    }

    @Test
    public void testeListaDadosVeiculos() {
        //--pré-condição
        testValidarBuscaComTresOuMaisResultados();
        //Gerando Lista de ANO , KM, COR, CAMBIO
        homePage.obterListaAnoVeiculo();
    }
}

