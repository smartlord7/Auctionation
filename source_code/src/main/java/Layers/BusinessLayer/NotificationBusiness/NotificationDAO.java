package Layers.BusinessLayer.NotificationBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.NotificationBusiness.DTO.NotificationEditDTO;
import Layers.BusinessLayer.NotificationBusiness.DTO.NotificationListDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO extends BaseDAO<NotificationEditDTO, NotificationListDTO> {
    public NotificationDAO(Connection conn) {
        super(conn, "Notification", true);
    }

    public List<NotificationListDTO> listByUser(int userId) {
        int i = 0;
        List<NotificationListDTO> result = new ArrayList<>();
        ResultSet rows;
        PreparedStatement ps;
        String query = "SELECT notificationTitle, description,  notificationid, userId, createTimestamp " +
                "FROM Notification n " +
                "WHERE n.userId = ? AND n.deleteTimestamp IS NULL AND seenTimestamp IS NULL;" +
                "UPDATE Notification " +
                "SET updateTimestamp = CURRENT_TIMESTAMP, " +
                "    seenTimestamp = CURRENT_TIMESTAMP " +
                "WHERE userId = ? AND deleteTimestamp IS NULL";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            rows = ps.executeQuery();

            while (rows.next()) {
                result.add(new NotificationListDTO(rows.getString(1), rows.getString(2),
                        rows.getInt(3), rows.getInt(4), rows.getTimestamp(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}