package DatuBasea;

import Util.Conn;
import model.Eskaera;
import model.EskaeraItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EskaeraDAO {

    public List<Eskaera> kargatuEskaerak() {
        List<Eskaera> eskaerak = new ArrayList<>();
        String sql = "SELECT e.id, m.zenbakia AS mahaia_zenb, e.sortze_data " +
                     "FROM eskaerak e " +
                     "LEFT JOIN mahaiak m ON e.mahaia_id = m.id " +
                     "ORDER BY e.sortze_data DESC LIMIT 50";

        try (Connection c = Conn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Integer mahaiaZenb = rs.getObject("mahaia_zenb") != null ? rs.getInt("mahaia_zenb") : null;
                Timestamp ts = rs.getTimestamp("sortze_data");
                LocalDateTime sortzeData = ts != null ? ts.toLocalDateTime() : null;
                List<EskaeraItem> items = kargatuEskaerarenProduktua(id);
                eskaerak.add(new Eskaera(id, mahaiaZenb, sortzeData, items));
            }
        } catch (SQLException e) {
            System.err.println("Errorea eskaerak kargatzean: " + e.getMessage());
        }
        return eskaerak;
    }

    public List<EskaeraItem> kargatuEskaerarenProduktua(int eskaeraId) {
        List<EskaeraItem> items = new ArrayList<>();
        String sql = "SELECT p.izena, ep.kantitatea " +
                     "FROM eskaera_produktuak ep " +
                     "JOIN produktuak p ON ep.produktua_id = p.id " +
                     "WHERE ep.eskaera_id = ?";

        try (Connection c = Conn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, eskaeraId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new EskaeraItem(
                            rs.getString("izena"),
                            rs.getInt("kantitatea")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Errorea eskaeraren produktuak kargatzean: " + e.getMessage());
        }
        return items;
    }

    public int lortuAzkenEskaeraId() {
        String sql = "SELECT id FROM eskaerak ORDER BY id DESC LIMIT 1";
        try (Connection c = Conn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Errorea azken eskaera lortzean: " + e.getMessage());
        }
        return 0;
    }

    public void ezabatuEskaera(int eskaeraId) {
        String sql = "DELETE FROM eskaerak WHERE id = ?";
        try (Connection c = Conn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, eskaeraId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errorea eskaera ezabatzean: " + e.getMessage());
        }
    }

}
