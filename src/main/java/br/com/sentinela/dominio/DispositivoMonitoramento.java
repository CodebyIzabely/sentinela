package br.com.sentinela.dominio;

public abstract class DispositivoMonitoramento {
    private final String codigoIdentificador;
    private final String localizacao;
    private boolean emOperacao;

    protected DispositivoMonitoramento(String codigoIdentificador, String localizacao) {
        if (codigoIdentificador == null || codigoIdentificador.isBlank()) {
            throw new IllegalArgumentException("Todo dispositivo precisa de um código identificador");
        }
        if (localizacao == null || localizacao.isBlank()) {
            throw new IllegalArgumentException("Todo dispositivo precisa de uma localização");
        }
        this.codigoIdentificador = codigoIdentificador;
        this.localizacao = localizacao;
        this.emOperacao = true;
    }

    public void ativar() {
        this.emOperacao = true;
    }

    public void desativar() {
        this.emOperacao = false;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public boolean isEmOperacao() {
        return emOperacao;
    }
}