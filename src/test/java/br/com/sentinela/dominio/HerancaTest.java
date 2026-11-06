package br.com.sentinela.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HerancaTest {

    private final ZonaRestrita casaDoReator = new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);

    @Test
    void todasAsFilhasHerdamGetCodigoIdentificador() {
        SensorRadiacao sensorRadiacao = new SensorRadiacao("SRD-01", "Teto da Casa do Reator", casaDoReator);
        SensorPerimetral sensorPerimetral = new SensorPerimetral("SPM-01", "Norte", casaDoReator);
        DronePatrulha drone = new DronePatrulha("DRN-01", "Perímetro Norte");

        assertEquals("SRD-01", sensorRadiacao.getCodigoIdentificador());
        assertEquals("SPM-01", sensorPerimetral.getCodigoIdentificador());
        assertEquals("DRN-01", drone.getCodigoIdentificador());
    }

    @Test
    void droneEhDispositivoMasNaoEhSensorRadiacao() {
        DispositivoMonitoramento dispositivo = new DronePatrulha("DRN-01", "Perímetro Norte");

        assertTrue(dispositivo instanceof DispositivoMonitoramento);
        assertFalse(dispositivo instanceof SensorRadiacao);
    }

    @Test
    void droneTemUmaCamera() {
        DronePatrulha drone = new DronePatrulha("DRN-01", "Perímetro Norte");
        assertNotNull(drone.getCamera());
    }

    @Test
    void droneDeveConsumirBateriaAoSerDespachado() {
        DronePatrulha drone = new DronePatrulha("DRN-01", "Perímetro Norte");

        drone.despacharPara(casaDoReator);

        assertEquals(85, drone.getBateriaPercentual());
        assertSame(casaDoReator, drone.getZonaAtual());
    }

    @Test
    void droneComBateriaBaixaNaoDeveDecolar() {
        DronePatrulha drone = new DronePatrulha("DRN-01", "Perímetro Norte");
        for (int missao = 0; missao < 6; missao++) {
            drone.patrulhar(); // 100 -> 10%
        }

        assertThrows(IllegalStateException.class, () -> drone.despacharPara(casaDoReator));
    }

    @Test
    void sensorRadiacaoUsaOLimiteDaZona() {
        SensorRadiacao sensor = new SensorRadiacao("SRD-01", "Teto da Casa do Reator", casaDoReator);
        assertEquals(2.5, sensor.getLimiteSeguranca());
    }

    @Test
    void deveRejeitarCodigoVazioNaFilha() {
        assertThrows(IllegalArgumentException.class,
                () -> new SensorRadiacao("", "Teto da Casa do Reator", casaDoReator));
    }
}