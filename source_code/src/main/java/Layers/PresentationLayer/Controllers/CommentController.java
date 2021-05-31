package Layers.PresentationLayer.Controllers;

import Helpers.config.Authorization;
import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.AuctionBusiness.DTO.AuctionEditDTO;
import Layers.BusinessLayer.CommentBusiness.CommentDAO;
import Layers.BusinessLayer.CommentBusiness.CommentEditDTO;
import Startup.ConnectionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static Helpers.config.Authorization.ROLE_ADMIN;
import static Helpers.config.Authorization.ROLE_USER;

@RestController
@RequestMapping("comment")
public class CommentController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private static ErrorResponse errorResponse;
    private static final CommentDAO commentDAO = new CommentDAO(ConnectionFactory.getConnection(), errorResponse);

    @Authorization(roles = {ROLE_ADMIN, ROLE_USER})
    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody CommentEditDTO payload) {
        return ResponseEntity.ok(((CommentEditDTO) commentDAO.create(payload)));
    }
}
