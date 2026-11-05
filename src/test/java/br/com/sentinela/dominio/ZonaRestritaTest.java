package br.com.sentinela.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZonaRestritaTest {

    private ZonaRestrita criarZonaComLimite(double limite) {
        ZonaRestrita zona = new ZonaRestrita();
        zona.setIdentificador("ZNR-01");
        zona.setDescricao("Casa do Reator");
        zona.setLimiteRadiacao(limite);
        return zona;
    }

    @Test
    void naoDeveSerNulaAposInstanciacao() {
        ZonaRestrita zona = new ZonaRestrita();
        assertNotNull(zona);
    }

    @Test
    void deveMudarDeNormalParaCriticoAoReceberLeituraAlta() {
        ZonaRestrita zona = criarZonaComLimite(2.5);
        zona.registrarLeituraRadiacao(0.2);
        assertEquals("NORMAL", zona.classificarRisco());

        zona.registrarLeituraRadiacao(4.7);

        assertEquals(4.7, zona.getNivelRadiacaoAtual());
        assertEquals("CRITICO", zona.classificarRisco());
    }

    @Test
    void deveClassificarComoAtencaoNaFaixaIntermediaria() {
        ZonaRestrita zona = criarZonaComLimite(2.5);

        zona.registrarLeituraRadiacao(1.5);

        assertEquals("ATENCAO", zona.classificarRisco());
    }

    @Test
    void deveRejeitarRadiacaoNegativaEManterUltimoValor() {
        ZonaRestrita zona = criarZonaComLimite(2.5);
        zona.registrarLeituraRadiacao(0.2);

        assertThrows(IllegalArgumentException.class, () -> zona.registrarLeituraRadiacao(-5));

        assertEquals(0.2, zona.getNivelRadiacaoAtual());
    }

    @Test
    void deveRejeitarLeituraAcimaDeUmMilhaoComoFalhaDeSensor() {
        ZonaRestrita zona = criarZonaComLimite(2.5);
        zona.registrarLeituraRadiacao(0.2);

        assertThrows(IllegalArgumentException.class, () -> zona.registrarLeituraRadiacao(1_000_001));

        assertEquals(0.2, zona.getNivelRadiacaoAtual());
    }
}