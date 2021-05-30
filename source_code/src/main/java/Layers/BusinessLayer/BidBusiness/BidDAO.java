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

    public BidDAO(Connection conn) {
        super(BID, false);
    }

    public boolean bid(BidEditDTO payload) {
        Connection conn = ConnectionFactory.getConnection();

        try(PreparedStatement ps = conn.prepareStatement("INSERT " +
                "INTO BID (description, amount,createTimestamp, auctionid, userid)" +
                "SELECT " +
                "?, ?, CURRENT_TIMESTAMP,?, ?" +
                "WHERE NOT EXISTS(" +
                "SELECT 1 FROM auction a " +
                "WHERE a.auctionid=auctionid " +
                "AND (a.currentbidvalue>=? OR CURRENT_TIMESTAMP < a.deleteTimestamp OR CURRENT_TIMESTAMP < a.endTimestamp)" +
                ");"))
        {
            ps.setString(1, payload.Description);
            ps.setFloat(2, payload.Amount);
            ps.setInt(3, payload.auctionId);
            ps.setInt(4, payload.userId);
            ps.setFloat(5, payload.Amount);

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