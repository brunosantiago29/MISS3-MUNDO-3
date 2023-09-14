// Classe SequenceManager
package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {
    public static int getValue(String sequenceName) {
        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT NEXTVAL('" + sequenceName + "')");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }
}
