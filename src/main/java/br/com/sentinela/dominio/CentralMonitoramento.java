package br.com.sentinela.dominio;

import java.util.ArrayList;
import java.util.List;

public class CentralMonitoramento {
    private final DispositivoMonitoramento[] dispositivos;

    public CentralMonitoramento(DispositivoMonitoramento[] dispositivos) {
        if (dispositivos == null || dispositivos.length == 0) {
            throw new IllegalArgumentException("A central precisa de pelo menos um dispositivo");
        }
        this.dispositivos = dispositivos;
    }

    public List<AlertaSeguranca> analisarLeituras() {
        List<AlertaSeguranca> alertasGerados = new ArrayList<>();

        for (DispositivoMonitoramento dispositivo : dispositivos) {
            if (dispositivo instanceof SensorRadiacao sensorRadiacao) {
                analisarSensorRadiacao(sensorRadiacao, alertasGerados);
            } else if (dispositivo instanceof SensorPerimetral sensorPerimetral) {
                analisarSensorPerimetral(sensorPerimetral, alertasGerados);
            }
        }
        return alertasGerados;
    }

    private void analisarSensorRadiacao(SensorRadiacao sensorRadiacao, List<AlertaSeguranca> alertasGerados) {
        ZonaRestrita zonaMonitorada = sensorRadiacao.getZonaMonitorada();

        if (!sensorRadiacao.isEmOperacao()) {
            alertasGerados.add(new AlertaSeguranca(sensorRadiacao, zonaMonitorada,
                    "ATENCAO", "Sensor de radiação inoperante: zona sem monitoramento"));
            return;
        }

        String riscoAtual = zonaMonitorada.classificarRisco();
        if (!riscoAtual.equals("NORMAL")) {
            String mensagem = "Radiação de " + zonaMonitorada.getNivelRadiacaoAtual()
                    + " µSv/h (limite " + zonaMonitorada.getLimiteRadiacao() + " µSv/h)";
            alertasGerados.add(new AlertaSeguranca(sensorRadiacao, zonaMonitorada, riscoAtual, mensagem));
        }
    }

    private void analisarSensorPerimetral(SensorPerimetral sensorPerimetral, List<AlertaSeguranca> alertasGerados) {
        ZonaRestrita zonaProtegida = sensorPerimetral.getZonaProtegida();

        if (!sensorPerimetral.isEmOperacao()) {
            alertasGerados.add(new AlertaSeguranca(sensorPerimetral, zonaProtegida,
                    "ATENCAO", "Sensor perimetral inoperante: cerca sem vigilância"));
            return;
        }

        if (sensorPerimetral.isViolacaoDetectada()) {
            alertasGerados.add(new AlertaSeguranca(sensorPerimetral, zonaProtegida,
                    "ATENCAO", "Violação da cerca no setor " + sensorPerimetral.getSetorVigiado()
                    + ": drone enviado para verificação"));
            despacharDrone(zonaProtegida, sensorPerimetral, alertasGerados);
        }
    }

    private void despacharDrone(ZonaRestrita zonaInvadida, SensorPerimetral sensorQueDetectou,
                                List<AlertaSeguranca> alertasGerados) {
        DronePatrulha droneDisponivel = buscarDroneDisponivel();

        if (droneDisponivel == null) {
            alertasGerados.add(new AlertaSeguranca(sensorQueDetectou, zonaInvadida,
                    "CRITICO", "Nenhum drone disponível para verificar a violação"));
            return;
        }

        try {
            String deteccao = droneDisponivel.despacharPara(zonaInvadida);
            if (deteccao.equals(CameraVisaoComputacional.VEICULO_NAO_IDENTIFICADO)) {
                alertasGerados.add(new AlertaSeguranca(droneDisponivel, zonaInvadida,
                        "CRITICO", "Câmera do drone confirmou veículo não identificado"));
            }
        } catch (IllegalStateException erro) {
            alertasGerados.add(new AlertaSeguranca(droneDisponivel, zonaInvadida,
                    "CRITICO", "Drone não pôde decolar: " + erro.getMessage()));
        }
    }

    private DronePatrulha buscarDroneDisponivel() {
        for (DispositivoMonitoramento dispositivo : dispositivos) {
            if (dispositivo instanceof DronePatrulha drone && drone.isEmOperacao()) {
                return drone;
            }
        }
        return null;
    }

    public void imprimirAlertas(List<AlertaSeguranca> alertas) {
        if (alertas.isEmpty()) {
            System.out.println("Nenhum alerta: instalação em rotina normal");
            return;
        }
        for (AlertaSeguranca alerta : alertas) {
            System.out.println(alerta);
        }
    }
}