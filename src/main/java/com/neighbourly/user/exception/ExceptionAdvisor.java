package com.neighbourly.user.exception;

import com.neighbourly.user.dto.ErrorResponse;
import com.neighbourly.user.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Response<ErrorResponse>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode("400");
        error.setErrorMessage("Missing required header: " + e.getHeaderName());
        Response<ErrorResponse> response = Response.<ErrorResponse>builder().data(error).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("400");
        errorResponse.setErrorMessage("Validation failed: " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        Response<ErrorResponse> response = Response.<ErrorResponse>builder().data(errorResponse).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDateFoundExceptipon(UserNotFoundException ex){
        ErrorResponse  errorResponse=new ErrorResponse(ex.getMessage(), "400");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400");
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
