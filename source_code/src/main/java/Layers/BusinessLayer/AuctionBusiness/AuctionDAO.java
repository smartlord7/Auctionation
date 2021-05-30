package Layers.BusinessLayer.AuctionBusiness;

import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import Layers.BusinessLayer.Base.BaseDAO;
import Startup.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuctionDAO extends BaseDAO<AuctionEditDTO, AuctionListDTO> {
    public AuctionDAO(Connection conn) {
        super("Auction", true);
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
}
