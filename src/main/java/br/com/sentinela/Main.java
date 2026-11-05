package br.com.sentinela;

import br.com.sentinela.dominio.ZonaRestrita;

public class Main {
    public static void main(String[] args) {
        ZonaRestrita casaDoReator = new ZonaRestrita();
        casaDoReator.identificador = "ZNR-01";
        casaDoReator.descricao = "Casa do Reator";
        casaDoReator.limiteRadiacao = 2.5;

        ZonaRestrita patioCombustivelIrradiado = new ZonaRestrita();
        patioCombustivelIrradiado.identificador = "ZNR-02";
        patioCombustivelIrradiado.descricao = "Pátio de Armazenamento de Combustível Irradiado";
        patioCombustivelIrradiado.limiteRadiacao = 2.5;

        casaDoReator.registrarLeituraRadiacao(0.2);
        patioCombustivelIrradiado.registrarLeituraRadiacao(0.3);
        imprimirSituacao(casaDoReator);
        imprimirSituacao(patioCombustivelIrradiado);

        System.out.println("--- Nova leitura na Casa do Reator ---");
        casaDoReator.registrarLeituraRadiacao(4.7);
        imprimirSituacao(casaDoReator);
    }

    private static void imprimirSituacao(ZonaRestrita zona) {
        System.out.println(zona.identificador + " - " + zona.descricao
                + " | Radiação: " + zona.nivelRadiacaoAtual + " µSv/h"
                + " | Risco: " + zona.classificarRisco());
    }
}