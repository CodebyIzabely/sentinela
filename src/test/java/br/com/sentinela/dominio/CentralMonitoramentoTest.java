package br.com.sentinela.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CentralMonitoramentoTest {

    private ZonaRestrita casaDoReator;
    private Sensor sensorDoReator;
    private CentralMonitoramento central;

    @BeforeEach
    void montarCenario() {
        casaDoReator = new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);
        sensorDoReator = new Sensor("SRD-01", "Teto da Casa do Reator", casaDoReator);
        central = new CentralMonitoramento(new Sensor[]{sensorDoReator});
    }

    @Test
    void naoDeveGerarAlertaEmRotinaNormal() {
        casaDoReator.registrarLeituraRadiacao(0.2);

        List<AlertaSeguranca> alertas = central.analisarLeituras();

        assertTrue(alertas.isEmpty());
    }

    @Test
    void deveGerarAlertaCriticoNoPicoDeRadiacao() {
        casaDoReator.registrarLeituraRadiacao(4.7);

        List<AlertaSeguranca> alertas = central.analisarLeituras();

        assertEquals(1, alertas.size());
        assertEquals("CRITICO", alertas.get(0).getSeveridade());
    }

    @Test
    void alertaDeveGuardarOMesmoObjetoSensorEZona() {
        casaDoReator.registrarLeituraRadiacao(4.7);

        AlertaSeguranca alerta = central.analisarLeituras().get(0);

        assertSame(sensorDoReator, alerta.getSensorOrigem());
        assertSame(casaDoReator, alerta.getZonaAfetada());
    }

    @Test
    void deveGerarAlertaDeAtencaoParaSensorInoperante() {
        casaDoReator.registrarLeituraRadiacao(0.2);
        sensorDoReator.desativar();

        List<AlertaSeguranca> alertas = central.analisarLeituras();

        assertEquals(1, alertas.size());
        assertEquals("ATENCAO", alertas.get(0).getSeveridade());
    }

    @Test
    void deveRejeitarCentralSemSensores() {
        assertThrows(IllegalArgumentException.class,
                () -> new CentralMonitoramento(new Sensor[]{}));
    }
}