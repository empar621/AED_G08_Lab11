package Act1;


//Clase que representa un registro con una clave entera y un dato genérico.

public class Register<T> {
    private int key; // Clave que se usará como índice/hash en la tabla
    private T data;  // Dato genérico asociado al registro

    // Constructor para inicializar el registro con clave y dato genérico
    public Register(int key, T data) {
        this.key = key;
        this.data = data;
    }

    // Retorna la clave del registro
    public int getKey() {
        return key;
    }

    // Retorna el dato genérico del registro
    public T getData() {
        return data;
    }

    // Representación en texto del objeto Register
    @Override
    public String toString() {
        return "(" + key + ", " + data + ")";
    }
}