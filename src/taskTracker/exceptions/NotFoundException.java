package tasktracker.exceptions;

 // Исключение, выводится в случае, если ресурс не найден

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}