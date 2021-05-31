package Layers.BusinessLayer.AuctionBusiness;

import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionDetailsDTO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import Layers.BusinessLayer.Base.Subentity;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.BidBusiness.BidDAO;
import Layers.BusinessLayer.CommentBusiness.CommentDAO;
import Layers.BusinessLayer.CommentBusiness.CommentEditDTO;
import Startup.ConnectionFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static Layers.BusinessLayer.Base.TableNames.AUCTION;
import static Layers.DataLayer.Enums.AuctionEnum.FINISHED;
import static Layers.DataLayer.Enums.AuctionEnum.INTERRUPTED;

/**
 * @Class DAO object used to access and modify data related to the auction entity.
 */
public class AuctionDAO extends BaseDAO<AuctionEditDTO, AuctionListDTO> {

    private static final String AUCTION_COMMENT_CANCEL = "We are sorry to inform that this auction is no longer available.";

    /**
     * Constructor.
     */
    public AuctionDAO() {
        super(AUCTION, true);
    }

    /**
     * Function that gets a list of all of the auctions where the given user is involved.
     * @param userId ID of the user.
     * @return List of AuctionListDTO objects that contains all of the auctions where the user has been or is involved.
     */
    public List<AuctionListDTO> getAllByUser(int userId) {
        PreparedStatement ps;
        Connection conn = ConnectionFactory.getConnection();
        ResultSet rows;
        List<AuctionListDTO> result;

        //construct the query structure
        result = new ArrayList<>();
        String query = "SELECT a.auctionId, a.description " +
                    "FROM auction a " +
                    "WHERE a.hostuserId = ? OR EXISTS (SELECT DISTINCT b.auctionId" +
                    "                      FROM bid b" +
                    "                      WHERE b.userId = ?) " +
                    "OR a.auctionId IN (SELECT DISTINCT c.auctionId" +
                    "                   FROM comment c " +
                    "                   WHERE c.userId = ?)" +
                    "AND deleteTimestamp IS NULL";

        try {
            //fill the data required for the query
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);

            //execute the query
            rows = ps.executeQuery();

            //put all of the auctions obtained in the list
            while (rows.next()) {
                result.add(new AuctionListDTO(rows.getInt(1), rows.getString(2)));
            }

        } catch (SQLException e) {
            auditError(e, conn);
        }

