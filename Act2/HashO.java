package Act2;

public class HashO<T> {
    private LinkedList<Register<T>>[] table; 
    private int size;                        

    @SuppressWarnings("unchecked")
    public HashO(int size) {
        this.size = size;
        this.table = new LinkedList[size];
        
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(int key) {
        return Math.abs(key) % size;
    }

    public void insert(Register<T> reg) {
        int index = hash(reg.getKey());
        table[index].insertLast(reg);
    }

    public Register<T> search(int key) {
        int index = hash(key);
        
        LinkedList.Node<Register<T>> current = table[index].getHead();
        while (current != null) {
            if (current.data.getKey() == key) {
                return current.data; 
            }
            current = current.next;
        }
        return null; 
    }

    public void delete(int key) {
        int index = hash(key);
        
        LinkedList.Node<Register<T>> current = table[index].getHead();
        while (current != null) {
            if (current.data.getKey() == key) {
                boolean removed = table[index].removeNode(current);
                if (removed) {
                    System.out.println("Clave " + key + " eliminada físicamente de la lista del índice [" + index + "].");
                }
                return;
            }
            current = current.next;
        }
        System.out.println("Eliminación fallida: La clave " + key + " no existe en la tabla.");
    }

    public void printTable() {
        System.out.println("==================== ESTADO TABLA HASH ABIERTA ====================");
        for (int i = 0; i < size; i++) {
            System.out.print("Índice [" + i + "]: ");
            if (table[i].isEmpty()) {
                System.out.println("[Vacío]");
            } else {
                LinkedList.Node<Register<T>> current = table[i].getHead();
                while (current != null) {
                    System.out.print(current.data + " -> ");
                    current = current.next;
                }
                System.out.println("null");
            }
        }
        System.out.println("===================================================================\n");
    }
}