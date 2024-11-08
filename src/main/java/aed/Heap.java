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
        if (heap.size() == nroDeElementos) {
            heap.add(data);
        } else {
            heap.set(nroDeElementos, data);
        }
        ArrayList<Pair<Integer, T>> posiciones = subir(nroDeElementos);
        nroDeElementos++;
        return posiciones;
    }

    public ArrayList<Pair<Integer, T>> desencolar() {
        ArrayList<Pair<Integer, T>> posiciones = new ArrayList<>();
        if (nroDeElementos == 1) {
            posiciones.add(new Pair(0, heap.get(RAIZ)));
            heap.set(RAIZ, null);
            nroDeElementos--;
        } else {
            T poped = proximo();
            heap.set(RAIZ, heap.get(ultimoElemento()));
            heap.set(ultimoElemento(), null);
            nroDeElementos--;
            posiciones = bajar(RAIZ);
            posiciones.add(new Pair(nroDeElementos - 1, poped));
        }
        return posiciones;
    }

    public T proximo() {
        return heap.get(RAIZ);
    }

    private ArrayList<Pair<Integer, T>> subir(int posicion) {
        int padre = buscarPosicionPadre(posicion);
        ArrayList<Pair<Integer, T>> posiciones = new ArrayList<>();
        while (posicion != RAIZ && comparator.compare(heap.get(posicion), heap.get(padre)) > 0) {
            permutar(posicion, padre);
            posiciones.add(new Pair<>(posicion, heap.get(posicion)));
            posicion = padre;
            padre = buscarPosicionPadre(posicion);
        }
        posiciones.add(new Pair<>(posicion, heap.get(posicion)));
        return posiciones;
    }

    private ArrayList<Pair<Integer, T>> bajar(int posicion) {
        int izq = buscarPosicionIzq(posicion);
        int der = buscarPosicionDer(posicion);
        ArrayList<Pair<Integer, T>> posiciones = new ArrayList<>();
        while (posicion < nroDeElementos
                && (haySiguienteMayor(posicion, izq) || haySiguienteMayor(posicion, der))) {
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
    }

    public ArrayList<Pair<Integer, T>> eliminar(int id) {
       heap.set(id, heap.get(ultimoElemento()));
       heap.set(ultimoElemento(), null);
       nroDeElementos--;
       return bajar(id);
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

    public int ultimoElemento() {
        return nroDeElementos - 1;
    }


}
