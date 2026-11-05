package br.com.sentinela.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZonaRestritaTest {

    @Test
    void naoDeveSerNulaAposInstanciacao() {
        ZonaRestrita zona = new ZonaRestrita();
        assertNotNull(zona);
    }

    @Test
    void deveMudarDeNormalParaCriticoAoReceberLeituraAlta() {
        ZonaRestrita zona = new ZonaRestrita();
        zona.limiteRadiacao = 2.5;
        zona.registrarLeituraRadiacao(0.2);
        assertEquals("NORMAL", zona.classificarRisco());

        zona.registrarLeituraRadiacao(4.7);

        assertEquals(4.7, zona.nivelRadiacaoAtual);
        assertEquals("CRITICO", zona.classificarRisco());
    }

    @Test
    void deveClassificarComoAtencaoNaFaixaIntermediaria() {
        ZonaRestrita zona = new ZonaRestrita();
        zona.limiteRadiacao = 2.5;

        zona.registrarLeituraRadiacao(1.5);

        assertEquals("ATENCAO", zona.classificarRisco());
    }
}