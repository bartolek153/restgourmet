package app.restgourmet.api.shared.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Class<?> clazz, UUID id) {
        super(String.format("Resource %s with id %s not found", clazz.getSimpleName(), id.toString()));
    }
}
