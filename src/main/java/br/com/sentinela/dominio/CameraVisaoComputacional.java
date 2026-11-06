package br.com.sentinela.dominio;

public class CameraVisaoComputacional {
    public static final String NENHUMA_DETECCAO = "NENHUMA_DETECCAO";
    public static final String VEICULO_NAO_IDENTIFICADO = "VEICULO_NAO_IDENTIFICADO";

    private String proximaDeteccao = NENHUMA_DETECCAO;

    public void programarProximaDeteccao(String deteccao) {
        this.proximaDeteccao = deteccao;
    }

    public String analisarImagem() {
        String resultado = proximaDeteccao;
        proximaDeteccao = NENHUMA_DETECCAO;
        return resultado;
    }
}