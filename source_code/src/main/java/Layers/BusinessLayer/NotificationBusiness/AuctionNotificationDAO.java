package Layers.BusinessLayer.NotificationBusiness;

import Layers.BusinessLayer.AuctionEditHistoryBusiness.AuctionEditHistoryListDTO;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.NotificationBusiness.DTO.AuctionNotificationEditDTO;

import java.sql.Connection;

/**
 * DAO object to access data of an auction notification.
 */
public class AuctionNotificationDAO extends BaseDAO<AuctionNotificationEditDTO, AuctionEditHistoryListDTO> {
    public AuctionNotificationDAO(Connection conn) {
        super("AuctionNotification", true);
    }
}