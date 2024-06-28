package de.borisskert.springboot.liquibaseexample.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProductSearchException extends RuntimeException {

    public ProductSearchException(Throwable cause) {
        super(cause);
    }
}
