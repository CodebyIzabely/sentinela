package br.com.sentinela;

import br.com.sentinela.dominio.ZonaRestrita;

public class Main {
    public static void main(String[] args) {
        ZonaRestrita casaDoReator = new ZonaRestrita();
        casaDoReator.setIdentificador("ZNR-01");
        casaDoReator.setDescricao("Casa do Reator");
        casaDoReator.setLimiteRadiacao(2.5);

        ZonaRestrita patioCombustivelIrradiado = new ZonaRestrita();
        patioCombustivelIrradiado.setIdentificador("ZNR-02");
        patioCombustivelIrradiado.setDescricao("Pátio de Armazenamento de Combustível Irradiado");
        patioCombustivelIrradiado.setLimiteRadiacao(2.5);

        casaDoReator.registrarLeituraRadiacao(0.2);
        patioCombustivelIrradiado.registrarLeituraRadiacao(0.3);
        imprimirSituacao(casaDoReator);
        imprimirSituacao(patioCombustivelIrradiado);

        System.out.println("--- Nova leitura na Casa do Reator ---");
        casaDoReator.registrarLeituraRadiacao(4.7);
        imprimirSituacao(casaDoReator);

        System.out.println("--- Tentativa de leitura inválida ---");
        try {
            casaDoReator.registrarLeituraRadiacao(-5);
        } catch (IllegalArgumentException erro) {
            System.out.println("Leitura rejeitada: " + erro.getMessage());
        }
        imprimirSituacao(casaDoReator);
    }

    private static void imprimirSituacao(ZonaRestrita zona) {
        System.out.println(zona.getIdentificador() + " - " + zona.getDescricao()
                + " | Radiação: " + zona.getNivelRadiacaoAtual() + " µSv/h"
                + " | Risco: " + zona.classificarRisco());
    }
}