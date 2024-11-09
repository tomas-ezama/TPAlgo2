package aed.comparator;

import aed.Traslado;

import java.util.Comparator;

public class TrasladosAntiguosComparator implements Comparator<Traslado> {
    @Override
    public int compare(Traslado o1, Traslado o2) {
        return o2.getTimestamp() - o1.getTimestamp();
    }
}
