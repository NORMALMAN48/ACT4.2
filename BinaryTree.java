// arbol binario de busqueda generico, solo guarda la raiz
public class BinaryTree<T extends Comparable<T>> {

    Node<T> root;

    // punto de entrada publico, asi no hay que pelear con la recursion desde afuera
    public void insert(T value) {
        root = insertHelper(root, value);
    }

    // como funciona esto:
    // - "current" es donde estamos parados ahorita
    // - si current es null ya no hay mas arbol para ese lado, entonces ahi va el nuevo dato
    // - si no, comparamos el valor y bajamos a la izq o a la der segun toque
    // - lo que regresa la llamada de abajo se vuelve a guardar en el hijo correspondiente
    //   (aunque ya existiera el hijo, no se pierde nada, solo se re-conecta)
    // - al final el caso base regresa el nodo nuevo y eso va subiendo hasta quedar enganchado
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

    // misma logica que insert pero nomas checando, no se agrega nada
    // caso base 1: current null -> se acabo el arbol -> no esta -> false
    // caso base 2: cmp 0 -> lo encontramos -> true
    // si no, seguimos bajando segun toque
    public boolean search(T value) {
        return searchHelper(root, value);
    }

    // igual que search pero regresa el detalle: en que profundidad esta, quien es su
    // padre y de que lado (izquierdo o derecho) cuelga. si no esta, regresa null
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

    // delete baja igual que insert pero al encontrar el nodo hay 3 casos:
    //   1. sin hijos      -> se regresa null y listo
    //   2. un solo hijo    -> ese hijo sube a ocupar el lugar
    //   3. dos hijos       -> no se puede borrar asi nomas porque se pierde un pedazo del arbol
    //        entonces buscamos el mas chico del subarbol derecho (el "sucesor"),
    //        copiamos su valor en el nodo actual y despues borramos el sucesor de mas abajo
    //        (el sucesor nunca tiene hijo izquierdo, asi que ese borrado siempre cae en caso 1 o 2)
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
            // caso 3: tiene los dos hijos, se busca el sucesor (el minimo del subarbol derecho)
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

    // los 3 recorridos son la misma recursion, nomas cambia el orden de las 3 lineas
    // preorder:  imprime -> va a la izq -> va a la der   (raiz primero)
    // inorder:   va a la izq -> imprime -> va a la der   (sale ordenado de menor a mayor)
    // postorder: va a la izq -> va a la der -> imprime   (raiz al final)

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
