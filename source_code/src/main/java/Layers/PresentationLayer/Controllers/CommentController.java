package Layers.PresentationLayer.Controllers;

import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Layers.BusinessLayer.CommentBusiness.CommentDAO;
import Layers.BusinessLayer.CommentBusiness.CommentEditDTO;
import Startup.ConnectionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private static final CommentDAO commentDAO = new CommentDAO(ConnectionFactory.getConnection());

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody CommentEditDTO payload) {
        return ResponseEntity.ok(((CommentEditDTO) commentDAO.create(payload)));
    }

}
