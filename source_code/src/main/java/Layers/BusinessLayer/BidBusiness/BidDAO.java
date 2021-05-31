package Layers.BusinessLayer.BidBusiness;

import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.BidBusiness.DTO.BidEditDTO;
import Layers.BusinessLayer.BidBusiness.DTO.BidListDTO;
import Layers.DataLayer.Enums.AuctionEnum;
import Startup.ConnectionFactory;

import java.sql.*;

import static Layers.BusinessLayer.Base.TableNames.BID;

/**
 * DAO object to access and manipulate data about bids.
 */
public class BidDAO extends BaseDAO<BidEditDTO, BidListDTO>{

    public BidDAO() {
        super(BID, false);
    }

    /**
     * Function that creates a bid.
     * @param payload Data about the bid.
     * @return Returns the data about the bid.
     */
    public BidEditDTO bid(BidEditDTO payload) {
        Connection conn = ConnectionFactory.getConnection();

        try(PreparedStatement ps = conn.prepareStatement("INSERT " +
                "INTO BID (description, amount,createTimestamp, auctionid, userid)" +
                "SELECT " +
                "?, ?, CURRENT_TIMESTAMP,?, ?" +
                "WHERE NOT EXISTS(" +
                "SELECT 1 FROM auction a " +
                "WHERE a.auctionid=? AND a.deleteTimestamp IS NULL " +
                "AND (a.currentbidvalue>=? OR currentstate=? OR currentstate=?)" +
                ");", Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, payload.description);
            ps.setFloat(2, payload.amount);
            ps.setInt(3, payload.auctionId);
            ps.setInt(4, payload.userId);
            ps.setInt(5, payload.auctionId);
            ps.setFloat(6, payload.amount);
            ps.setInt(7, AuctionEnum.INTERRUPTED.getValue());
            ps.setInt(8, AuctionEnum.FINISHED.getValue());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                logger.info("Bid registered!");

                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next())
                {
                    payload.id = rs.getInt(1);
                }

                saveChanges(conn);
                return payload;
            } else {
                BidEditDTO dummy = new BidEditDTO();
                dummy.id = -1;

                return dummy;
            }
        } catch (SQLException e) {
            auditError(e, conn);
        }
        return null;
    }
}