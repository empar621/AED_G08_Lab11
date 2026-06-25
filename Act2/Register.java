package Act2;

public class Register<T> {
    private int key;
    private T data;

    public Register(int key, T data) {
        this.key = key;
        this.data = data;
    }

    public int getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + data + ")";
    }
}