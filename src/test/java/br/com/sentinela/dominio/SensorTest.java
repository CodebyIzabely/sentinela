package br.com.sentinela.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SensorTest {

    private final ZonaRestrita casaDoReator = new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);

    @Test
    void deveNascerEmOperacao() {
        Sensor sensor = new Sensor("SRD-01", "Teto da Casa do Reator", casaDoReator);
        assertTrue(sensor.isEmOperacao());
    }

    @Test
    void deveGuardarOMesmoObjetoDaZonaMonitorada() {
        Sensor sensor = new Sensor("SRD-01", "Teto da Casa do Reator", casaDoReator);
        assertSame(casaDoReator, sensor.getZonaMonitorada());
    }

    @Test
    void deveRejeitarCodigoVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sensor("", "Teto da Casa do Reator", casaDoReator));
    }

    @Test
    void deveRejeitarSensorSemZona() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sensor("SRD-01", "Teto da Casa do Reator", null));
    }
}