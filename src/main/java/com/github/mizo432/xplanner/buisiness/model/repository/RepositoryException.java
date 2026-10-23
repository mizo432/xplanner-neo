package com.github.mizo432.xplanner.buisiness.model.repository;

public class RepositoryException extends Exception {
    public RepositoryException() {
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
