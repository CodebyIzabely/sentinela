package br.com.sentinela.dominio;

public class Sensor {
    private final String codigoIdentificador;
    private final String localizacao;
    private final ZonaRestrita zonaMonitorada;
    private boolean emOperacao;

    public Sensor(String codigoIdentificador, String localizacao, ZonaRestrita zonaMonitorada) {
        if (codigoIdentificador == null || codigoIdentificador.isBlank()) {
            throw new IllegalArgumentException("Todo sensor precisa de um código identificador");
        }
        if (localizacao == null || localizacao.isBlank()) {
            throw new IllegalArgumentException("Todo sensor precisa de uma localização");
        }
        if (zonaMonitorada == null) {
            throw new IllegalArgumentException("Todo sensor precisa monitorar uma zona");
        }
        this.codigoIdentificador = codigoIdentificador;
        this.localizacao = localizacao;
        this.zonaMonitorada = zonaMonitorada;
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

    public ZonaRestrita getZonaMonitorada() {
        return zonaMonitorada;
    }

    public boolean isEmOperacao() {
        return emOperacao;
    }
}