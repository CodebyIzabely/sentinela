package br.com.sentinela.dominio;

public class AlertaSeguranca {
    private final DispositivoMonitoramento sensorOrigem;
    private final ZonaRestrita zonaAfetada;
    private final String severidade;
    private final String mensagem;

    public AlertaSeguranca(DispositivoMonitoramento sensorOrigem, ZonaRestrita zonaAfetada, String severidade, String mensagem) {
        if (sensorOrigem == null || zonaAfetada == null) {
            throw new IllegalArgumentException("Todo alerta precisa de um sensor de origem e de uma zona afetada");
        }
        this.sensorOrigem = sensorOrigem;
        this.zonaAfetada = zonaAfetada;
        this.severidade = severidade;
        this.mensagem = mensagem;
    }

    public DispositivoMonitoramento getSensorOrigem() {
        return sensorOrigem;
    }

    public ZonaRestrita getZonaAfetada() {
        return zonaAfetada;
    }

    public String getSeveridade() {
        return severidade;
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        return "[" + severidade + "] " + mensagem
                + " | Sensor: " + sensorOrigem.getCodigoIdentificador()
                + " (" + sensorOrigem.getLocalizacao() + ")"
                + " | Zona: " + zonaAfetada.getIdentificador() + " - " + zonaAfetada.getDescricao();
    }
}