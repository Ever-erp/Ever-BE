package com.ever.autoever_1st.common.exception;

import com.ever.autoever_1st.common.dto.response.ApiResponse;
import com.ever.autoever_1st.common.exception.exception_class.business.BusinessException;
import com.ever.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.ever.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.ever.autoever_1st.common.exception.exception_class.database.DatabaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e){
        log.error("BusinessException : {}", e.getMessage());
        ApiResponse<Object> response = ApiResponse.fail(e.getMessage(), e.getStatus());
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(ValidationException e){
        log.error("ValidationException : {}", e.getMessage());
        ApiResponse<Object> response = ApiResponse.fail(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataNotFoundException(DataNotFoundException e){
        log.error("DataNotFoundException : {}", e.getMessage());
        ApiResponse<Object> response = ApiResponse.fail(e.getMessage(), e.getStatus());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException e){
        log.error("RuntimeException : {}", e.getMessage());
        ApiResponse<Object> response = ApiResponse.fail("서버 내부오류 발생", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e){
        log.error("Exception : {}", e.getMessage());
        ApiResponse<Object> response = ApiResponse.fail("예상치 못한 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // database
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiResponse<Object>> handleDatabaseException(DatabaseException e){
        log.error("DatabaseException : {}", e.getMessage(), e);
        ApiResponse<Object> response = ApiResponse.fail(e.getMessage(), e.getStatus());
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(response);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataAccessException(DataAccessException e){
        log.error("DataAccessException : {}", e.getMessage(), e);
        ApiResponse<Object> response = ApiResponse.fail("데이터베이스 접근 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail("권한이 없습니다.", HttpStatus.FORBIDDEN.value()));
    }
}
