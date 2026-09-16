// arbol binario de busqueda generico, solo guarda la raiz
public class BinaryTree<T extends Comparable<T>> {

    Node<T> root;

    // punto de entrada publico
    public void insert(T value) {
        root = insertHelper(root, value);
    }

    private Node<T> insertHelper(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value); // aqui estaba vacio, entra el nodo nuevo
        }

        int cmp = value.compareTo(current.getData());
        if (cmp < 0) {
            current.leftChild = insertHelper(current.leftChild, value);
        } else if (cmp > 0) {
            current.rightChild = insertHelper(current.rightChild, value);
        }
        // si cmp es 0 el valor ya existe, no se hace nada (no se permiten duplicados)

        return current;
    }

    public boolean search(T value) {
        return searchHelper(root, value);
    }

    public String locate(T value) {
        return locateHelper(root, value, null, 0);
    }

    private String locateHelper(Node<T> current, T value, Node<T> parent, int depth) {
        if (current == null) {
            return null; // no esta
        }

        int cmp = value.compareTo(current.getData());
        if (cmp == 0) {
            if (parent == null) {
                return "Found at depth " + depth + " (root).";
            }
            if (parent.leftChild == current) {
                return "Found at depth " + depth + " (left child of " + parent.getData() + ").";
            } else {
                return "Found at depth " + depth + " (right child of " + parent.getData() + ").";
            }
        } else if (cmp < 0) {
            return locateHelper(current.leftChild, value, current, depth + 1);
        } else {
            return locateHelper(current.rightChild, value, current, depth + 1);
        }
    }

    private boolean searchHelper(Node<T> current, T value) {
        if (current == null) {
            return false;
        }
        int cmp = value.compareTo(current.getData());
        if (cmp == 0) {
            return true;
        }
        if (cmp < 0) {
            return searchHelper(current.leftChild, value);
        } else {
            return searchHelper(current.rightChild, value);
        }
    }

    public void delete(T value) {
        root = deleteHelper(root, value);
    }

    private Node<T> deleteHelper(Node<T> current, T value) {
        if (current == null) {
            return null; // no estaba, no hay nada que borrar
        }

        int cmp = value.compareTo(current.getData());
        if (cmp < 0) {
            current.leftChild = deleteHelper(current.leftChild, value);
        } else if (cmp > 0) {
            current.rightChild = deleteHelper(current.rightChild, value);
        } else {
            // este es el nodo que hay que quitar
            if (current.leftChild == null) {
                return current.rightChild; // caso 1 o 2 por el lado derecho
            }
            if (current.rightChild == null) {
                return current.leftChild; // caso 2 por el lado izquierdo
            }
            // tiene los dos hijos
            Node<T> successor = findMin(current.rightChild);
            current.setData(successor.getData());
            current.rightChild = deleteHelper(current.rightChild, successor.getData());
        }
        return current;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }


    public void preorder() {
        preorderHelper(root);
    }

    private void preorderHelper(Node<T> current) {
        if (current == null) return;
        System.out.println(current.getData());
        preorderHelper(current.leftChild);
        preorderHelper(current.rightChild);
    }

    public void inorder() {
        inorderHelper(root);
    }

    private void inorderHelper(Node<T> current) {
        if (current == null) return;
        inorderHelper(current.leftChild);
        System.out.println(current.getData());
        inorderHelper(current.rightChild);
    }

    public void postorder() {
        postorderHelper(root);
    }

    private void postorderHelper(Node<T> current) {
        if (current == null) return;
        postorderHelper(current.leftChild);
        postorderHelper(current.rightChild);
        System.out.println(current.getData());
    }
}
