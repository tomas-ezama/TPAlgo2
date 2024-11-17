package aed;



import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private static final int RAIZ = 0;
    private ArrayList<T> heap;
    private int nroDeElementos;
    private Comparator<T> comparator;

    public Heap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.nroDeElementos = 0;
        this.comparator = comparator;
    }

    public ArrayList<Pair<Integer, T>> encolar(T data) {
        if (heap.size() == nroDeElementos) { // O(1)
            heap.add(data); // O(1)
        } else {
            heap.set(nroDeElementos, data); //O(1)
        }
        ArrayList<Pair<Integer, T>> posiciones = subir(nroDeElementos); // O(Log(N))
        nroDeElementos++;
        return posiciones;
    } //O(Log(N))

    public ArrayList<Pair<Integer, T>> desencolar() {
        ArrayList<Pair<Integer, T>> posiciones = new ArrayList<>();
        if (nroDeElementos == 1) {
            posiciones.add(new Pair(0, heap.get(RAIZ)));
            heap.set(RAIZ, null);
            nroDeElementos--;
        } else {
            T poped = proximo();
            heap.set(RAIZ, heap.get(indiceDeUltimoElemento()));
            heap.set(indiceDeUltimoElemento(), null);
            nroDeElementos--;
            posiciones = bajar(RAIZ); // O(Log(T))
            posiciones.add(new Pair(nroDeElementos - 1, poped));
        }
        return posiciones;
    } // O(Log(T))

    public T proximo() {
        return heap.get(RAIZ);
    }

    public ArrayList<Pair<Integer, T>> subir(int posicion) {
        int padre = buscarPosicionPadre(posicion); //O(1)
        ArrayList<Pair<Integer, T>> posiciones = new ArrayList<>();
        while (posicion != RAIZ && comparator.compare(heap.get(posicion), heap.get(padre)) > 0) { // O(Log(n))
            permutar(posicion, padre); // O(1)
            posiciones.add(new Pair<>(posicion, heap.get(posicion)));
            posicion = padre;
            padre = buscarPosicionPadre(posicion);  // O(1)
        }
        posiciones.add(new Pair<>(posicion, heap.get(posicion)));
        return posiciones;
    } // O(Log(n))

    public ArrayList<Pair<Integer, T>> bajar(int posicion) {
        int izq = buscarPosicionIzq(posicion); // O(1)
        int der = buscarPosicionDer(posicion); // O(1)
        ArrayList<Pair<Integer, T>> posiciones = new ArrayList<>();
        while (posicion < nroDeElementos
                && (haySiguienteMayor(posicion, izq) || haySiguienteMayor(posicion, der))) { // O(Log(T))
            if (haySiguienteMayor(posicion, der) && comparator.compare(heap.get(der), heap.get(izq)) > 0) {
                permutar(posicion, der);
                posiciones.add(new Pair<>(posicion, heap.get(posicion)));
                posicion = der;
            } else {
                permutar(posicion, izq);
                posiciones.add(new Pair<>(posicion, heap.get(posicion)));
                posicion = izq;
            }
            izq = buscarPosicionIzq(posicion);
            der = buscarPosicionDer(posicion);
        }
        posiciones.add(new Pair<>(posicion, heap.get(posicion)));
        return posiciones;
    } // O(Log(T))

    public ArrayList<Pair<Integer, T>> eliminar(int id) {
       heap.set(id, heap.get(indiceDeUltimoElemento()));
       heap.set(indiceDeUltimoElemento(), null);
       nroDeElementos--;
       return bajar(id); //O(Log(T))
    }

    private void permutar(int a, int b) {
        T dataA = heap.get(a);
        T dataB = heap.get(b);
        heap.set(a, dataB);
        heap.set(b, dataA);
    }

    private boolean haySiguienteMayor(int posicion, int lado) {
        return lado<nroDeElementos && comparator.compare(heap.get(posicion), heap.get(lado)) < 0;
    }

    private int buscarPosicionPadre(int posicion) {
        return (posicion - 1) / 2;
    }

    private int buscarPosicionIzq(int posicion) {
        return 2 * posicion + 1;
    }

    private int buscarPosicionDer(int posicion) {
        return 2 * posicion + 2;
    }

    public String toString() {
        String s = "";
        if (nroDeElementos == 0) {
            return s;
        }
        for (int i = 0; i < nroDeElementos-1; i++) {
            s += heap.get(i) + ",";
        }
        return s + heap.get(nroDeElementos-1);
    }

    public int indiceDeUltimoElemento() {
        return nroDeElementos - 1;
    }

    public ArrayList<T> getHeap() {
        return new ArrayList<>(heap);
    }

    public int getNroDeElementos() {
        return nroDeElementos;
    }
}
