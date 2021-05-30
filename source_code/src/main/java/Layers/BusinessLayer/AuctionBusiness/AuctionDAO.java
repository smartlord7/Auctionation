package Layers.BusinessLayer.AuctionBusiness;

import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionDetailsDTO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import Layers.BusinessLayer.Base.Subentity;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.BidBusiness.BidDAO;
import Layers.BusinessLayer.CommentBusiness.CommentDAO;
import Startup.ConnectionFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Layers.BusinessLayer.Base.TableNames.AUCTION;

public class AuctionDAO extends BaseDAO<AuctionEditDTO, AuctionListDTO> {
    public AuctionDAO(Connection conn) {
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
                    "WHERE (a.auctionId IN (SELECT DISTINCT b.auctionId" +
                    "                      FROM bid b" +
                    "                      WHERE b.userId = ?) OR a.hostuserId = ?) AND" +
                    "      deleteTimestamp IS NULL";


        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            rows = ps.executeQuery();

            while (rows.next()) {
                result.add(new AuctionListDTO(rows.getInt(1), rows.getString(2)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            if (!auctionFields[i].isAnnotationPresent(Subentity.class)) {
                sb.append(auctionFields[i].getName()).append(", ");
            }
        }
        sb.append(auctionFields[i].getName())
                .append(" FROM ")
                .append(AUCTION)
                .append(" WHERE ")
                .append(AUCTION)
                .append(" Id = ? AND")
                .append("deleteTimestamp IS NOT NULL");

        try {
            ps = conn.prepareStatement(sb.toString());
            rows = ps.executeQuery();

            if (rows.next()) {
                for (i = 0; i < auctionFields.length; i++) {
                    auctionFields[i].set(dto, rows.getObject(i + 1));
                }
            }

        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }

        dto.bidHistory = new BidDAO().getbyProp("auctionId", Integer.toString(auctionId));
        dto.comments = new CommentDAO().getbyProp("auctionId", Integer.toString(auctionId));

        return dto;
    }
}
