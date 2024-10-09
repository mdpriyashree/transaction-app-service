package com.example.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({RecordNotFound.class})
    public ResponseEntity<Object> handleNotFoundException(RecordNotFound exception) {
        return new ResponseEntity(errorMessage("Record Not found"), HttpStatus.NOT_FOUND);
    }

    private ErrorResponse errorMessage(String msg) {
        ErrorResponse error=new ErrorResponse();
        error.setErrorMessage(msg);
        return error;
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
