package com.innogent.jpaCRUD.exceptionHandler;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataAccessException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<String> handleMultipleException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error occurred! Error Message : "+ex.getMessage());
    }
    
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex){
    	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Null Data! Error Message : "+ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException ex){
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Illegal Argument, Please check again!Error Message : "+ex.getMessage());
    }
}