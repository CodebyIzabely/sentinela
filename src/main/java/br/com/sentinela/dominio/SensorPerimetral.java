package br.com.sentinela.dominio;

public class SensorPerimetral extends DispositivoMonitoramento {
    private final String setorVigiado;
    private final ZonaRestrita zonaProtegida;
    private boolean violacaoDetectada;

    public SensorPerimetral(String codigoIdentificador, String setorVigiado, ZonaRestrita zonaProtegida) {
        super(codigoIdentificador, "Cerca - setor " + setorVigiado);
        if (setorVigiado == null || setorVigiado.isBlank()) {
            throw new IllegalArgumentException("Todo sensor perimetral precisa de um setor vigiado");
        }
        if (zonaProtegida == null) {
            throw new IllegalArgumentException("Todo sensor perimetral precisa proteger uma zona");
        }
        this.setorVigiado = setorVigiado;
        this.zonaProtegida = zonaProtegida;
        this.violacaoDetectada = false;
    }

    public void registrarViolacaoCerca() {
        this.violacaoDetectada = true;
    }

    public void normalizarPerimetro() {
        this.violacaoDetectada = false;
    }

    public boolean isViolacaoDetectada() {
        return violacaoDetectada;
    }

    public String getSetorVigiado() {
        return setorVigiado;
    }

    public ZonaRestrita getZonaProtegida() {
        return zonaProtegida;
    }
}