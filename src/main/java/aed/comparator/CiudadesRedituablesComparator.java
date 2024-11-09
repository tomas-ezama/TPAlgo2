package aed.comparator;

import aed.Ciudad;

import java.util.Comparator;

public class CiudadesRedituablesComparator implements Comparator<Ciudad> {
    @Override
    public int compare(Ciudad o1, Ciudad o2) {
        int masRedituable = (o1.getGananciaTotal() - o1.getPerdidaTotal()) - (o2.getGananciaTotal()- o2.getPerdidaTotal());
        if (masRedituable == 0) {
            return o2.getId() - o1.getId();
        }
        return masRedituable;
    }
}
