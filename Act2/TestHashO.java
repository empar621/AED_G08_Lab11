package Act2;

public class TestHashO {
    public static void main(String[] args) {
        HashO<String> openHash = new HashO<>(7);

        System.out.println("--- INICIANDO INSERCIÓN DE ELEMENTOS ---");

        openHash.insert(new Register<>(10, "Juan"));   // 10 % 7 = 3
        openHash.insert(new Register<>(17, "Ana"));    // 17 % 7 = 3 (Colisión en índice 3)
        openHash.insert(new Register<>(24, "Luis"));   // 24 % 7 = 3 (Colisión múltiple en índice 3)
        openHash.insert(new Register<>(5, "Pedro"));   // 5 % 7 = 5
        openHash.insert(new Register<>(12, "Carla"));  // 12 % 7 = 5 (Colisión en índice 5)
        openHash.insert(new Register<>(8, "Sofia"));    // 8 % 7 = 1

        // 1. Mostrar el contenido de cada lista de la tabla hash inicial
        System.out.println("\n>>> TABLA HASH INICIAL CON COLISIONES ENCADENADAS <<<");
        openHash.printTable();

        // 2. Probar la operación de búsqueda
        System.out.println("--- EJECUTANDO BÚSQUEDA ---");
        int buscarClave = 17;
        Register<String> result = openHash.search(buscarClave);
        if (result != null) {
            System.out.println("Registro encontrado con éxito: " + result);
        } else {
            System.out.println("La clave " + buscarClave + " no fue localizada.");
        }
        System.out.println();

        // 3. Probar la operación de eliminación física
        System.out.println("--- EJECUTANDO ELIMINACIÓN DE LA CLAVE 17 ---");
        openHash.delete(17);
        System.out.println();

        // 4. Mostrar el contenido de la tabla hash final tras la eliminación
        System.out.println(">>> TABLA HASH FINAL LUEGO DE LA REMOCIÓN <<<");
        openHash.printTable();
        
        // 5. Verificar que los demás elementos de la cadena (como el 24) sigan accesibles
        System.out.println("--- COMPROBACIÓN DE INTEGRIDAD DE CADENA (BÚSQUEDA CLAVE 24) ---");
        Register<String> postDeleteResult = openHash.search(24);
        if (postDeleteResult != null) {
            System.out.println("Verificación exitosa: " + postDeleteResult + " sigue en la tabla.");
        } else {
            System.out.println("Error: Se rompió el enlace de la lista al eliminar un elemento intermedio.");
        }
    }
}