package maximax444.blps.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message + " -- не существует");
    }
}
