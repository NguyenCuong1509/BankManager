package com.system.bank.exception;

import com.system.bank.dto.ApiReponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandleException {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiReponse<Object>> handlingException(Exception ex){
        log.error("Exception chưa xác định", ex);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        ApiReponse<Object> apiReponse = ApiReponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiReponse);
    }
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiReponse<Object>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiReponse<Object> apiReponse = ApiReponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiReponse);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiReponse<Object>> handlingValidation(MethodArgumentNotValidException methodException){
        FieldError fieldError = methodException.getFieldError();
        String message = fieldError != null? fieldError.getDefaultMessage():"Dữ liệu khng hợp lệ";

        ErrorCode errorCode = ErrorCode.RESULT_INVALID;
        try {
            if (fieldError != null && fieldError.getDefaultMessage()!=null){
                errorCode= ErrorCode.valueOf(fieldError.getDefaultMessage());
                message=errorCode.getMessage();

            }
        }catch(IllegalArgumentException exception){
            throw new IllegalArgumentException("lỗi"+exception);
        }
        ApiReponse<Object> apiReponse = ApiReponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiReponse);
    }
}
