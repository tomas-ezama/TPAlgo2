package aed;

public class Ciudad {
    private int id;
    private int gananciaTotal;
    private int perdidaTotal;

    private int indiceEnHeap;

    public Ciudad(int id) {
        this.id = id;
        this.gananciaTotal = 0;
        this.perdidaTotal= 0;
        this.indiceEnHeap = -1;
    }

    public int getId() {
        return id;
    }

    public int getGananciaTotal() {
        return gananciaTotal;
    }

    public void sumarGananciaTotal(int gananciaTotal) {
        this.gananciaTotal += gananciaTotal;
    }

    public int getPerdidaTotal() {
        return perdidaTotal;
    }

    public void sumarPerdidaTotal(int perdidaTotal) {
        this.perdidaTotal += perdidaTotal;
    }

    public int getIndiceEnHeap() {
        return indiceEnHeap;
    }

    public void setIndiceEnHeap(int indiceEnHeap) {
        this.indiceEnHeap = indiceEnHeap;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
