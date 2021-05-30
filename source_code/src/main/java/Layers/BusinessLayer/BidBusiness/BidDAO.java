package Layers.BusinessLayer.BidBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.BidBusiness.DTO.BidEditDTO;
import Layers.BusinessLayer.BidBusiness.DTO.BidListDTO;
import Startup.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Layers.BusinessLayer.Base.TableNames.BID;

public class BidDAO extends BaseDAO<BidEditDTO, BidListDTO>{

    public BidDAO() {
        super(BID, true);
    }

    public boolean bid(String description, float amount, int auctionid, int userid) {
        Connection conn = ConnectionFactory.getConnection();

        try(PreparedStatement ps = conn.prepareStatement("INSERT " +
                "INTO BID (description, amount, auctionid, userid)" +
                "SELECT " +
                "?, ?, ?, ?" +
                "WHERE NOT EXISTS(" +
                "SELECT 1 FROM auction a " +
                "WHERE a.auctionid=auctionid " +
                "AND (a.currentbidvalue>=? OR CURRENT_TIMESTAMP < a.deleteTimestamp)" +
                ");"))
        {
            ps.setString(1, description);
            ps.setFloat(2, amount);
            ps.setInt(3, auctionid);
            ps.setInt(4, userid);
            ps.setFloat(5, amount);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                saveChanges(conn);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
