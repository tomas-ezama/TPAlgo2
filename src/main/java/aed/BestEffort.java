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
    private int totalDeTrasladosPorDespachar;
    private Heap<Ciudad> ciudadesConMayorSuperavit;
    private ArrayList<Integer> ciudadesConMayorGanacia;
    private ArrayList<Integer> ciudadesConMayorPerdida;
    private int totalDeTrasladosDespachados;
    private int gananciaTotal;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        totalDeTrasladosPorDespachar = traslados.length;
        gananciaTotal = 0;
        iniciarCiudades(cantCiudades); //O(C)
        iniciarTraslados(); //O(1)
        for (int i = 0; i < totalDeTrasladosPorDespachar; i++) { //O(T)
            Traslado traslado = traslados[i];
            trasladosMasAntiguos.encolar(traslado); // O(Log(T))
            trasladosMasRedituables.encolar(traslado); // O(Log(T))
        }
        actualizarIndices(); //O(T)
    } //O(C) + O(T) * O(Log(T))

    private void actualizarIndices() {
        ArrayList<Traslado> masAntiguosHeap = trasladosMasAntiguos.getHeap();
        ArrayList<Traslado> masRedituablesHeap = trasladosMasRedituables.getHeap();
        for (int i = 0; i < totalDeTrasladosPorDespachar; i++) {
            masAntiguosHeap.get(i).setIndiceMasAntiguo(i);
            masRedituablesHeap.get(i).setIndiceMasRedituable(i);
        }
    } //O(N)

    private void acturalizarIndicesMasAntiguos (ArrayList<Pair<Integer, Traslado>> lista) {
        for (int i = 0; i < lista.size(); i++) { // O(N)
            if(Objects.nonNull(lista.get(i).getValue())) {
                lista.get(i).getValue().setIndiceMasAntiguo(lista.get(i).getKey());
            }
        }
    } // O(N)

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
        for (int i = 0; i < cantCiudades; i++) { // O(N)
            this.ciudades[i] = new Ciudad(i);
        }
        ciudadesConMayorGanacia = new ArrayList<>();
        ciudadesConMayorPerdida = new ArrayList<>();
        ciudadesConMayorSuperavit = new Heap<>(new CiudadesRedituablesComparator());
        ciudadesConMayorGanacia.add(0);
        ciudadesConMayorPerdida.add(0);
    } //O(N)

    private void iniciarTraslados() {
        trasladosMasAntiguos = new Heap<>(new TrasladosAntiguosComparator());
        trasladosMasRedituables = new Heap<>(new TrasladosRedituablesComparator());
    } // O(1)

    public void registrarTraslados(Traslado[] traslados) {
        for (Traslado traslado: traslados) { // O(traslados)
            ArrayList<Pair<Integer, Traslado>> indicesRedi = trasladosMasRedituables.encolar(traslado); // O(Log(T))
            acturalizarIndicesMasRedituables(indicesRedi); // O(N) = O(Log(T))
            ArrayList<Pair<Integer, Traslado>> indicesAntiguos = trasladosMasAntiguos.encolar(traslado); // O(Log(T))
            acturalizarIndicesMasAntiguos(indicesAntiguos); // O(N) =O(Log(T))
            totalDeTrasladosPorDespachar++;
        }
    } // O(traslados * O(Log(T))

    public int[] despacharMasRedituables(int n) {
        n = n > trasladosMasRedituables.getNroDeElementos() ? trasladosMasRedituables.getNroDeElementos() : n;
        int[] ids = new int[n];
        for (int i = 0; i < n; i++) { // O(n)
            Traslado trasladoADespachar = trasladosMasRedituables.proximo(); // O(1)
            despacharTrasladoMasRedituable(trasladoADespachar); // O(Log(T)) + O(Log(T))
            actualizarCiudadesConMayorGanancia(trasladoADespachar); // Si todas tienen mayor gananacia o mayor perdida
            actualizarCiudadConMayorPerdida(trasladoADespachar); // tendremos O(C).
            actualizarCiudadMasRedituables(trasladoADespachar); // O(Log(C)) + O(Log(C))

            ids[i] = trasladoADespachar.getId();
            totalDeTrasladosDespachados++;
            gananciaTotal += trasladoADespachar.getGananciaNeta();
        }
        return ids;
    } // O( n * ( Log(T) + Log(C) )

    private void despacharTrasladoMasRedituable(Traslado trasladoADespachar) {
        acturalizarIndicesMasRedituables(trasladosMasRedituables.desencolar()); // O(Log(T))
        acturalizarIndicesMasAntiguos(trasladosMasAntiguos.eliminar(trasladoADespachar.getIndiceMasAntiguo())); // O(Log(T))
        totalDeTrasladosPorDespachar--;
    } // O(Log(T)) + O(Log(T))

    public int[] despacharMasAntiguos(int n){
        n = n > trasladosMasAntiguos.getNroDeElementos() ? trasladosMasAntiguos.getNroDeElementos() : n;
        int[] ids = new int[n];
        for (int i = 0; i < n; i++) {
            Traslado trasladoADespachar = trasladosMasAntiguos.proximo();
            despacharTrasladoMasAntiguo(trasladoADespachar); // O(Log(T)) + O(Log(T))
            actualizarCiudadesConMayorGanancia(trasladoADespachar); // Si todas tienen mayor gananacia o mayor perdida
            actualizarCiudadConMayorPerdida(trasladoADespachar); // tendremos O(C).
            actualizarCiudadMasRedituables(trasladoADespachar); // O(Log(C)) + O(Log(C))

            ids[i] = trasladoADespachar.getId();
            totalDeTrasladosDespachados++;
            gananciaTotal += trasladoADespachar.getGananciaNeta();
        }
        return ids;
    } // O( n * ( Log(T) + Log(C) )

    private void actualizarCiudadConMayorPerdida(Traslado trasladoADespachar) {
        int idDestino = trasladoADespachar.getDestino();
        ciudades[idDestino].sumarPerdidaTotal(trasladoADespachar.getGananciaNeta());
        if (ciudades[idDestino].getPerdidaTotal() > ciudades[ciudadesConMayorPerdida.get(0)].getPerdidaTotal()) {
            ciudadesConMayorPerdida =  new ArrayList<>();
            ciudadesConMayorPerdida.add(idDestino);
        } else if (ciudades[idDestino].getPerdidaTotal() == ciudades[ciudadesConMayorPerdida.get(0)].getPerdidaTotal()) {
            boolean noPertenece = true;
            for (Integer ciudad : ciudadesConMayorPerdida) {
                if (ciudad == idDestino) {
                    noPertenece = false;
                }
            }
            if (noPertenece) {
                ciudadesConMayorPerdida.add(idDestino);
            } else {
                ciudadesConMayorPerdida = new ArrayList<>();
                ciudadesConMayorPerdida.add(idDestino);
            }
        }
    }

    private void actualizarCiudadMasRedituables(Traslado trasladoADespachar) {
        int idOrigen = trasladoADespachar.getOrigen();
        int idDestino = trasladoADespachar.getDestino();
        if (ciudades[idDestino].getIndiceEnHeap() == -1) {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.encolar(ciudades[idDestino]));
        } else {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.bajar(ciudades[idDestino].getIndiceEnHeap())); // O(Log(C)) + O(Log(C))
        }
        if (ciudades[idOrigen].getIndiceEnHeap() == -1) {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.encolar(ciudades[idOrigen]));
        } else {
            acturalizarCiudadesMasRedituables(ciudadesConMayorSuperavit.subir(ciudades[idOrigen].getIndiceEnHeap())); // O(Log(C)) + O(Log(C))
        }
    } // O(Log(C))

    private void actualizarCiudadesConMayorGanancia(Traslado trasladoADespachar) {
        int idOrigen = trasladoADespachar.getOrigen();
        ciudades[idOrigen].sumarGananciaTotal(trasladoADespachar.getGananciaNeta());
        if (ciudades[idOrigen].getGananciaTotal() > ciudades[ciudadesConMayorGanacia.get(0)].getGananciaTotal()) {
            ciudadesConMayorGanacia = new ArrayList<>();
            ciudadesConMayorGanacia.add(idOrigen);
        } else if (ciudades[idOrigen].getGananciaTotal() == ciudades[ciudadesConMayorGanacia.get(0)].getGananciaTotal()) {
            boolean noPertenece = true;
            for (Integer ciudad : ciudadesConMayorGanacia) { // O(C)
                if (ciudad == idOrigen) {
                    noPertenece = false;
                }
            }
            if (noPertenece) {
                ciudadesConMayorGanacia.add(idOrigen);
            } else {
                ciudadesConMayorGanacia = new ArrayList<>();
                ciudadesConMayorGanacia.add(idOrigen);
            }
        }
    } // O(C)

    private void despacharTrasladoMasAntiguo(Traslado trasladoDespachado) {
        acturalizarIndicesMasAntiguos(trasladosMasAntiguos.desencolar()); // O(Log(T)) + O(Log(T))
        acturalizarIndicesMasRedituables(trasladosMasRedituables.eliminar(trasladoDespachado.getIndiceMasRedituable())); // O(Log(T)) + O(Log(T))
        totalDeTrasladosPorDespachar--;
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
