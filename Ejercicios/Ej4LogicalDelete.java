package Ejercicios;

public class Ej4LogicalDelete {

    public static final int EMPTY = 0;
    public static final int OCCUPIED = 1;
    public static final int DELETED = 2;

    static class Entry {
        Integer key;
        int status;

        Entry() {
            this.key = null;
            this.status = EMPTY;
        }
    }

    private Entry[] table;
    private int size;

    public Ej4LogicalDelete(int size) {
        this.size = size;
        this.table = new Entry[size];
        for (int i = 0; i < size; i++) {
            table[i] = new Entry();
        }
    }

    private int hash(int key) {
        return Math.abs(key) % size;
    }

    public void insert(int key) {
        int index = hash(key);
        int initialIndex = index;
        int firstDeletedIdx = -1;

        do {
            if (table[index].status == EMPTY) {
                int target = (firstDeletedIdx != -1) ? firstDeletedIdx : index;
                table[target].key = key;
                table[target].status = OCCUPIED;
                System.out.println("Clave " + key + " insertada en índice [" + target + "]");
                return;
            }
            if (table[index].status == DELETED && firstDeletedIdx == -1) {
                firstDeletedIdx = index;
            }
            if (table[index].status == OCCUPIED && table[index].key == key) {
                return; 
            }
            index = (index + 1) % size;
        } while (index != initialIndex);

        if (firstDeletedIdx != -1) {
            table[firstDeletedIdx].key = key;
            table[firstDeletedIdx].status = OCCUPIED;
            System.out.println("Clave " + key + " insertada reutilizando celda DELETED [" + firstDeletedIdx + "]");
        } else {
            System.out.println("Error: Tabla llena para la clave " + key);
        }
    }

    public boolean search(int key) {
        int index = hash(key);
        int initialIndex = index;

        System.out.print("Buscando clave " + key + " -> Ruta de sondeo: ");
        do {
            System.out.print("[" + index + " (" + getStatusStr(table[index].status) + ")] ");
            if (table[index].status == EMPTY) {
                System.out.println("-> Resultado: NO ENCONTRADA (Sondeo detenido en EMPTY).");
                return false;
            }
            if (table[index].status == OCCUPIED && table[index].key == key) {
                System.out.println("-> Resultado: ¡ENCONTRADA!");
                return true;
            }
            index = (index + 1) % size;
        } while (index != initialIndex);

        System.out.println("-> Resultado: NO ENCONTRADA (Tabla recorrida por completo).");
        return false;
    }

    public void deleteLogically(int key) {
        int index = hash(key);
        int initialIndex = index;

        do {
            if (table[index].status == EMPTY) {
                System.out.println("Eliminación fallida: El elemento no existe.");
                return;
            }
            if (table[index].status == OCCUPIED && table[index].key == key) {
                table[index].status = DELETED;
                table[index].key = null; 
                System.out.println("Clave " + key + " eliminada lógicamente en índice [" + index + "]");
                return;
            }
            index = (index + 1) % size;
        } while (index != initialIndex);
    }

    public void printTableState() {
        System.out.println("==================================================");
        for (int i = 0; i < size; i++) {
            String val = (table[i].status == OCCUPIED) ? String.valueOf(table[i].key) : "---";
            System.out.println("Índice [" + i + "]: STATUS = " + getStatusStr(table[i].status) + " \t| Valor = " + val);
        }
        System.out.println("==================================================\n");
    }

    private String getStatusStr(int status) {
        switch (status) {
            case OCCUPIED: return "OCCUPIED";
            case DELETED:  return "DELETED ";
            default:       return "EMPTY   ";
        }
    }

    public static void main(String[] args) {
        Ej4LogicalDelete ht = new Ej4LogicalDelete(7);

        System.out.println("--- FASE 1: INSERCIÓN DE CLAVES (5, 12, 19, 26) ---");
        ht.insert(5);
        ht.insert(12);
        ht.insert(19);
        ht.insert(26);
        ht.printTableState();

        System.out.println("--- FASE 2: ELIMINACIÓN LÓGICA DE LA CLAVE 12 ---");
        ht.deleteLogically(12);
        ht.printTableState();

        System.out.println("--- FASE 3: VERIFICACIÓN DE BÚSQUEDA TRAS ELIMINACIÓN ---");
        ht.search(19);
        System.out.println();

        System.out.println("--- FASE 4: REINSERCIÓN DE LA CLAVE 33 (REUTILIZACIÓN) ---");
        ht.insert(33);
        ht.printTableState();
    }
}