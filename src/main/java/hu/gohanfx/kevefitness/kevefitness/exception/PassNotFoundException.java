package hu.gohanfx.kevefitness.kevefitness.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The pass is not found in the database!")
public class PassNotFoundException extends RuntimeException {
}
