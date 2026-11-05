package br.com.sentinela.dominio;

public class ZonaRestrita {
    private static final double PERCENTUAL_ATENCAO = 0.5;
    private static final double LEITURA_MAXIMA_VALIDA = 1_000_000.0;

    private String identificador;
    private String descricao;
    private double nivelRadiacaoAtual; // em µSv/h
    private double limiteRadiacao;     // em µSv/h

    public void registrarLeituraRadiacao(double leitura) {
        if (leitura < 0) {
            throw new IllegalArgumentException("Radiação negativa não existe fisicamente: " + leitura);
        }
        if (leitura > LEITURA_MAXIMA_VALIDA) {
            throw new IllegalArgumentException("Leitura absurda, provável falha de sensor: " + leitura);
        }
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getNivelRadiacaoAtual() {
        return nivelRadiacaoAtual;
    }

    public double getLimiteRadiacao() {
        return limiteRadiacao;
    }

    public void setLimiteRadiacao(double limiteRadiacao) {
        if (limiteRadiacao <= 0) {
            throw new IllegalArgumentException("O limite de radiação deve ser maior que zero");
        }
        this.limiteRadiacao = limiteRadiacao;
    }
}