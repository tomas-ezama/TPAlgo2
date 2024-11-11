package aed;

import aed.comparator.CiudadesRedituablesComparator;
import aed.comparator.TrasladosAntiguosComparator;
import aed.comparator.TrasladosRedituablesComparator;

import java.util.ArrayList;
import java.util.Objects;

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
    private int totalDeTrasladosDespachados;
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
        actualizarIndices();
    }

    private void actualizarIndices() {
        ArrayList<Traslado> masAntiguosHeap = trasladosMasAntiguos.getHeap();
        ArrayList<Traslado> masRedituablesHeap = trasladosMasRedituables.getHeap();
        for (int i = 0; i < trasladosArraySize; i++) {
            masAntiguosHeap.get(i).setIndiceMasAntiguo(i);
            masRedituablesHeap.get(i).setIndiceMasRedituable(i);
        }
    }

    private void acturalizarIndicesMasAntiguos (ArrayList<Pair<Integer, Traslado>> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if(Objects.nonNull(lista.get(i).getValue())) {
                lista.get(i).getValue().setIndiceMasAntiguo(lista.get(i).getKey());
            }
        }
    }

    private void acturalizarIndicesMasRedituables (ArrayList<Pair<Integer, Traslado>> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (Objects.nonNull(lista.get(i).getValue())) {
                lista.get(i).getValue().setIndiceMasRedituable(lista.get(i).getKey());
            }
        }
    }

    private void acturalizarCiudadesMasRedituables (ArrayList<Pair<Integer, Ciudad>> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (Objects.nonNull(lista.get(i).getValue())) {
                lista.get(i).getValue().setIndiceEnHeap(lista.get(i).getKey());
            }
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
        ciudadesConMayorGanacia.add(0);
        ciudadesConMayorPerdida.add(0);
    }

    private void iniciarTraslados() {
        trasladosMasAntiguos = new Heap<>(new TrasladosAntiguosComparator());
        trasladosMasRedituables = new Heap<>(new TrasladosRedituablesComparator());
        trasladosPorDespachar = new ArrayList<>();
    }

    public void registrarTraslados(Traslado[] traslados) {
        for (Traslado traslado: traslados) {
            if (trasladosArraySize == trasladosPorDespachar.size()) {
                trasladosPorDespachar.add(traslado);
            } else {
                trasladosPorDespachar.set(trasladosArraySize, traslado);
            }
            trasladosMasRedituables.encolar(traslado);
            trasladosMasAntiguos.encolar(traslado);
            trasladosArraySize++;
        }
        actualizarIndices();
    }

    public int[] despacharMasRedituables(int n){
        n = n > trasladosMasRedituables.getNroDeElementos() ? trasladosMasRedituables.getNroDeElementos() : n;
        int[] ids = new int[n];
        for (int i = 0; i < n; i++) {
            Traslado trasladoADespachar = trasladosMasRedituables.proximo();
            despacharTrasladoMasRedituable(trasladoADespachar);
            actualizarCiudadesConMayorGanancia(trasladoADespachar);
            actualizarCiudadConMayorPerdida(trasladoADespachar);
            actualizarCiudadMasRedituables(trasladoADespachar);

            ids[i] = trasladoADespachar.getId();
            totalDeTrasladosDespachados++;
            gananciaTotal += trasladoADespachar.getGananciaNeta();
        }
        return ids;
    }

    private void despacharTrasladoMasRedituable(Traslado trasladoADespachar) {
        acturalizarIndicesMasRedituables(trasladosMasRedituables.desencolar());
        acturalizarIndicesMasAntiguos(trasladosMasAntiguos.eliminar(trasladoADespachar.getIndiceMasAntiguo()));
        trasladosPorDespachar.set(trasladoADespachar.getIndiceMaestro(), trasladosPorDespachar.get(trasladosArraySize-1));
        trasladosPorDespachar.get(trasladosArraySize-1).setIndiceMaestro(trasladoADespachar.getIndiceMaestro());
        trasladosPorDespachar.set(trasladosArraySize-1, null);
        trasladosArraySize--;
    }

    public int[] despacharMasAntiguos(int n){
        n = n > trasladosMasAntiguos.getNroDeElementos() ? trasladosMasAntiguos.getNroDeElementos() : n;
        int[] ids = new int[n];
        for (int i = 0; i < n; i++) {
            Traslado trasladoADespachar = trasladosMasAntiguos.proximo();
            despacharTrasladoMasAntiguo(trasladoADespachar);
            actualizarCiudadesConMayorGanancia(trasladoADespachar);
            actualizarCiudadConMayorPerdida(trasladoADespachar);
            actualizarCiudadMasRedituables(trasladoADespachar);

            ids[i] = trasladoADespachar.getId();
            totalDeTrasladosDespachados++;
            gananciaTotal += trasladoADespachar.getGananciaNeta();
        }
        return ids;
    }

    private void actualizarCiudadConMayorPerdida(Traslado trasladoADespachar) {
        int idDestino = trasladoADespachar.getDestino();
        ciudades[idDestino].sumarPerdidaTotal(trasladoADespachar.getGananciaNeta());
        if (ciudades[idDestino].getPerdidaTotal() > ciudades[ciudadesConMayorPerdida.get(0)].getPerdidaTotal()) {
            ciudadesConMayorPerdida.clear();
            ciudadesConMayorPerdida.add(idDestino);
        } else if (ciudades[idDestino].getPerdidaTotal() == ciudades[ciudadesConMayorPerdida.get(0)].getPerdidaTotal()) {
            boolean flag = true;
            for (Integer ciudad : ciudadesConMayorPerdida) {
                if (ciudad == idDestino) {
                    flag = false;
                }
            }
            if (flag) {
                ciudadesConMayorPerdida.add(idDestino);
            } else {
                ciudadesConMayorPerdida.clear();
                ciudadesConMayorPerdida.add(idDestino);
            }
        }
    }

    private void actualizarCiudadConGananciaEnMasRedituables(int idOrigen) {
        if (ciudades[idOrigen].getIndiceEnHeap() == -1) {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.encolar(ciudades[idOrigen]));
        } else {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.subir(ciudades[idOrigen].getIndiceEnHeap()));
        }
    }

    private void actualizarCiudadConPerdidaEnMasRedituables(int idDestion) {
        if (ciudades[idDestion].getIndiceEnHeap() == -1) {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.encolar(ciudades[idDestion]));
        } else {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.bajar(ciudades[idDestion].getIndiceEnHeap()));
        }
    }

    private void actualizarCiudadMasRedituables(Traslado trasladoADespachar) {
        int idOrigen = trasladoADespachar.getOrigen();
        int idDestino = trasladoADespachar.getDestino();
        if (ciudades[idDestino].getIndiceEnHeap() == -1) {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.encolar(ciudades[idDestino]));
        } else {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.bajar(ciudades[idDestino].getIndiceEnHeap()));
        }
        if (ciudades[idOrigen].getIndiceEnHeap() == -1) {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.encolar(ciudades[idOrigen]));
        } else {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.subir(ciudades[idOrigen].getIndiceEnHeap()));

        }
    }

    private void actualizarCiudadesConMayorGanancia(Traslado trasladoADespachar) {
        int idOrigen = trasladoADespachar.getOrigen();
        ciudades[idOrigen].sumarGananciaTotal(trasladoADespachar.getGananciaNeta());
        if (ciudades[idOrigen].getGananciaTotal() > ciudades[ciudadesConMayorGanacia.get(0)].getGananciaTotal()) {
            ciudadesConMayorGanacia.clear();
            ciudadesConMayorGanacia.add(idOrigen);
        } else if (ciudades[idOrigen].getGananciaTotal() == ciudades[ciudadesConMayorGanacia.get(0)].getGananciaTotal()) {
            boolean flag = true;
            for (Integer ciudad : ciudadesConMayorGanacia) {
                if (ciudad == idOrigen) {
                    flag = false;
                }
            }
            if (flag) {
                ciudadesConMayorGanacia.add(idOrigen);
            }
        }
    }

    private void despacharTrasladoMasAntiguo(Traslado trasladoDespachado) {
        acturalizarIndicesMasAntiguos(trasladosMasAntiguos.desencolar());
        acturalizarIndicesMasRedituables(trasladosMasRedituables.eliminar(trasladoDespachado.getIndiceMasRedituable()));
        trasladosPorDespachar.set(trasladoDespachado.getIndiceMaestro(), trasladosPorDespachar.get(trasladosArraySize-1));
        trasladosPorDespachar.get(trasladosArraySize-1).setIndiceMaestro(trasladoDespachado.getIndiceMaestro());
        trasladosPorDespachar.set(trasladosArraySize-1, null);
        trasladosArraySize--;
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
        return gananciaTotal/ totalDeTrasladosDespachados;
    }
    
}
