package br.com.sentinela.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZonaRestritaTest {

    @Test
    void naoDeveSerNulaAposInstanciacao() {
        ZonaRestrita zona = new ZonaRestrita();
        assertNotNull(zona);
    }
}