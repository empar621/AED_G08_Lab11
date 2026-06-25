package Ejercicios;

import java.util.LinkedList;

public class Ej3HashAbierto {
    
    static class HashEntry {
        int key;
        String name;

        HashEntry(int key, String name) {
            this.key = key;
            this.name = name;
        }

        @Override
        public String toString() {
            return "(" + key + ", \"" + name + "\")";
        }
    }

    public static void main(String[] args) {
        int size = 7; 
        @SuppressWarnings("unchecked")
        LinkedList<HashEntry>[] table = new LinkedList[size];
 
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }

        System.out.println("--- 1. PROCESO DE INSERCIÓN ---");
        int[] claves = {10, 17, 24, 31, 5, 12};
        String[] nombres = {"Juan", "Ana", "Luis", "Rosa", "Pedro", "Carla"};

        for (int i = 0; i < claves.length; i++) {
            int idx = claves[i] % size;
            table[idx].add(new HashEntry(claves[i], nombres[i]));
            System.out.println("Insertado: " + claves[i] + " -> Índice calculado: " + idx);
        }

        System.out.println("\n>>> ESTADO INICIAL DE LA TABLA HASH ABIERTA <<<");
        printOpenTable(table);

        // búsqueda de la clave 24
        System.out.println("--- 2. EJECUTANDO BÚSQUEDA DE LA CLAVE 24 ---");
        int targetKey = 24;
        int targetIdx = targetKey % size;
        LinkedList<HashEntry> list = table[targetIdx];
        
        int nodePosition = 1;
        boolean found = false;
        for (HashEntry entry : list) {
            if (entry.key == targetKey) {
                System.out.println("Clave encontrada exitosamente.");
                System.out.println("-> Nombre asociado: " + entry.name);
                System.out.println("-> Posición en la Tabla Hash: Índice [" + targetIdx + "]");
                System.out.println("-> Ubicación en la Lista Enlazada: Nodo número " + nodePosition);
                found = true;
                break;
            }
            nodePosition++;
        }
        if (!found) System.out.println("La clave no existe.");

        // Eliminar la clave 17
        System.out.println("\n--- 3. ELIMINANDO LA CLAVE 17 ---");
        int deleteKey = 17;
        int deleteIdx = deleteKey % size;
        
        // Eliminación física basada en la coincidencia de clave
        boolean removed = table[deleteIdx].removeIf(entry -> entry.key == deleteKey);
        if (removed) {
            System.out.println("Registro con clave " + deleteKey + " eliminado físicamente.");
        }

        // Mostrar estado final de la tabla y conteo de nodos restantes
        System.out.println("\n>>> ESTADO DE LA TABLA DESPUÉS DE ELIMINAR LA CLAVE 17 <<<");
        printOpenTable(table);
        System.out.println("¿Cuántos nodos quedan en la cadena del índice 3?: " + table[3].size() + " nodos.");
    }

    private static void printOpenTable(LinkedList<HashEntry>[] table) {
        System.out.println("==================================================");
        for (int i = 0; i < table.length; i++) {
            System.out.print("Índice [" + i + "]: ");
            if (table[i].isEmpty()) {
                System.out.println("null");
            } else {
                for (HashEntry entry : table[i]) {
                    System.out.print(entry + " --> ");
                }
                System.out.println("null");
            }
        }
        System.out.println("==================================================");
    }
}