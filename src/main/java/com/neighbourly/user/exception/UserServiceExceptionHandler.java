package com.neighbourly.user.exception;

import com.neighbourly.user.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {
private final Logger LOGGER = LoggerFactory.getLogger(UserServiceExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDateFoundExceptipon(UserNotFoundException ex){
        LOGGER.error(ex.getMessage(),ex);
        ErrorResponse  errorResponse=new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }


}
