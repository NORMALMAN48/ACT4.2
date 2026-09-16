
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

    // esta es la unica linea que le importa al arbol
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ID " + id + " - " + name + " (" + role + ")";
    }

    // sirve para armar un empleado temp
    public static Employee withId(int id) {
        return new Employee(id, "", "");
    }
}
