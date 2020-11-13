package uk.mightylordx.project.exceptions;

public class IncorrectFileException extends RuntimeException {
    public IncorrectFileException(String message, Throwable err) {
        super(message, err);
    }
}
