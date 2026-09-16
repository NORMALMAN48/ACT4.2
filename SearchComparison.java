// compara buscar un empleado en el BinaryTree contra buscarlo en singleList (uno por uno)
// esto es la evidencia de eficiencia que pide el caso de estudio
public class SearchComparison {

    public static void run(int n) {
        // se generan los ids ya alternados entre chicos y grandes (1000, luego el mas
        // grande, luego el segundo mas chico, etc). asi nunca quedan en orden ascendente
        // y el arbol no se arma como una fila derecha (que seria el peor caso)
        int[] ids = new int[n];
        int low = 0;
        int high = n - 1;
        boolean takeLow = true;
        for (int i = 0; i < n; i++) {
            if (takeLow) {
                ids[i] = 1000 + low;
                low++;
            } else {
                ids[i] = 1000 + high;
                high--;
            }
            takeLow = !takeLow;
        }

        singleList<Employee> list = new singleList<>();
        BinaryTree<Employee> tree = new BinaryTree<>();

        for (int i = 0; i < n; i++) {
            Employee e = new Employee(ids[i], "Employee" + ids[i], "Role" + (ids[i] % 10));
            list.addAt(0, e); // se mete al inicio, es mas rapido que ir hasta el final
            tree.insert(e);
        }

        // como todo se metio por el inicio, el primer id generado termino hasta el fondo
        // de la lista -> ese es el peor caso posible para buscar uno por uno desde el head
        Employee target = Employee.withId(ids[0]);

        long startList = System.nanoTime();
        Node<Employee> current = list.getHead();
        int stepsList = 0;
        while (current != null) {
            stepsList++;
            if (current.getData().compareTo(target) == 0) {
                break;
            }
            current = current.next;
        }
        long endList = System.nanoTime();

        long startTree = System.nanoTime();
        boolean found = tree.search(target);
        long endTree = System.nanoTime();

        System.out.println("With " + n + " employees:");
        System.out.println("  singleList (sequential): " + stepsList + " steps, " + (endList - startList) + " ns");
        System.out.println("  BinaryTree:               found=" + found + ", " + (endTree - startTree) + " ns");
    }
}
