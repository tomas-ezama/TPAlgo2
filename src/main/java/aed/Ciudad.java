package aed;

public class Ciudad {
    private int id;
    private int gananciaTotal;
    private int perdidaTotal;

    public Ciudad(int id) {
        this.id = id;
        this.gananciaTotal = 0;
        this.perdidaTotal= 0;
    }

    public int getId() {
        return id;
    }

    public int getGananciaTotal() {
        return gananciaTotal;
    }

    public void setGananciaTotal(int gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
    }

    public int getPerdidaTotal() {
        return perdidaTotal;
    }

    public void setPerdidaTotal(int perdidaTotal) {
        this.perdidaTotal = perdidaTotal;
    }
}
