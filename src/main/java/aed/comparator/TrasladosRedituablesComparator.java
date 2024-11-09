package aed.comparator;

import aed.Traslado;

import java.util.Comparator;

public class TrasladosRedituablesComparator implements Comparator<Traslado> {
    @Override
    public int compare(Traslado o1, Traslado o2) {
        int masRedituable = o1.getGananciaNeta() - o2.getGananciaNeta();
        if (masRedituable == 0) {
            return o2.getId() - o1.getId();
        }
        return masRedituable;
    }
}
