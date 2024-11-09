package aed;

public class Traslado {
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int indiceMaestro;
    private int indiceMasAntiguo;
    private int indiceMasRedituable;
    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public int getGananciaNeta() {
        return gananciaNeta;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getIndiceMaestro() {
        return indiceMaestro;
    }

    public void setIndiceMaestro(int indiceMaestro) {
        this.indiceMaestro = indiceMaestro;
    }

    public int getIndiceMasAntiguo() {
        return indiceMasAntiguo;
    }

    public void setIndiceMasAntiguo(int indiceMasAntiguo) {
        this.indiceMasAntiguo = indiceMasAntiguo;
    }

    public int getIndiceMasRedituable() {
        return indiceMasRedituable;
    }

    public void setIndiceMasRedituable(int indiceMasRedituable) {
        this.indiceMasRedituable = indiceMasRedituable;
    }
}
