package Act1;

public class HashC<T> {
    // Constantes obligatorias para representar el estado de cada celda
    public static final int EMPTY = 0;
    public static final int OCCUPIED = 1;
    public static final int DELETED = 2;

    private static class Element<T> {
        Register<T> register; // Registro guardado
        int status;           // Estado de la celda (0, 1 o 2)

        Element() {
            this.register = null;
            this.status = EMPTY; 
        }
    }

    private Element<T>[] table; 
    private int size;           

    @SuppressWarnings("unchecked")
    public HashC(int size) {
        this.size = size;
        this.table = new Element[size];
        for (int i = 0; i < size; i++) {
            table[i] = new Element<>();
        }
    }

    private int hash(int key) {
        // Math.abs se asegura de manejar correctamente llaves negativas si existieran
        return Math.abs(key) % size;
    }

    public void insert(Register<T> reg) {
        int index = hash(reg.getKey());
        int initialIndex = index;
        int firstDeletedIndex = -1; 

        do {
            // Caso 1: Celda completamente vacía
            if (table[index].status == EMPTY) {
                int targetIndex = (firstDeletedIndex != -1) ? firstDeletedIndex : index;
                table[targetIndex].register = reg;
                table[targetIndex].status = OCCUPIED;
                return;
            }
            
            // Caso 2: Celda marcada como eliminada (guardamos su posición por si se requiere reutilizar)
            if (table[index].status == DELETED && firstDeletedIndex == -1) {
                firstDeletedIndex = index;
            }
            
            // Caso 3: Control de duplicados exactos en estado ocupado (opcional, actualiza el valor)
            if (table[index].status == OCCUPIED && table[index].register.getKey() == reg.getKey()) {
                table[index].register = reg; 
                return;
            }

            index = (index + 1) % size;
        } while (index != initialIndex);

        if (firstDeletedIndex != -1) {
            table[firstDeletedIndex].register = reg;
            table[firstDeletedIndex].status = OCCUPIED;
        } else {
            System.out.println("Error: La tabla hash está completamente llena. No se pudo insertar la clave: " + reg.getKey());
        }
    }

    public Register<T> search(int key) {
        int index = hash(key);
        int initialIndex = index;

        do {
            if (table[index].status == EMPTY) {
                return null;
            }

            if (table[index].status == OCCUPIED && table[index].register.getKey() == key) {
                return table[index].register;
            }

            index = (index + 1) % size;
        } while (index != initialIndex);

        return null; // Se recorrió la tabla sin éxito
    }

    public void delete(int key) {
        int index = hash(key);
        int initialIndex = index;

        do {
            if (table[index].status == EMPTY) {
                System.out.println("Eliminación fallida: La clave " + key + " no existe.");
                return;
            }
            
            if (table[index].status == OCCUPIED && table[index].register.getKey() == key) {
                table[index].status = DELETED;   
                table[index].register = null;    
                System.out.println("Clave " + key + " eliminada lógicamente con éxito.");
                return;
            }

            index = (index + 1) % size;
        } while (index != initialIndex);

        System.out.println("Eliminación fallida: La clave " + key + " no existe.");
    }

    public void printTable() {
        System.out.println("==================================================");
        for (int i = 0; i < size; i++) {
            System.out.print("Índice [" + i + "]: ");
            if (table[i].status == OCCUPIED) {
                System.out.println("STATUS: OCCUPIED -> Registro: " + table[i].register);
            } else if (table[i].status == DELETED) {
                System.out.println("STATUS: DELETED  -> (Espacio disponible para reinserción)");
            } else {
                System.out.println("STATUS: EMPTY    -> (Vacío)");
            }
        }
        System.out.println("==================================================\n");
    }
}