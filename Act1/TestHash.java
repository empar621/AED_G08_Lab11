package Act1;

public class TestHash {
    public static void main(String[] args) {
        HashC<String> hashC = new HashC<>(15);

        System.out.println("--- INICIANDO INSERCIÓN DE VALORES ---");
        hashC.insert(new Register<>(34, "Juan"));
        hashC.insert(new Register<>(3, "Maria"));
        hashC.insert(new Register<>(7, "Pedro"));
        hashC.insert(new Register<>(30, "Luis")); // Provocará colisiones
        hashC.insert(new Register<>(11, "Ana"));
        hashC.insert(new Register<>(8, "Carlos"));
        hashC.insert(new Register<>(7, "Rosa"));   // Clave repetida (mismo key, diferente dato)
        hashC.insert(new Register<>(23, "Jorge"));
        hashC.insert(new Register<>(41, "Sofia"));
        hashC.insert(new Register<>(16, "Andres"));
        hashC.insert(new Register<>(34, "Lucia"));  // Clave repetida

        // 1. Mostrar el estado de la tabla hash inicial con las inserciones y colisiones
        System.out.println("\n>>> ESTADO DE LA TABLA HASH ANTES DE LA ELIMINACIÓN <<<");
        hashC.printTable();

        // 2. Realizar la búsqueda de la clave 23 antes de las modificaciones
        System.out.println("--- BUSCANDO CLAVE 23 ---");
        Register<String> found = hashC.search(23);
        if (found != null) {
            System.out.println("Resultado de búsqueda exitoso: " + found);
        } else {
            System.out.println("Clave 23 no encontrada.");
        }
        System.out.println();

        // 3. Eliminar de forma lógica la clave 30
        System.out.println("--- EJECUTANDO ELIMINACIÓN DE LA CLAVE 30 ---");
        hashC.delete(30);
        System.out.println();

        // 4. Mostrar el estado de la tabla posterior a la eliminación (para constatar el estado DELETED)
        System.out.println(">>> ESTADO DE LA TABLA HASH DESPUÉS DE ELIMINAR LA CLAVE 30 <<<");
        hashC.printTable();

        // 5. Volver a buscar la clave 23 (Debe seguir encontrándose ya que DELETED no frena el sondeo)
        System.out.println("--- NUEVA BÚSQUEDA DE CLAVE 23 (POST-ELIMINACIÓN DE 30) ---");
        found = hashC.search(23);
        if (found != null) {
            System.out.println("Resultado de búsqueda exitoso: " + found + " (Demuestra que el sondeo no se rompió)");
        } else {
            System.out.println("Error crítico: El sondeo lineal se rompió debido a una mala gestión de DELETED.");
        }
    }
}