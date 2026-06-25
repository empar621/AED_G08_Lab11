package Act2;


public class LinkedList<E> {

    public static class Node<E> {
        public E data;
        public Node<E> next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E> head; 
    public LinkedList() {
        this.head = null;
    }

    public Node<E> getHead() {
        return head;
    }

    public void insertLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public boolean removeNode(Node<E> targetNode) {
        if (head == null || targetNode == null) return false;

        if (head == targetNode) {
            head = head.next;
            return true;
        }

        Node<E> current = head;
        while (current.next != null && current.next != targetNode) {
            current = current.next;
        }

        if (current.next == targetNode) {
            current.next = targetNode.next;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }
}