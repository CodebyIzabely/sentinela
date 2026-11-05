package br.com.sentinela;

import br.com.sentinela.dominio.ZonaRestrita;

public class Main {
    public static void main(String[] args) {
        ZonaRestrita casaDoReator = new ZonaRestrita("ZNR-01", "Casa do Reator", 2.5);
        ZonaRestrita patioCombustivelIrradiado = new ZonaRestrita(
                "ZNR-02", "Pátio de Armazenamento de Combustível Irradiado", 2.5);

        // ZonaRestrita zonaFantasma = new ZonaRestrita(); // não compila: não existe construtor padrão

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

        System.out.println("--- Tentativa de cadastrar zona sem identificador ---");
        try {
            new ZonaRestrita("", "Zona sem nome", 2.5);
        } catch (IllegalArgumentException erro) {
            System.out.println("Cadastro rejeitado: " + erro.getMessage());
        }
    }

    private static void imprimirSituacao(ZonaRestrita zona) {
        System.out.println(zona.getIdentificador() + " - " + zona.getDescricao()
                + " | Radiação: " + zona.getNivelRadiacaoAtual() + " µSv/h"
                + " | Risco: " + zona.classificarRisco());
    }
}