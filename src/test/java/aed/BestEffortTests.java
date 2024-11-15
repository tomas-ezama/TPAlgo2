package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42)
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_con_mas_ganancia_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void despachar_mas_viejo_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_mas_viejo_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void despachar_mixtos(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void agregar_traslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 1, 10001, 5),
            new Traslado(9, 0, 1, 40000, 2),
            new Traslado(10, 0, 1, 50000, 3),
            new Traslado(11, 0, 1, 50000, 4),
            new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void promedio_por_traslado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 1, 2, 1452, 5),
            new Traslado(9, 1, 2, 334, 2),
            new Traslado(10, 1, 2, 24, 3),
            new Traslado(11, 1, 2, 333, 4),
            new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());
        

    }

    @Test
    void mayor_superavit(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }

  // Tests agregados por el grupo
    
    @Test
    void mayor_ganancia(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 1, 4, 100, 5),
            new Traslado(2, 0, 6, 300, 2),
            new Traslado(3, 5, 2, 1000, 3),
            new Traslado(4, 3, 5, 500, 4),
            new Traslado(5, 5, 1, 2000, 1),
            new Traslado(6, 6, 0, 3000, 6),
            new Traslado(7, 2, 1, 999, 7),
            new Traslado(8, 2, 3, 999, 8),
            new Traslado(9, 2, 5, 999, 10),
            new Traslado(10, 2, 1, 999, 9)

        };

        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());

        sis.despacharMasRedituables(2);
        assertSetEquals(new ArrayList<>(Arrays.asList(6,5)), sis.ciudadesConMayorGanancia());

        sis.despacharMasRedituables(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(2)), sis.ciudadesConMayorGanancia());
    }

    @Test
    void mayor_perdida(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 1, 4, 200, 5),
            new Traslado(2, 6, 6, 300, 2), 
            new Traslado(3, 5, 2, 1000, 3), 
            new Traslado(4, 3, 5, 500, 4),
            new Traslado(5, 5, 1, 2000, 1), 
            new Traslado(6, 6, 0, 3000, 6), 
            new Traslado(7, 2, 4, 2900, 7),
            new Traslado(8, 2, 3, 500, 8),
            new Traslado(9, 2, 5, 100, 10),
            new Traslado(10, 2, 1, 300, 9)

        };

        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_de_sobra(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 0, 1, 200, 1),
            new Traslado(2, 0, 1, 300, 2), 
            new Traslado(3, 1, 0, 1000, 3), 
            new Traslado(4, 1, 0, 500, 4)
        };

        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(2);
        sis.despacharMasRedituables(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit());

        Traslado[] masNuevos = new Traslado[] {
            new Traslado(5, 4, 3, 10000, 5),
            new Traslado(6, 6,4,1000,6),
            new Traslado(6, 1,4,5000,6)

        };

        sis.registrarTraslados(masNuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertEquals(1, sis.ciudadConMayorSuperavit());
    }

    @Test
    void todos_empatan(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1,1,2,100,1),
            new Traslado(2,2,3,100,2),
            new Traslado(3,3,4,100,3),
            new Traslado(4,4,1,100,4)
        };

        BestEffort sis = new BestEffort(cantCiudades, nuevos);

        sis.despacharMasAntiguos(2);
        sis.despacharMasRedituables(10);

        assertSetEquals(new ArrayList<>(Arrays.asList(1,2,3,4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1,2,3,4)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit());
        }
}

