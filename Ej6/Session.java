package Ej6;

public class Session {
    private String token;
    private String username;
    private String role;
    private long expiresAt; // Timestamp Unix en milisegundos

    public Session(String token, String username, String role, long expiresAt) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

     //Verifica si la sesión actual ha expirado comparándola con el tiempo del sistema.
   
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }

    @Override
    public String toString() {
        long timeLeft = expiresAt - System.currentTimeMillis();
        return String.format("[Token: %s | Usuario: %s | Rol: %s | TTL restante: %d ms]", 
                             token, username, role, timeLeft > 0 ? timeLeft : 0);
    }
}