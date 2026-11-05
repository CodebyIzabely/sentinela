package br.com.sentinela.dominio;

public class ZonaRestrita {
    private static final double PERCENTUAL_ATENCAO = 0.5;

    public String identificador;
    public String descricao;
    public double nivelRadiacaoAtual; // em µSv/h
    public double limiteRadiacao;     // em µSv/h

    public void registrarLeituraRadiacao(double leitura) {
        this.nivelRadiacaoAtual = leitura;
    }

    public String classificarRisco() {
        if (nivelRadiacaoAtual >= limiteRadiacao) {
            return "CRITICO";
        }
        if (nivelRadiacaoAtual >= limiteRadiacao * PERCENTUAL_ATENCAO) {
            return "ATENCAO";
        }
        return "NORMAL";
    }
}