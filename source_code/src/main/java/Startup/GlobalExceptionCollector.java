package Startup;

import Layers.BusinessLayer.ExceptionBusiness.DTO.ExceptionEditDTO;
import Layers.BusinessLayer.ExceptionBusiness.ExceptionDAO;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionCollector {

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public ResponseEntity<Object> handleValidationException(final Exception ex) throws SQLException, IllegalAccessException {
        Connection conn = ConnectionFactory.getConnection();

        if (ex.getClass() == PSQLException.class) {
            SQLException sqlEx = (PSQLException) ex;
            ExceptionDAO exDAO = new ExceptionDAO(conn);
            ExceptionEditDTO dto = new ExceptionEditDTO(sqlEx.getErrorCode(), sqlEx.getSQLState(), sqlEx.getMessage(), Timestamp.from(Instant.now()));
            exDAO.create(dto);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
