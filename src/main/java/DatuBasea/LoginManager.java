package DatuBasea;

import Util.Conn;
import model.Erabiltzailea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {

    public boolean login(Erabiltzailea user) {
        String query = "SELECT id, erabiltzailea, rola_id FROM erabiltzaileak WHERE erabiltzailea = ? AND pasahitza = ? AND ezabatua = 0";
        
        try (Connection conn = Conn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, user.getErabiltzailea());
            pstmt.setString(2, user.getPasahitza());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setRolaId(rs.getInt("rola_id"));
                    System.out.println("Login exitoso para: " + user.getErabiltzailea());
                    return true;
                } else {
                    System.out.println("Credenciales inválidas o usuario eliminado");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta de login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
