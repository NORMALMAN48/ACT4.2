import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// clase que controla todo el menu
public class Menu {

    private static final BinaryTree<Integer> intTree = new BinaryTree<>();
    private static final BinaryTree<Employee> employeeTree = new BinaryTree<>();


    private static final String EMPLOYEE_FILE = "empleados.txt";

    public static void start() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("-----------------------------------------");
            System.out.println("[1] Integer Tree");
            System.out.println("[2] Employee Tree");
            System.out.println("0 exit");

            choice = readInt(sc, "Choose: ");

            if (choice == 1) {
                intTreeMenu(sc);
            } else if (choice == 2) {
                employeeTreeMenu(sc);
            } else if (choice == 0) {
                System.out.println("Closing...");
            } else {
                System.out.println("Invalid option.");
            }
        } while (choice != 0);

        sc.close();
    }

    private static void intTreeMenu(Scanner sc) {
        int choice;

        do {
            System.out.println("---- integer tree ----");
            System.out.println("[1] Insert value");
            System.out.println("[2] Search value");
            System.out.println("[3] Delete value");
            System.out.println("[4] Show preorder, inorder, postorder (traversal)");
            System.out.println("0 back");

            choice = readInt(sc, "Choose: ");

            if (choice == 1) {
                int value = readInt(sc, "Value to insert: ");
                intTree.insert(value);
                System.out.println("Inserted " + value + ".");
            } else if (choice == 2) {
                int value = readInt(sc, "Value to search: ");
                String result = intTree.locate(value);
                if (result == null) {
                    System.out.println("Not found.");
                } else {
                    System.out.println(result);
                }
            } else if (choice == 3) {
                int value = readInt(sc, "Value to delete: ");
                boolean found = intTree.search(value);
                if (found) {
                    intTree.delete(value);
                    System.out.println(value + " was found and has been deleted.");
                } else {
                    System.out.println(value + " was not found. Nothing was deleted.");
                }
            } else if (choice == 4) {
                showTraversals(intTree);
            } else if (choice == 0) {
                System.out.println("Going back...");
            } else {
                System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }



    private static void employeeTreeMenu(Scanner sc) {
        int choice;

        do {
            System.out.println("---- employee tree ----");
            System.out.println("[1] Insert employee");
            System.out.println("[2] Search employee");
            System.out.println("[3] Delete employee");
            System.out.println("[4] Show preorder, inorder, postorder (traversal)");
            System.out.println("[5] Load employees from " + EMPLOYEE_FILE);
            System.out.println("[6] Compare BinaryTree vs single list");
            System.out.println("0 back");

            choice = readInt(sc, "Choose: ");

            if (choice == 1) {
                insertEmployeeInteractively(sc);
            } else if (choice == 2) {
                int id = readInt(sc, "Id to search: ");
                String result = employeeTree.locate(Employee.withId(id));
                if (result == null) {
                    System.out.println("No employee with that id.");
                } else {
                    System.out.println(result);
                }
            } else if (choice == 3) {
                int id = readInt(sc, "Id to delete: ");
                Employee key = Employee.withId(id);
                boolean found = employeeTree.search(key);
                if (found) {
                    employeeTree.delete(key);
                    System.out.println("Employee " + id + " was found and has been deleted.");
                } else {
                    System.out.println("Employee " + id + " was not found. Nothing was deleted.");
                }
            } else if (choice == 4) {
                showTraversals(employeeTree);
            } else if (choice == 5) {
                loadEmployeesFromFile();
            } else if (choice == 6) {
                int count = readInt(sc, "How many employees to test with? ");
                SearchComparison.run(count);
            } else if (choice == 0) {
                System.out.println("Going back...");
            } else {
                System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    // pide id, nombre y rol por separado y mete el empleado nuevo al arbol.

    private static void insertEmployeeInteractively(Scanner sc) {
        int id = readInt(sc, "Employee id: ");
        Employee key = Employee.withId(id);

        if (employeeTree.search(key)) {
            System.out.println("Employee " + id + " already exists. Nothing was inserted.");
            return;
        }

        sc.nextLine(); // limpia el salto de linea que dejo pendiente readInt (que usa sc.next())
        System.out.print("Employee name: ");
        String name = sc.nextLine().trim();
        System.out.print("Employee role: ");
        String role = sc.nextLine().trim();

        employeeTree.insert(new Employee(id, name, role));
        System.out.println("Employee " + id + " was added.");
    }

    // lee el archivo de empleados (cada linea "id,name,role") y mete cada empleado
    private static void loadEmployeesFromFile() {
        File file = new File(EMPLOYEE_FILE);
        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File '" + EMPLOYEE_FILE + "' was not found.");
            return;
        }

        int loaded = 0;
        while (reader.hasNextLine()) {
            String line = reader.nextLine().trim();

            if (line.equals("")) {
                continue; // linea vacia, se ignora
            }

            String[] parts = line.split(",");
            if (parts.length != 3) {
                System.out.println("Malformed line, skipping: " + line);
                continue;
            }

            try {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String role = parts[2].trim();
                employeeTree.insert(new Employee(id, name, role));
                loaded++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid id, skipping line: " + line);
            }
        }

        reader.close();
        System.out.println("Loaded " + loaded + " employees from the file.");
    }

    // sirve para los dos arboles 
    private static <T extends Comparable<T>> void showTraversals(BinaryTree<T> tree) {
        System.out.println("---- preorder ----");
        tree.preorder();
        System.out.println("---- inorder ----");
        tree.inorder();
        System.out.println("---- postorder ----");
        tree.postorder();
    }

    // no deja pasar hasta que se escriba un entero valido
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String token = sc.next().trim();
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                System.out.println("'" + token + "' is not a valid int.");
            }
        }
    }
}
