package Layers.BusinessLayer.StatsBusiness;


import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Layers.BusinessLayer.UserBusiness.DTO.UserListDTO;
import Layers.DataLayer.Enums.AuctionEnum;
import Startup.ConnectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatsDAO extends BaseDAO<StatsEditDTO, StatsListDTO> {
    public StatsDAO(Connection conn) {
        super("User", true);
    }


    public static StatsListDTO getStats() {
        Connection conn = ConnectionFactory.getConnection();
        StatsListDTO result = new StatsListDTO();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rows = stmt.executeQuery("" +
                    "SELECT userid, COUNT(*) aw " +
                    "FROM auction INNER JOIN bid ON (currentbidvalue = amount) AND (auction.auctionid=bid.auctionid)" +
                    "WHERE currentstate= 2 " +
                    "GROUP BY userid " +
                    "ORDER BY aw DESC " +
                    "limit 10");
            while (rows.next()) {
                result.topAuctionWinners.add(new StatsListDTO.UserAuction(rows.getInt("userid"),rows.getInt("aw")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet rows = stmt.executeQuery("" +
                    "SELECT hostuserid, COUNT(*) ac " +
                    "FROM auction " +
                    "GROUP BY hostuserid " +
                    "ORDER BY ac DESC " +
                    "limit 10;");
            while (rows.next()) {
                result.topAuctionOpeners.add(new StatsListDTO.UserAuction(rows.getInt("hostuserid"),rows.getInt("ac")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = conn.createStatement()) {
            ResultSet rows = stmt.executeQuery("" +
                    "SELECT COUNT(*) c " +
                    "FROM auction " +
                    "WHERE createtimestamp >= ( CURRENT_TIMESTAMP - INTERVAL '10 DAY') ");
            if (rows.next()) {
                result.totalLastAuctions=rows.getInt("c");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}


