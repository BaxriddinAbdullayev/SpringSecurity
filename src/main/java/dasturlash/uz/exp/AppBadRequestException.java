package dasturlash.uz.exp;

import org.apache.coyote.BadRequestException;

public class AppBadRequestException extends RuntimeException {
    public AppBadRequestException(String message) {
        super(message);
    }
}
