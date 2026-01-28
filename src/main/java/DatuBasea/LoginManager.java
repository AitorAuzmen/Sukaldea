package DatuBasea;

import Util.Conn;
import model.Erabiltzailea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {

    // 'sukalde' rola rola_id = 3 dela suposatzen da
    private static final int SUKALDE_ROLA_ID = 3;

    public boolean login(Erabiltzailea user) {
        String query = "SELECT id, erabiltzailea, rola_id FROM erabiltzaileak WHERE erabiltzailea = ? AND pasahitza = ? AND ezabatua = 0";

        try (Connection conn = Conn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getErabiltzailea());
            pstmt.setString(2, user.getPasahitza());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int rolaId = rs.getInt("rola_id");
                    if (rolaId == SUKALDE_ROLA_ID) {
                        user.setId(rs.getInt("id"));
                        user.setRolaId(rolaId);
                        System.out.println("Login arrakastatsua: " + user.getErabiltzailea());
                        return true;
                    } else {
                        System.out.println("Erabiltzaileak ez du sukalde rola");
                    }
                } else {
                    System.out.println("Baliogabeko kredentzialak edo erabiltzailea ezabatuta");
                }
            }
        } catch (SQLException e) {
            System.err.println("Login kontsultan errorea: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
