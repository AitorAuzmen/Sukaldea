package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {

    private static final String URL = "jdbc:mysql://localhost:3306/tpv?useSSL=false&serverTimezone=UTC&autoReconnect=true&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1MG2024";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
            System.out.println("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error cargando driver MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                System.out.println("Conexión a la BD establecida correctamente");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Error en la conexión a la BD: " + e.getMessage());
            throw new SQLException("No se pudo conectar a la base de datos: " + e.getMessage(), e);
        }
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Prueba de conexión: EXITOSA");
            }
        } catch (SQLException e) {
            System.err.println("Prueba de conexión: FALLIDA - " + e.getMessage());
        }
    }
}
