package com.pinterestscheduler.pinterest.Exception;

import com.pinterestscheduler.pinterest.DTO.ResponseDTO.ErrorDetailsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(PinterestApiException.class)
    public ResponseEntity<ErrorDetailsDto> handlePinterestApiException(PinterestApiException exception,
                                                                        WebRequest webRequest) {
        String[] errorStatusCodeAndMessage = extractErrorCodeAndMessage(exception.getMessage());
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(), errorStatusCodeAndMessage[1], webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(Integer.parseInt(errorStatusCodeAndMessage[0])));

    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorDetailsDto> handleInvalidInputException(InvalidInputException exception, WebRequest webRequest) {
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                           WebRequest webRequest) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private String[] extractErrorCodeAndMessage(String errorMessage) {
        String[] errorDetails = new String[2];
        String[] error = errorMessage.split(":", 2);
        errorDetails[0] = error[0].split(" ",2)[0];
        errorDetails[1] = error[1].trim();
        return errorDetails;
    }

}
