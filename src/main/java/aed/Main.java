package aed;

import aed.comparator.IntComparator;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Heap heap = new Heap<>(new IntComparator());
        heap.encolar(1);
        heap.encolar(2);
        heap.encolar(2);
        heap.encolar(3);
        heap.encolar(3);
        heap.encolar(5);
        heap.encolar(4);
        heap.encolar(5);
        heap.encolar(1);
        heap.desencolar();
        heap.desencolar();
        heap.desencolar();
        heap.desencolar();
        heap.desencolar();
        heap.desencolar();
        heap.proximo();
    }
}
