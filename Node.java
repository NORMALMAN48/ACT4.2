// un nodo comun, lo comparten el arbol (BinaryTree) y la lista (singleList)
// si es arbol: usa data + leftChild/rightChild y next queda sin usar
// si es lista: usa data + next y los otros dos quedan sin usar
public class Node<T> {

    private T data;
    Node<T> leftChild;
    Node<T> rightChild;
    Node<T> next;

    // el constructor obliga a poner un valor, los punteros ya nacen en null
    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
