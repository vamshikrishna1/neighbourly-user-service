package com.neighbourly.user.exception;

import com.neighbourly.user.dto.Error;
import com.neighbourly.user.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Response<Error>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        Error error = new Error();
        error.setErrorCode("400");
        error.setErrorMessage("Missing required header: " + e.getHeaderName());
        Response<Error> response = Response.<Error>builder().data(error).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Error>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Error errorResponse = new Error();
        errorResponse.setErrorCode("400");
        errorResponse.setErrorMessage("Validation failed: " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        Response<Error> response = Response.<Error>builder().data(errorResponse).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleNoDateFoundExceptipon(UserNotFoundException ex){
        Error errorResponse=new Error(ex.getMessage(), "400");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Error errorResponse = new Error(ex.getMessage(), "400");
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
