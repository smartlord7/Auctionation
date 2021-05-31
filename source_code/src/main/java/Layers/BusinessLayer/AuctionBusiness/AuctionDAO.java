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
import com.jayway.jsonpath.Configuration;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static Layers.BusinessLayer.Base.TableNames.AUCTION;
import static Layers.DataLayer.Enums.AuctionEnum.FINISHED;
import static Layers.DataLayer.Enums.AuctionEnum.INTERRUPTED;

public class AuctionDAO extends BaseDAO<AuctionEditDTO, AuctionListDTO> {

    private static final String AUCTION_COMMENT_CANCEL = "We are sorry to inform that this auction is no longer available.";

    public AuctionDAO() {
        super(AUCTION, true);
    }

    public List<AuctionListDTO> getAllByUser(int userId) {
        PreparedStatement ps;
        Connection conn = ConnectionFactory.getConnection();
        ResultSet rows;
        List<AuctionListDTO> result;

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
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            rows = ps.executeQuery();

            while (rows.next()) {
                result.add(new AuctionListDTO(rows.getInt(1), rows.getString(2)));
            }

        } catch (SQLException e) {
            auditError(e, conn);
        }

        return result;
    }

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
            ps = conn.prepareStatement(sb.toString());
            ps.setInt(1, auctionId);
            rows = ps.executeQuery();

            if (rows.next()) {
                for (i = 0; i < auctionFields.length; i++) {
                    if (auctionFields[i].isAnnotationPresent(Subentity.class)) {
                        continue;
                    }
                    auctionFields[i].set(dto, rows.getObject(i + 1));
                }
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

    public AuctionEndDTO terminateById(int id) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rows;

        sb.append("Auction").append( " SET ");
        sb.append("currentState = ?, endTimestamp = ? WHERE ");
        sb.append(tableName).append("Id = ?");

        try {
            ps = conn.prepareStatement(sb.toString());

            ps.setInt(1, FINISHED.ordinal());
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.setInt(3, id);

            int numAffectedRows = ps.executeUpdate();

            if (numAffectedRows == 1) {
                logger.info("Auction with ID " + id + " successfully terminated!");

                String query = "SELECT u.username, b.amount, b.userid, b.bidid, b.auctionid " +
                        "FROM \"user\" u , auction a INNER JOIN bid b ON (a.currentbidvalue = b.amount) AND (a.auctionid=b.auctionid)" +
                        "WHERE currentstate=2 and b.userid=u.userid";

                try {
                    ps = conn.prepareStatement(query);
                    rows = ps.executeQuery();

                    while (rows.next()) {
                        saveChanges(conn);
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

    public boolean cancelById(int auctionId, int adminId) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps;
        CommentDAO commentDAO = new CommentDAO();
        CommentEditDTO commentEditDTO = new CommentEditDTO();

        sb.append("Auction").append( " SET ");
        sb.append("currentState = ?, endTimestamp = ? WHERE ");
        sb.append(tableName).append("Id = ?");

        try {
            ps = conn.prepareStatement(sb.toString());

            ps.setInt(1, INTERRUPTED.ordinal());
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.setInt(3, auctionId);

            int numAffectedRows = ps.executeUpdate();

            if (numAffectedRows == 1) {
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

}
