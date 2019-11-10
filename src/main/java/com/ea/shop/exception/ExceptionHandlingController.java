package com.ea.shop.exception;

import com.ea.shop.message.MetaMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> exceptionControl(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse();

        MetaMessage metaMessage = new MetaMessage();
        metaMessage.setMessage(ex.getMessage());
        metaMessage.setSeverity(MetaMessage.SEVERITY.ERROR.getDescription());

        response.addMessage(metaMessage);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> businessExceptionControl(BusinessException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorList(ex.getErrorList());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}