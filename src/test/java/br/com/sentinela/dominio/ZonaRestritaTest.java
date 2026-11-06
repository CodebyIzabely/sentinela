package br.com.sentinela.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZonaRestritaTest {

    private ZonaRestrita criarCasaDoReator() {
        return new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);
    }

    @Test
    void naoDeveSerNulaAposInstanciacao() {
        ZonaRestrita zona = criarCasaDoReator();
        assertNotNull(zona);
    }

    @Test
    void deveMudarDeNormalParaCriticoAoReceberLeituraAlta() {
        ZonaRestrita zona = criarCasaDoReator();
        zona.registrarLeituraRadiacao(0.2);
        assertEquals("NORMAL", zona.classificarRisco());

        zona.registrarLeituraRadiacao(4.7);

        assertEquals(4.7, zona.getNivelRadiacaoAtual());
        assertEquals("CRITICO", zona.classificarRisco());
    }

    @Test
    void deveClassificarComoAtencaoNaFaixaIntermediaria() {
        ZonaRestrita zona = criarCasaDoReator();

        zona.registrarLeituraRadiacao(1.5);

        assertEquals("ATENCAO", zona.classificarRisco());
    }

    @Test
    void deveRejeitarRadiacaoNegativaEManterUltimoValor() {
        ZonaRestrita zona = criarCasaDoReator();
        zona.registrarLeituraRadiacao(0.2);

        assertThrows(IllegalArgumentException.class, () -> zona.registrarLeituraRadiacao(-5));

        assertEquals(0.2, zona.getNivelRadiacaoAtual());
    }

    @Test
    void deveRejeitarLeituraAcimaDeUmMilhaoComoFalhaDeSensor() {
        ZonaRestrita zona = criarCasaDoReator();
        zona.registrarLeituraRadiacao(0.2);

        assertThrows(IllegalArgumentException.class, () -> zona.registrarLeituraRadiacao(1_000_001));

        assertEquals(0.2, zona.getNivelRadiacaoAtual());
    }

    @Test
    void deveRejeitarIdentificadorVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new ZonaRestrita("", "Casa do Reator", 2.5));
    }

    @Test
    void deveRejeitarDescricaoVazia() {
        assertThrows(IllegalArgumentException.class,
                () -> new ZonaRestrita("ZNR-01", "", 2.5));
    }

    @Test
    void deveRejeitarLimiteZeroOuNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ZonaRestrita("ZNR-01", "Casa do Reator", 0));
    }

    @Test
    void deveNascerComRadiacaoZerada() {
        ZonaRestrita zona = criarCasaDoReator();
        assertEquals(0.0, zona.getNivelRadiacaoAtual());
    }
}