package br.com.sentinela;

import br.com.sentinela.dominio.ZonaRestrita;

public class Main {
    public static void main(String[] args) {
        ZonaRestrita casaDoReator = new ZonaRestrita();
        casaDoReator.identificador = "ZNR-01";
        casaDoReator.descricao = "Casa do Reator";
        casaDoReator.nivelRadiacaoAtual = 0.2;

        ZonaRestrita patioCombustivelIrradiado = new ZonaRestrita();
        patioCombustivelIrradiado.identificador = "ZNR-02";
        patioCombustivelIrradiado.descricao = "Pátio de Armazenamento de Combustível Irradiado";
        patioCombustivelIrradiado.nivelRadiacaoAtual = 0.3;

        System.out.println(casaDoReator.identificador + " - " + casaDoReator.descricao
                + " | Radiação: " + casaDoReator.nivelRadiacaoAtual + " µSv/h");
        System.out.println(patioCombustivelIrradiado.identificador + " - " + patioCombustivelIrradiado.descricao
                + " | Radiação: " + patioCombustivelIrradiado.nivelRadiacaoAtual + " µSv/h");
    }
}