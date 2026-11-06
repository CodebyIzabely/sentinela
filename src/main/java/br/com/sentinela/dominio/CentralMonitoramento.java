package br.com.sentinela.dominio;

import java.util.ArrayList;
import java.util.List;

public class CentralMonitoramento {
    private final Sensor[] sensores;

    public CentralMonitoramento(Sensor[] sensores) {
        if (sensores == null || sensores.length == 0) {
            throw new IllegalArgumentException("A central precisa de pelo menos um sensor");
        }
        this.sensores = sensores;
    }

    public List<AlertaSeguranca> analisarLeituras() {
        List<AlertaSeguranca> alertasGerados = new ArrayList<>();

        for (Sensor sensorAnalisado : sensores) {
            ZonaRestrita zonaMonitorada = sensorAnalisado.getZonaMonitorada();

            if (!sensorAnalisado.isEmOperacao()) {
                alertasGerados.add(new AlertaSeguranca(sensorAnalisado, zonaMonitorada,
                        "ATENCAO", "Sensor inoperante: a zona está sem monitoramento"));
                continue;
            }

            String riscoAtual = zonaMonitorada.classificarRisco();
            if (!riscoAtual.equals("NORMAL")) {
                String mensagem = "Radiação de " + zonaMonitorada.getNivelRadiacaoAtual()
                        + " µSv/h (limite " + zonaMonitorada.getLimiteRadiacao() + " µSv/h)";
                alertasGerados.add(new AlertaSeguranca(sensorAnalisado, zonaMonitorada, riscoAtual, mensagem));
            }
        }
        return alertasGerados;
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