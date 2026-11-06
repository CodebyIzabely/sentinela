package br.com.sentinela.dominio;

public class DronePatrulha extends DispositivoMonitoramento {
    private static final int BATERIA_MINIMA_PARA_VOO = 20;
    private static final int CONSUMO_POR_MISSAO = 15;

    private final String rotaPatrulha;
    private final CameraVisaoComputacional camera;
    private int bateriaPercentual;
    private ZonaRestrita zonaAtual;

    public DronePatrulha(String codigoIdentificador, String rotaPatrulha) {
        super(codigoIdentificador, "Base de drones");
        if (rotaPatrulha == null || rotaPatrulha.isBlank()) {
            throw new IllegalArgumentException("Todo drone precisa de uma rota de patrulha");
        }
        this.rotaPatrulha = rotaPatrulha;
        this.camera = new CameraVisaoComputacional();
        this.bateriaPercentual = 100;
    }

    public String patrulhar() {
        iniciarMissao();
        return camera.analisarImagem();
    }

    public String despacharPara(ZonaRestrita destino) {
        if (destino == null) {
            throw new IllegalArgumentException("O drone precisa de uma zona de destino");
        }
        iniciarMissao();
        this.zonaAtual = destino;
        return camera.analisarImagem();
    }

    private void iniciarMissao() {
        if (bateriaPercentual < BATERIA_MINIMA_PARA_VOO) {
            throw new IllegalStateException("Bateria insuficiente para voo: " + bateriaPercentual + "%");
        }
        bateriaPercentual -= CONSUMO_POR_MISSAO;
    }

    public String getRotaPatrulha() {
        return rotaPatrulha;
    }

    public int getBateriaPercentual() {
        return bateriaPercentual;
    }

    public CameraVisaoComputacional getCamera() {
        return camera;
    }

    public ZonaRestrita getZonaAtual() {
        return zonaAtual;
    }
}