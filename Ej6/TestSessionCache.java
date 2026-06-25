package Ej6;

public class TestSessionCache {
    public static void main(String[] args) {
        // capacidad de 7 cubos
        SessionCache cache = new SessionCache(7);

        System.out.println("=== (1) INICIO DE SESIÓN DE USUARIOS (LOGIN) ===");
        // User 1: expiracion rapida para provocar caducidad forzada (1 segundo de vida)
        cache.login("abc123", "juan_emerson", "ADMIN", 1000); // 
        // User 2: sesion estandar (6 segundos de vida)
        cache.login("xyz789", "daniel_alejandro", "USER", 6000); // 
        // User 3: sesion prolongada (10 segundos de vida)
        cache.login("mno456", "eduardo_gustavo", "MANAGER", 10000);
        
        System.out.println("\nSesiones inicialmente activas detectadas: " + cache.getActiveSessionsCount());
        System.out.println("========================================================\n");

        // Simular retraso del sistema de 1.5 segundos para forzar la expiración del primer token
        try {
            System.out.println("... Esperando 1500 milisegundos (HTTP) ...\n");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("=== (2) VALIDACIÓN DE TOKENS ===");
        // validacion del usuario 1 
        Session s1 = cache.validate("abc123");
        if (s1 != null) System.out.println("Sesión válida: " + s1);

        // validacion del usuario 2 (aub valida)
        Session s2 = cache.validate("xyz789");
        if (s2 != null) System.out.println("Sesión válida: " + s2);
        System.out.println("========================================================\n");

        System.out.println("=== (3) CIERRE DE SESIÓN EXPLÍCITO (LOGOUT) ===");
        // usuario 2 decide cerrar sesion voluntariamente antes de su tiempo de caducidad
        cache.logout("xyz789");
        System.out.println("========================================================\n");

        System.out.println("=== (4) EJECUCIÓN DEL RECOLECTOR DE EXPIRADOS ===");
        // limpieza masiva en lote para purgar los residuos de memoria 
        cache.cleanExpired();
        
        // cuantas sesiones quedan vivas al final de todo el flujo
        System.out.println("Sesiones remanentes activas en la caché: " + cache.getActiveSessionsCount());
        System.out.println("========================================================");
    }
}