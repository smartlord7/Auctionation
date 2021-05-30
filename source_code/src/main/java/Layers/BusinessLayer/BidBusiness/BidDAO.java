package Layers.BusinessLayer.BidBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.BidBusiness.DTO.BidEditDTO;
import Layers.BusinessLayer.BidBusiness.DTO.BidListDTO;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class BidDAO extends BaseDAO<BidEditDTO, BidListDTO>{

    public BidDAO(Connection conn) {
        super(conn, "Bid", true);
    }


    public boolean bid(String description, float amount, int auctionid, int userid) {
        try(PreparedStatement ps = conn.prepareStatement("INSERT " +
                "INTO BID (description, amount, auctionid,userid)" +
                "SELECT " +
                "?,?,?,?" +
                "WHERE NOT EXISTS(" +
                "SELECT 1 FROM auction au" +
                "WHERE au.auctionid=auctionid" +
                "AND (au.currentbidvalue>=? OR CURRENT_TIMESTAMP<au.deleteTimestamp)" +
                ");"))
        {
            ps.setString(1, description);
            ps.setFloat(2, amount);
            ps.setInt(3, auctionid);
            ps.setInt(4, userid);
            ps.setFloat(5, amount);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                saveChanges();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
