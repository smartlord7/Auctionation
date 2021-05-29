package Layers.PresentationLayer.Controllers;

import Layers.BusinessLayer.AuctionBusiness.AuctionDAO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionListDTO;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.UserBusiness.DTO.UserEditDTO;
import Startup.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final BaseDAO<AuctionEditDTO, AuctionListDTO> auctionDAO = new AuctionDAO(ConnectionFactory.getConnection());

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseBody
    public AuctionEditDTO create(@RequestBody UserEditDTO payload) {
        return (AuctionEditDTO) auctionDAO.create(payload);
    }

    @GetMapping(value = "/list", consumes = "application/json")
    @ResponseBody
    public List<AuctionListDTO> list() {
        return auctionDAO.getAll(null, null);
    }
}
