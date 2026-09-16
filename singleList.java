// lista ligada simple
public class singleList<T> {
    private Node<T> head;
    private int size;

    public singleList() {
        size = 0;
        head = null;
    }


    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        Node<T> tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = newNode;
        size++;
    }


    public void addAt(int index, T data) {
        if (index < 0 || index > size) {
            System.out.println("Index out of bounds");
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(data);
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node<T> tmp = head;
        for (int i = 0; i < index - 1; i++) {
            tmp = tmp.next;
        }
        Node<T> newNode = new Node<>(data);
        newNode.next = tmp.next;
        tmp.next = newNode;
        size++;
    }


    public void removeFirst() {
        if (head == null) {
            return;
        }
        head = head.next;
        size--;
    }


    public void removeLast() {
        if (head == null) {
            return;
        }
        if (head.next == null) {
            head = null;
            size--;
            return;
        }
        Node<T> tmp = head;
        while (tmp.next.next != null) {
            tmp = tmp.next;
        }
        tmp.next = null;
        size--;
    }

    public void removeAt(int index) {
        if (index < 0 || index >= size || head == null) {
            System.out.println("Index out of bounds");
            return;
        }
        if (index == 0) {
            removeFirst();
            return;
        }
        Node<T> tmp = head;
        for (int i = 0; i < index - 1; i++) {
            tmp = tmp.next;
        }
        tmp.next = tmp.next.next;
        size--;
    }

    public void editDataAt(int index, T data) {
        if (index < 0 || index >= size || head == null) {
            System.out.println("out of bounds");
            return;
        }
        Node<T> tmp = head;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        tmp.setData(data);
    }

    public void clear() {
        head = null;
        size = 0;
    }

    // arma un string con todo el contenido
    public String showContent() {
        if (head == null) {
            return "Empty list";
        }
        String text = "";
        Node<T> tmp = head;
        while (tmp != null) {
            text = text + tmp.getData();
            tmp = tmp.next;
        }
        return text;
    }

    public int getSize() {
        return size;
    }

    public Node<T> getHead() {
        return head;
    }
}
