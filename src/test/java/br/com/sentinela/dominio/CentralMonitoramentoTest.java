package br.com.sentinela.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CentralMonitoramentoTest {

    private ZonaRestrita casaDoReator;
    private ZonaRestrita patioCombustivelIrradiado;
    private SensorRadiacao sensorDoReator;
    private SensorPerimetral sensorCercaNorte;
    private DronePatrulha droneDePatrulha;
    private CentralMonitoramento central;

    @BeforeEach
    void montarCenario() {
        casaDoReator = new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);
        patioCombustivelIrradiado = new ZonaRestrita("ZNR-02", "Pátio de Combustível Irradiado", 2.5);
        sensorDoReator = new SensorRadiacao("SRD-01", "Teto da Casa do Reator", casaDoReator);
        sensorCercaNorte = new SensorPerimetral("SPM-01", "Norte", patioCombustivelIrradiado);
        droneDePatrulha = new DronePatrulha("DRN-01", "Perímetro Norte");
        central = new CentralMonitoramento(new DispositivoMonitoramento[]{
                sensorDoReator, sensorCercaNorte, droneDePatrulha});
    }

    @Test
    void naoDeveGerarAlertaEmRotinaNormal() {
        sensorDoReator.registrarLeitura(0.2);

        assertTrue(central.analisarLeituras().isEmpty());
    }

    @Test
    void deveGerarAlertaCriticoNoPicoDeRadiacao() {
        sensorDoReator.registrarLeitura(4.7);

        List<AlertaSeguranca> alertas = central.analisarLeituras();

        assertEquals(1, alertas.size());
        assertEquals("CRITICO", alertas.get(0).getSeveridade());
    }

    @Test
    void alertaDeveGuardarOMesmoObjetoSensorEZona() {
        sensorDoReator.registrarLeitura(4.7);

        AlertaSeguranca alerta = central.analisarLeituras().get(0);

        assertSame(sensorDoReator, alerta.getSensorOrigem());
        assertSame(casaDoReator, alerta.getZonaAfetada());
    }

    @Test
    void deveGerarAlertaDeAtencaoParaSensorInoperante() {
        sensorDoReator.desativar();

        List<AlertaSeguranca> alertas = central.analisarLeituras();

        assertEquals(1, alertas.size());
        assertEquals("ATENCAO", alertas.get(0).getSeveridade());
    }

    @Test
    void violacaoComVeiculoDeveGerarAtencaoECriticoDoDrone() {
        sensorCercaNorte.registrarViolacaoCerca();
        droneDePatrulha.getCamera().programarProximaDeteccao(CameraVisaoComputacional.VEICULO_NAO_IDENTIFICADO);

        List<AlertaSeguranca> alertas = central.analisarLeituras();

        assertEquals(2, alertas.size());
        assertEquals("ATENCAO", alertas.get(0).getSeveridade());
        assertSame(sensorCercaNorte, alertas.get(0).getSensorOrigem());
        assertEquals("CRITICO", alertas.get(1).getSeveridade());
        assertSame(droneDePatrulha, alertas.get(1).getSensorOrigem());
    }

    @Test
    void violacaoSemVeiculoDeveGerarApenasAtencao() {
        sensorCercaNorte.registrarViolacaoCerca();

        List<AlertaSeguranca> alertas = central.analisarLeituras();

        assertEquals(1, alertas.size());
        assertEquals("ATENCAO", alertas.get(0).getSeveridade());
    }

    @Test
    void deveRejeitarCentralSemDispositivos() {
        assertThrows(IllegalArgumentException.class,
                () -> new CentralMonitoramento(new DispositivoMonitoramento[]{}));
    }
}