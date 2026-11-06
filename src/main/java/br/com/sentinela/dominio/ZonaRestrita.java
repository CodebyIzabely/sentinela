package br.com.sentinela.dominio;

public class ZonaRestrita {
    private static final double PERCENTUAL_ATENCAO = 0.5;
    private static final double LEITURA_MAXIMA_VALIDA = 1_000_000.0;

    private final String identificador;
    private final String descricao;
    private final double limiteRadiacao; // em µSv/h
    private double nivelRadiacaoAtual;   // em µSv/h

    public ZonaRestrita(String identificador, String descricao, double limiteRadiacao) {
        if (identificador == null || identificador.isBlank()) {
            throw new IllegalArgumentException("Toda zona precisa de um identificador");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Toda zona precisa de uma descrição");
        }
        if (limiteRadiacao <= 0) {
            throw new IllegalArgumentException("O limite de radiação deve ser maior que zero");
        }
        this.identificador = identificador;
        this.descricao = descricao;
        this.limiteRadiacao = limiteRadiacao;
        this.nivelRadiacaoAtual = 0.0;
    }

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

    public String getDescricao() {
        return descricao;
    }

    public double getLimiteRadiacao() {
        return limiteRadiacao;
    }

    public double getNivelRadiacaoAtual() {
        return nivelRadiacaoAtual;
    }
}