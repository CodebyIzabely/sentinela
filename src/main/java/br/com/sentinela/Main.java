package br.com.sentinela;

import br.com.sentinela.dominio.AlertaSeguranca;
import br.com.sentinela.dominio.CameraVisaoComputacional;
import br.com.sentinela.dominio.CentralMonitoramento;
import br.com.sentinela.dominio.DispositivoMonitoramento;
import br.com.sentinela.dominio.DronePatrulha;
import br.com.sentinela.dominio.SensorPerimetral;
import br.com.sentinela.dominio.SensorRadiacao;
import br.com.sentinela.dominio.ZonaRestrita;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ===== Montagem da instalação =====
        ZonaRestrita casaDoReator = new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);
        ZonaRestrita patioCombustivelIrradiado = new ZonaRestrita(
                "ZNR-02", "Pátio de Armazenamento de Combustível Irradiado", 2.5);

        // ZonaRestrita zonaFantasma = new ZonaRestrita(); // não compila: não existe construtor padrão

        SensorRadiacao sensorDoReator = new SensorRadiacao("SRD-01", "Teto da Casa do Reator", casaDoReator);
        SensorRadiacao sensorDoPatio = new SensorRadiacao("SRD-02", "Entrada do Pátio", patioCombustivelIrradiado);
        SensorPerimetral sensorCercaNorte = new SensorPerimetral("SPM-01", "Norte", patioCombustivelIrradiado);
        DronePatrulha droneDePatrulha = new DronePatrulha("DRN-01", "Perímetro Norte -> Pátio -> Casa do Reator");

        CentralMonitoramento central = new CentralMonitoramento(new DispositivoMonitoramento[]{
                sensorDoReator, sensorDoPatio, sensorCercaNorte, droneDePatrulha});

        // ===== Cenário 1 =====
        System.out.println("=== CENÁRIO 1: ROTINA NORMAL ===");
        sensorDoReator.registrarLeitura(0.2);
        sensorDoPatio.registrarLeitura(0.3);
        imprimirLeitura(sensorDoReator);
        imprimirLeitura(sensorDoPatio);
        System.out.println("Perímetro " + sensorCercaNorte.getLocalizacao() + ": "
                + (sensorCercaNorte.isViolacaoDetectada() ? "VIOLADO" : "íntegro"));
        String deteccaoNaPatrulha = droneDePatrulha.patrulhar();
        System.out.println("Drone " + droneDePatrulha.getCodigoIdentificador() + " em patrulha ("
                + droneDePatrulha.getRotaPatrulha() + "): " + deteccaoNaPatrulha
                + " | bateria " + droneDePatrulha.getBateriaPercentual() + "%");
        imprimirAlertasDaCentral(central);

        // ===== Cenário 2 =====
        System.out.println();
        System.out.println("=== CENÁRIO 2: PICO DE RADIAÇÃO ===");
        sensorDoReator.registrarLeitura(4.7);
        imprimirLeitura(sensorDoReator);
        imprimirAlertasDaCentral(central);
        sensorDoReator.registrarLeitura(0.2); // situação controlada antes do próximo cenário

        // ===== Cenário 3 =====
        System.out.println();
        System.out.println("=== CENÁRIO 3: ACESSO INDEVIDO ===");
        sensorCercaNorte.registrarViolacaoCerca();
        System.out.println("Perímetro " + sensorCercaNorte.getLocalizacao() + ": VIOLADO");
        droneDePatrulha.getCamera().programarProximaDeteccao(CameraVisaoComputacional.VEICULO_NAO_IDENTIFICADO);
        imprimirAlertasDaCentral(central);
        System.out.println("Drone " + droneDePatrulha.getCodigoIdentificador() + " na zona "
                + droneDePatrulha.getZonaAtual().getIdentificador()
                + " | bateria restante " + droneDePatrulha.getBateriaPercentual() + "%");
    }

    private static void imprimirLeitura(SensorRadiacao sensor) {
        ZonaRestrita zona = sensor.getZonaMonitorada();
        System.out.println("Leitura " + sensor.getCodigoIdentificador()
                + " | " + zona.getIdentificador() + " - " + zona.getDescricao()
                + " | " + String.format("%.2f µSv/h", sensor.getLeituraAtual())
                + " | Risco: " + zona.classificarRisco());
    }

    private static void imprimirAlertasDaCentral(CentralMonitoramento central) {
        System.out.println("--- Análise da central ---");
        List<AlertaSeguranca> alertas = central.analisarLeituras();
        central.imprimirAlertas(alertas);
    }
}