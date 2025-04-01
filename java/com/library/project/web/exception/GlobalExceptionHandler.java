package com.library.project.web.exception;

import com.library.project.web.payload.ApiResponse;
import com.library.project.web.payload.JsonRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest){
        ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handlerBadRequestException(
            BadRequestException exception, WebRequest webRequest){
        ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArrayListFormatException.class)
    public ResponseEntity<ApiResponse> handlerArrayListFormatException(
            ArrayListFormatException exception, WebRequest webRequest){
        ApiResponse apiResponse = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),exception.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(apiResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse> handlerConflictException(
            ConflictException exception, WebRequest webRequest){
        ApiResponse apiResponse = new ApiResponse(HttpStatus.CONFLICT.value(),exception.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, WebRequest webRequest){
        Map<String,String> mapErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) ->{
            String clave = ((FieldError)error).getField();
            String valor = error.getDefaultMessage();
            mapErrors.put(clave,valor);
        });
        JsonRequired jsonRequired = new JsonRequired(HttpStatus.BAD_REQUEST.value(),mapErrors,
                webRequest.getDescription(false));
        return  new ResponseEntity<>(jsonRequired, HttpStatus.BAD_REQUEST);
    }
}
