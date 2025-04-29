package OOSE_Final_Project.Blog.exceptionHandler;

import OOSE_Final_Project.Blog.dto.res.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(NoSuchElementException ex) {
        var result = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "HandleNotFound", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(InternalAuthenticationServiceException ex) {
        var result = new ApiResponse<>(HttpStatus.BAD_REQUEST, "HandleNotFound", null, "Bad credentials");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllException(Exception ex) {
        var result = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Handle All exception", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, "Invalid request content", errorList, "VALIDATION_ERROR");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
