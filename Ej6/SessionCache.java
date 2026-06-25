package Ej6;

public class SessionCache {

    private static class Node {
        Session session;
        Node next;

        Node(Session session) {
            this.session = session;
            this.next = null;
        }
    }

    private Node[] table;
    private int size;

    public SessionCache(int size) {
        this.size = size;
        this.table = new Node[size]; // inicializado implicitamente  null
    }

    private int hash(String token) {
        return Math.abs(token.hashCode()) % size; // 
    }

    /**
     * 1. Registra una nueva sesión con un tiempo de vida (TTL) específico.
     */
    public void login(String token, String username, String role, long ttlMs) {
        long expiresAt = System.currentTimeMillis() + ttlMs; 
        Session newSession = new Session(token, username, role, expiresAt);
        int index = hash(token);

        Node newNode = new Node(newSession);
        
        // insercion al inicio (O(1))
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            // control preventivo de duplicacion de token
            Node current = table[index];
            while (current != null) {
                if (current.session.getToken().equals(token)) {
                    current.session = newSession; 
                    return;
                }
                current = current.next;
            }
            newNode.next = table[index];
            table[index] = newNode;
        }
        System.out.println("-> Login exitoso para el usuario: " + username + " (Token: " + token + ")");
    }

    /**
     * 2. Retorna la sesión si existe y no ha expirado; retorna null en caso contrario.
     */
    public Session validate(String token) {
        int index = hash(token);
        Node current = table[index];

        while (current != null) {
            if (current.session.getToken().equals(token)) {
                if (current.session.isExpired()) { 
                    System.out.println("-> Validación fallida: El token " + token + " ya expiró.");
                    return null; 
                }
                return current.session; 
            }
            current = current.next;
        }
        System.out.println("-> Validación fallida: El token " + token + " no existe.");
        return null; 
    }

    /**
     * 3. Elimina explícitamente la sesión de la caché 
     */
    public void logout(String token) {
        int index = hash(token);
        Node current = table[index];
        Node prev = null;

        while (current != null) {
            if (current.session.getToken().equals(token)) {
                if (prev == null) {
                    table[index] = current.next; // nodo a eliminar era la cabeza
                } else {
                    prev.next = current.next; // salta el nodo intermedio
                }
                System.out.println("-> Logout exitoso: Sesión del token " + token + " destruida."); 
                return;
            }
            prev = current;
            current = current.next;
        }
        System.out.println("-> Logout fallido: No se encontró sesión activa para el token " + token);
    }

    /**
     * 4. Recorre toda la tabla y purga físicamente las sesiones que caducaron.
     */
    public void cleanExpired() {
        long now = System.currentTimeMillis(); 
        int purgedCount = 0;

        for (int i = 0; i < size; i++) {
            Node current = table[i];
            Node prev = null;

            while (current != null) {
                if (current.session.getExpiresAt() < now) { 
                    purgedCount++;
                    if (prev == null) {
                        table[i] = current.next; // eemueve la cabeza de la lista
                        current = table[i];
                    } else {
                        prev.next = current.next; // sesvincula el nodo interno
                        current = current.next;
                    }
                } else {
                    prev = current;
                    current = current.next;
                }
            }
        }
        System.out.println("-> [Mantenimiento]: cleanExpired() ejecutado. Se purgaron " + purgedCount + " sesiones obsoletas de la memoria.");
    }

    /**
     * Cuenta de forma exacta el número de sesiones que permanecen activas en la caché.
     */
    public int getActiveSessionsCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            Node current = table[i];
            while (current != null) {
                if (!current.session.isExpired()) {
                    count++;
                }
                current = current.next;
            }
        }
        return count;
    }
}