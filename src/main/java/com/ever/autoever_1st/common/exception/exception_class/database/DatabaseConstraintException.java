package com.ever.autoever_1st.common.exception.exception_class.database;

import com.ever.autoever_1st.common.exception.CustomStatus;
import org.springframework.http.HttpStatus;

public class DatabaseConstraintException extends DatabaseException {

    public DatabaseConstraintException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public DatabaseConstraintException(String message, CustomStatus customStatus) {
        super(message, customStatus);
    }

    public DatabaseConstraintException(String message, int status) {
        super(message, status);
    }

    public DatabaseConstraintException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }

    public DatabaseConstraintException(String message, CustomStatus customStatus, Throwable cause) {
        super(message, customStatus, cause);
    }

    public DatabaseConstraintException(String message, int status, Throwable cause) {
        super(message, status, cause);
    }
}
