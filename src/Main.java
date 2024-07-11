import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/Escuela";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Main app = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Sistema de Registro Estudiantil ---");
            System.out.println("1. Ingresar estudiante");
            System.out.println("2. Buscar estudiante por nombre");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del estudiante: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ingrese la edad del estudiante: ");
                    int edad = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Ingrese el curso del estudiante: ");
                    String curso = scanner.nextLine();

                    System.out.print("Ingrese la nota 1 del estudiante\n(con coma ej: 16,32): ");
                    double nota1 = scanner.nextDouble();

                    System.out.print("Ingrese la nota 2 del estudiante\n(con coma ej: 15,84): ");
                    double nota2 = scanner.nextDouble();

                    Estudiante estudiante = new Estudiante(nombre, edad, curso, nota1, nota2);
                    app.insertarEstudiante(estudiante);
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del estudiante a buscar: ");
                    String nombreBusqueda = scanner.nextLine();
                    app.buscarEstudiantePorNombre(nombreBusqueda);
                    break;

                case 3:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    public void insertarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO Estudiante (nombre, edad, curso, nota1, nota2) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estudiante.getNombre());
            pstmt.setInt(2, estudiante.getEdad());
            pstmt.setString(3, estudiante.getCurso());
            pstmt.setDouble(4, estudiante.getNota1());
            pstmt.setDouble(5, estudiante.getNota2());
            pstmt.executeUpdate();
            System.out.println("Estudiante insertado con éxito.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error al insertar el estudiante en la base de datos: " + e.getMessage());
        }
    }

    public Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Conectado a la base de datos");
        return conn;
    }

    public void buscarEstudiantePorNombre(String nombre) {
        String query = "SELECT * FROM Estudiante WHERE nombre = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("Nombre: " + resultSet.getString("nombre"));
                System.out.println("Edad: " + resultSet.getInt("edad"));
                System.out.println("Curso: " + resultSet.getString("curso"));
                System.out.println("Nota 1: " + resultSet.getDouble("nota1"));
                System.out.println("Nota 2: " + resultSet.getDouble("nota2"));
            } else {
                System.out.println("No se encontró un estudiante con el nombre " + nombre);
            }
        } catch (SQLException e1) {
            System.out.println(e1);
            System.out.println("Error al conectar a la base de datos: " + e1.getMessage());
        }
    }
}
