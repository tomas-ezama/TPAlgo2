package aed;

import aed.comparator.CiudadesRedituablesComparator;
import aed.comparator.TrasladosAntiguosComparator;
import aed.comparator.TrasladosRedituablesComparator;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    private Ciudad[] ciudades;
    private Heap<Traslado> trasladosMasAntiguos;
    private Heap<Traslado> trasladosMasRedituables;
    private ArrayList<Traslado> trasladosPorDespachar;
    private int trasladosArraySize;
    private Heap<Ciudad> ciudadesConMayorSuperavit;
    private ArrayList<Integer> ciudadesConMayorGanacia;
    private ArrayList<Integer> ciudadesConMayorPerdida;
    private int trasladosDespachados;
    private int gananciaTotal;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        trasladosArraySize = traslados.length;
        gananciaTotal = 0;
        iniciarCiudades(cantCiudades);
        iniciarTraslados();
        for (int i = 0; i < trasladosArraySize; i++) {
            Traslado traslado = traslados[i];
            traslado.setIndiceMaestro(i);
            trasladosPorDespachar.add(traslado);
            trasladosMasAntiguos.encolar(traslado);
            trasladosMasRedituables.encolar(traslado);
        }
        ArrayList<Traslado> masAntiguosHeap = trasladosMasAntiguos.getHeap();
        ArrayList<Traslado> masRedituablesHeap = trasladosMasRedituables.getHeap();
        for (int i = 0; i < trasladosArraySize; i++) {
            masAntiguosHeap.get(i).setIndiceMasAntiguo(i);
            masRedituablesHeap.get(i).setIndiceMasRedituable(i);
        }
    }

    private void acturalizarIndicesMasAntiguos (ArrayList<Pair<Integer, Traslado>> lista) {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).getValue().setIndiceMasAntiguo(lista.get(i).getKey());
        }
    }

    private void acturalizarIndicesMasRedituables (ArrayList<Pair<Integer, Traslado>> lista) {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).getValue().setIndiceMasRedituable(lista.get(i).getKey());
        }
    }

    private void iniciarCiudades(int cantCiudades) {
        this.ciudades = new Ciudad[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {
            this.ciudades[i] = new Ciudad(i);
        }
        ciudadesConMayorGanacia = new ArrayList<>();
        ciudadesConMayorPerdida = new ArrayList<>();
        ciudadesConMayorSuperavit = new Heap<>(new CiudadesRedituablesComparator());
    }

    private void iniciarTraslados() {
        trasladosMasAntiguos = new Heap<>(new TrasladosAntiguosComparator());
        trasladosMasRedituables = new Heap<>(new TrasladosRedituablesComparator());
        trasladosPorDespachar = new ArrayList<>();
    }

    public void registrarTraslados(Traslado[] traslados) {
        ArrayList<Pair<Integer, Traslado>> nuevosIndicesMasAntiguos = new ArrayList<>();
        ArrayList<Pair<Integer, Traslado>> nuevosIndicesMasRedituables = new ArrayList<>();

        for (Traslado traslado: traslados) {
            if (trasladosArraySize == trasladosPorDespachar.size()) {
                trasladosPorDespachar.add(traslado);
            } else {
                trasladosPorDespachar.set(trasladosArraySize, traslado);
            }
            nuevosIndicesMasRedituables.addAll(trasladosMasRedituables.encolar(traslado));
            nuevosIndicesMasAntiguos.addAll(trasladosMasAntiguos.encolar(traslado));
            trasladosArraySize++;
        }
        acturalizarIndicesMasRedituables(nuevosIndicesMasRedituables);
        acturalizarIndicesMasAntiguos(nuevosIndicesMasAntiguos);
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        return ciudadesConMayorSuperavit.proximo().getId();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesConMayorGanacia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesConMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return gananciaTotal/ trasladosDespachados;
    }
    
}
