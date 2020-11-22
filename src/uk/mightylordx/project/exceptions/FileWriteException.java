package uk.mightylordx.project.exceptions;

public class FileWriteException extends RuntimeException {
    public FileWriteException(String message, Throwable err) {
        super(message, err);
    }
}