        return result;
    }

    /**
     * Function that gets the details about a certain auction.
     * @param auctionId ID of the auction.
     * @return Returns a AuctionDetailsDTO object that contains info about the auction if it exists.
     *         Returns null if the auction does not exist.
     */
    public AuctionDetailsDTO getDetails(int auctionId) {
        int i ;
        PreparedStatement ps;
        Connection conn;
        ResultSet rows;
        AuctionDetailsDTO dto;
        StringBuilder sb;
        Field[] auctionFields;

        conn = ConnectionFactory.getConnection();
        dto = new AuctionDetailsDTO();
        auctionFields = AuctionDetailsDTO.class.getDeclaredFields();
        sb = new StringBuilder("SELECT ");

        //Construct the query structure
        for (i = 0; i < auctionFields.length - 1; i++) {
            if (auctionFields[i].isAnnotationPresent(Subentity.class)) {
                continue;
            }
            sb.append(auctionFields[i].getName()).append(",");

        }

        if (!auctionFields[i].isAnnotationPresent(Subentity.class)) {
            sb.append(auctionFields[i].getName());
        }

        if (sb.charAt(sb.length() - 1) == ',') {
            sb.replace(sb.length() - 1, sb.length(), "");
        }
                sb.append(" FROM ")
                .append(AUCTION)
                .append(" WHERE ")
                .append(AUCTION)
                .append("Id = ? AND ")
                .append("deleteTimestamp IS NULL");

        try {
            //prepare and fill the data required by the query
            ps = conn.prepareStatement(sb.toString());
            ps.setInt(1, auctionId);

            //execute the query
            rows = ps.executeQuery();

            if (rows.next()) {
                for (i = 0; i < auctionFields.length; i++) {
                    //check if the field isn't an entity or an enumeration
                    if (auctionFields[i].isAnnotationPresent(Subentity.class)) {
                        continue;
                    }
                    auctionFields[i].set(dto, rows.getObject(i + 1));
                }
                //obtain any previous bids and comments related to the auction if they exist
                dto.bidHistory = new BidDAO().getbyProp("auctionId", Integer.toString(auctionId));
                dto.comments = new CommentDAO().getbyProp("auctionId", Integer.toString(auctionId));
            } else {
                return null;
            }

        } catch (SQLException | IllegalAccessException e) {
            auditError(e, conn);
        }


        return dto;
    }

    /**
     * Function that terminates a given auction.
     * @param id ID associated to the auction.
     * @return Returns an AuctionEndDTO object that contains data about the terminated auction.
     */
    public AuctionEndDTO terminateById(int id) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rows;

        //construct the query structure
        sb.append("Auction").append( " SET ");
        sb.append("currentState = ?, endTimestamp = ? WHERE ");
        sb.append(tableName).append("Id = ?");

        try {
            //prepare and fill the data required by the query
            ps = conn.prepareStatement(sb.toString());

            ps.setInt(1, FINISHED.ordinal());
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.setInt(3, id);

            //update the auction info
            int numAffectedRows = ps.executeUpdate();

            if (numAffectedRows == 1) {
                logger.info("Auction with ID " + id + " successfully terminated!");

                //construct the query to obtain data about the winning bid
                String query = "SELECT u.username, b.amount, b.userid, b.bidid, b.auctionid " +
                        "FROM \"user\" u , auction a INNER JOIN bid b ON (a.currentbidvalue = b.amount) AND (a.auctionid=b.auctionid)" +
                        "WHERE currentstate=2 and b.userid=u.userid";

                try {
                    //get the winning bid
                    ps = conn.prepareStatement(query);
                    rows = ps.executeQuery();

                    while (rows.next()) {
                        //commit the update
                        saveChanges(conn);

                        //return the info obtained in the previous query
                        return new AuctionEndDTO(rows.getString(1), rows.getFloat(2),
                                rows.getInt(3), rows.getInt(4), rows.getInt(5));
                    }

                } catch (SQLException e) {
                    auditError(e, conn);
                }

                return null;
            }
        } catch (SQLException e) {
            auditError(e, conn);
        }

        return null;
    }

    /**
     * Function that cancels a given auction.
     * @param auctionId ID of the auction.
     * @param adminId ID of the admin who whats to cancel the auction.
     * @return true if the auction was successfully terminated, else returns false;
     */
    public boolean cancelById(int auctionId, int adminId) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;
        CommentDAO commentDAO = new CommentDAO();
        CommentEditDTO commentEditDTO = new CommentEditDTO();

        //construct the query structure
        sb.append("Auction").append( " SET ");
        sb.append("currentState = ?, endTimestamp = ? WHERE ");
        sb.append(tableName).append("Id = ?");

        try {
            ps = conn.prepareStatement(sb.toString());

            //fill the data required for the query
            ps.setInt(1, INTERRUPTED.ordinal());
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.setInt(3, auctionId);

            //execute the update and commit
            int numAffectedRows = ps.executeUpdate();
            saveChanges(conn);

            if (numAffectedRows == 1) {
                //construct a comment to notify all of the users involved in the auction
                logger.info("Auction with ID " + auctionId + " successfully canceled!");

                commentEditDTO.text = AUCTION_COMMENT_CANCEL;
                commentEditDTO.userId = adminId;

                commentDAO.create(commentEditDTO);

                return true;
            }

            return false;

        } catch (SQLException e) {
            auditError(e, conn);
        }

        return false;
    }

    /**
     * Function that updates a given auction.
     * @param dto DTO object that contains the data required for the update.
     * @param id ID of the auction.
     * @return
     */
    @Override
    public Layers.BusinessLayer.Base.DTO.BaseEditDTO updateById(Layers.BusinessLayer.Base.DTO.BaseEditDTO dto, int id){
        AuctionEditDTO editDTO = (AuctionEditDTO) dto;
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;

        //construct the query
        String query = "SELECT * FROM auction a WHERE a.hostuserid = ? AND a.auctionid = ?";

        try {
            ps = conn.prepareStatement(query);

            //fill the data required for the query
            ps.setInt(1, editDTO.hostUserId);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                return super.updateById(editDTO, id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            error = new ErrorResponse(e.getSQLState(), e.getMessage());
        }

        return null;
    }

}
