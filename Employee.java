// objeto del caso practico. implementa Comparable para que BinaryTree<Employee>
// pueda ordenar/buscar/eliminar sin saber nada de "empleados" ni de "ids",
// nomas llama compareTo() y confia en lo que esta clase decida que significa eso
public class Employee implements Comparable<Employee> {

    private int id;
    private String name;
    private String role;

    public Employee(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    // esta es la unica linea que le importa al arbol: los empleados se ordenan por id
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ID " + id + " - " + name + " (" + role + ")";
    }

    // sirve para armar un empleado "de mentiras" cuando solo se tiene el id que se busca
    public static Employee withId(int id) {
        return new Employee(id, "", "");
    }
}
