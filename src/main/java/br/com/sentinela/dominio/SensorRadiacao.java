package br.com.sentinela.dominio;

public class SensorRadiacao extends DispositivoMonitoramento {
    private final ZonaRestrita zonaMonitorada;
    private final double limiteSeguranca; // em µSv/h
    private double leituraAtual;          // em µSv/h

    public SensorRadiacao(String codigoIdentificador, String localizacao, ZonaRestrita zonaMonitorada) {
        super(codigoIdentificador, localizacao);
        if (zonaMonitorada == null) {
            throw new IllegalArgumentException("Todo sensor de radiação precisa monitorar uma zona");
        }
        this.zonaMonitorada = zonaMonitorada;
        this.limiteSeguranca = zonaMonitorada.getLimiteRadiacao();
    }

    public void registrarLeitura(double leitura) {
        zonaMonitorada.registrarLeituraRadiacao(leitura);
        this.leituraAtual = leitura;
    }

    public boolean ultrapassouLimite() {
        return leituraAtual >= limiteSeguranca;
    }

    public ZonaRestrita getZonaMonitorada() {
        return zonaMonitorada;
    }

    public double getLeituraAtual() {
        return leituraAtual;
    }

    public double getLimiteSeguranca() {
        return limiteSeguranca;
    }
}