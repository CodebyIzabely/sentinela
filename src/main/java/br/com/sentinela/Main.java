package br.com.sentinela;

import br.com.sentinela.dominio.AlertaSeguranca;
import br.com.sentinela.dominio.CentralMonitoramento;
import br.com.sentinela.dominio.Sensor;
import br.com.sentinela.dominio.ZonaRestrita;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ZonaRestrita casaDoReator = new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);
        ZonaRestrita patioCombustivelIrradiado = new ZonaRestrita(
                "ZNR-02", "Pátio de Armazenamento de Combustível Irradiado", 2.5);

        // ZonaRestrita zonaFantasma = new ZonaRestrita(); // não compila: não existe construtor padrão

        Sensor sensorDoReator = new Sensor("SRD-01", "Teto da Casa do Reator", casaDoReator);
        Sensor sensorDoPatio = new Sensor("SRD-02", "Entrada do Pátio", patioCombustivelIrradiado);

        CentralMonitoramento central = new CentralMonitoramento(
                new Sensor[]{sensorDoReator, sensorDoPatio});

        System.out.println("=== ROTINA NORMAL ===");
        casaDoReator.registrarLeituraRadiacao(0.2);
        patioCombustivelIrradiado.registrarLeituraRadiacao(0.3);
        imprimirSituacao(casaDoReator);
        imprimirSituacao(patioCombustivelIrradiado);
        List<AlertaSeguranca> alertas = central.analisarLeituras();
        central.imprimirAlertas(alertas);

        System.out.println();
        System.out.println("=== PICO DE RADIAÇÃO ===");
        casaDoReator.registrarLeituraRadiacao(4.7);
        imprimirSituacao(casaDoReator);
        alertas = central.analisarLeituras();
        central.imprimirAlertas(alertas);

        System.out.println();
        System.out.println("=== SENSOR EM MANUTENÇÃO ===");
        casaDoReator.registrarLeituraRadiacao(0.2);
        sensorDoPatio.desativar();
        alertas = central.analisarLeituras();
        central.imprimirAlertas(alertas);
    }

    private static void imprimirSituacao(ZonaRestrita zona) {
        System.out.println("Leitura: " + zona.getIdentificador() + " - " + zona.getDescricao()
                + " | Radiação: " + zona.getNivelRadiacaoAtual() + " µSv/h"
                + " | Risco: " + zona.classificarRisco());
    }
}