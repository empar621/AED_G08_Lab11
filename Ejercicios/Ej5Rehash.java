package Ejercicios;

import java.util.Arrays;

public class Ej5Rehash {

    private int[] table;
    private int size;
    private int itemsCount;
    private final double threshold = 0.75; // Umbral crítico 

    public Ej5Rehash(int initialSize) {
        this.size = initialSize;
        this.table = new int[size];
        Arrays.fill(table, -1); // -1 celda vacía
        this.itemsCount = 0;
    }

    private int hash(int key, int currentSize) {
        return Math.abs(key) % currentSize;
    }

    public void insert(int key) {
        int index = hash(key, this.size);
        int i = index;

        // Sondeo lineal para ubicar una posición vacía
        do {
            if (table[i] == -1) {
                table[i] = key;
                itemsCount++;
                double currentAlpha = (double) itemsCount / size;
                
                System.out.printf("Insertado: %2d | Elementos (n): %d | Factor de carga (\u03b1): %.3f\n", 
                                  key, itemsCount, currentAlpha);
                
                if (currentAlpha > threshold) {
                    System.out.println("\n>> [ALERTA]: \u03b1 super\u00f3 el 0.75. Iniciando Rehashing... <<");
                    rehash(17); 
                }
                return;
            }
            i = (i + 1) % size;
        } while (i != index);
    }

    private void rehash(int newSize) {
        System.out.println("\n=== ESTADO DE LA TABLA ANTES DEL REHASH (M = " + size + ") ===");
        printTable();

        int[] oldTable = this.table;
        
        this.size = newSize;
        this.table = new int[size];
        Arrays.fill(table, -1);
        this.itemsCount = 0; 

        for (int key : oldTable) {
            if (key != -1) {
                insertNewTable(key);
            }
        }

        System.out.println("=== ESTADO DE LA TABLA DESPUES DEL REHASH (M = " + size + ") ===");
        printTable();
    }

    private void insertNewTable(int key) {
        int index = hash(key, this.size);
        while (table[index] != -1) {
            index = (index + 1) % size;
        }
        table[index] = key;
        itemsCount++;
    }

    public void printTable() {
        System.out.println(Arrays.toString(table));
        double finalAlpha = (double) itemsCount / size;
        System.out.printf("Capacidad total (M): %d | Ocupados (n): %d | \u03b1 Resultante: %.3f\n", 
                          size, itemsCount, finalAlpha);
        System.out.println("----------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Ej5Rehash ht = new Ej5Rehash(7);

        System.out.println("--- INICIANDO INSERCION SECUENCIAL DE VALORES ---");
        int[] valores = {2, 9, 16, 23, 4, 11};

        for (int val : valores) {
            ht.insert(val);
        }
    }
}