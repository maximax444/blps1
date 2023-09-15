package maximax444.blps.exceptions;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("Ошибка входа в систему");
    }
}